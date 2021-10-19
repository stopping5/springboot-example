package com.example.chat.service.websocket;

import com.alibaba.fastjson.JSONObject;
import com.example.chat.model.Message;
import com.example.chat.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description ChartServiceImp
 * @Author stopping
 * @date: 2021/2/15 22:00
 */
@ServerEndpoint("/chart/{username}")
@Component
@Slf4j
public class ChartServiceImp {
    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 缓存在线用户
     */
    private static ConcurrentMap<String, Session> userSession = new ConcurrentHashMap<>();

    public static ConcurrentMap<String, Session> getUserSession() {
        return userSession;
    }

    /**
     * 在线用户数量
     */
    private static AtomicInteger onLineNum = new AtomicInteger();

    /**
     * 建立连接
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        //新增用户数量
        onLineNum.incrementAndGet();
        //缓存用户session信息
        userSession.put(username, session);
        log.info("用户:{}已经登录,当前用户数量:{}", username, onLineNum);
    }

    /**
     * 发送消息
     */
    @OnMessage
    public void sendMessage(String data) {
        Message message = JSONObject.parseObject(data, Message.class);
        Integer msgModel = message.getMessageModel();
        String sendMsg = message.getMessage();
        //群发
        if (msgModel == 0) {
            sendAllUserMsg(data);
        }
        //指定联系人
        else if (msgModel == 1) {
            Session recipientSession = userSession.get(message.getRecipientName());
            sendMsg(recipientSession, data);
        }
    }

    /**
     * 发送群体信息
     */
    public void sendAllUserMsg(String message) {
        for (Session session : userSession.values()) {
            sendMsg(session, message);
        }
    }

    /**
     * 发送个人信息
     */
    public void sendMsg(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送信息异常");
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void close(@PathParam("username") String username) {
        userSession.remove(username);
        log.info("用户:{}已经下线了", username);
        onLineNum.decrementAndGet();
        log.info("当前在线用户:{}", onLineNum);
    }
}
