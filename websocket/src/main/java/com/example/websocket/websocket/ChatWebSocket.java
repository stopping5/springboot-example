package com.example.websocket.websocket;

import com.alibaba.fastjson.JSONObject;
import com.example.websocket.model.Message;
import com.example.websocket.utils.RedisUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description ChatWebSocket
 * @Author stopping
 * @date: 2021/1/27 22:48
 */
@Component
@ServerEndpoint("/chat/{username}")
@Slf4j
public class ChatWebSocket {
    private static RedisUtil redisUtil;

    // 注入的时候，给类的 service 注入
    @Autowired
    public void setChatService(RedisUtil redisUtil) {
        ChatWebSocket.redisUtil = redisUtil;
    }
    /**
     * 计算登录人数
     * */
    private static AtomicInteger onlineNum = new AtomicInteger();
    /**
     * 缓存session
     * */
    private static ConcurrentMap<String,Session> userSession = new ConcurrentHashMap<>();

    public static ConcurrentMap<String, Session> getUserSession() {
        return userSession;
    }

    @OnOpen
    public void onOpen(Session session,@PathParam("username") String username){
        //新增在线数量
        onlineNum.incrementAndGet();
        //用户连接登录缓存
        userSession.put(username,session);
        log.info("用户[{}]登录,当前连接人数{}",username,onlineNum);
        //广播上线
        broadcast("用户["+username+"]上线了,当前在线人数:"+onlineNum);
        //发送用户离线消息
        outLineMessage(username);
    }

    /**
     * 获取离线消息
     * */
    private void outLineMessage(String username) {
        String msgs = redisUtil.get(username);
        if (msgs != null){
            sendIndo(username,msgs);
        }
    }

    @OnClose
    public void onClose(@PathParam("username") String username){
        //减少在线数量
        onlineNum.decrementAndGet();
        //用户断开连接逻辑
        userSession.remove(username);
        log.info("用户[{}]退出登录,当前连接人数{}",username,onlineNum);
        //广播下线
        broadcast("用户["+username+"]下线了,当前在线人数:"+onlineNum);
    }

    @OnMessage
    public void onMessage(String message){
        //发送信息
        Message msg = JSONObject.parseObject(message,Message.class);
        Integer type = msg.getType();
        if (type == 0){
            broadcast(msg.getMsg());
        }else if (type == 1){
            sendIndo(msg.getUsername(),msg.getMsg());
        }
    }
    /**
     * 指定用户发送
     * */
    private void sendIndo(String username,String message) {
        Session session = userSession.get(username);
        //用户存在直接发送消息
        if (session != null){
            session.getAsyncRemote().sendText(message);
        }
        //用户不存在缓存消息
        else{
            log.info("用户{}不存在缓存消息[{}]",username,message);
            redisUtil.append(username,message+",");
        }
    }

    /**
     * 广播消息
     * */
    private void broadcast(String msg) {
        for (Session session : userSession.values()){
            sendMsg(session,msg);
        }
    }

    /**
     * 发送普通短信
     * */
    private void sendMsg(Session session, String msg) {
        log.info("发送信息"+msg);
        //用户在线直接发送消息
        if (session != null){
            synchronized (session){
                try {
                    session.getBasicRemote().sendText(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
