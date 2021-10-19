package com.example.websocket.websocket;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description OneToManyWebSocket 一对多socket
 * @Author stopping
 * @date: 2021/1/27 13:05
 */
@Slf4j
@Component
@ServerEndpoint("/oneToMany/{number}")
@Getter
@Setter
public class OneToManyWebSocket {
    /**
     * 在线数量
     */
    private static AtomicInteger userNum = new AtomicInteger();
    /**
     * 缓存信息
     */
    private static ConcurrentMap<String, Session> userSession = new ConcurrentHashMap<>();

    /**
     * @Description 建立连接成功调用
     * @Param session , num 登录号
     * @Author stopping
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("number") String num) {
        addUserNum();
        //计算在线数量
        log.info("建立连接成功{},当前在先用户数量{}", num, userNum);
        //缓存用户信息
        userSession.put(num, session);
        //广播所有用户消息
        try {
            broadcast(num + "已经登录");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description 关闭连接使用
     * @Param num 用户手机号码
     * @Author stopping
     */
    @OnClose
    public void onClose(@PathParam("number") String num) {
        //自检数量
        supUserNum();
        //移除缓存
        userSession.remove(num);
        log.info("用户{}退出,当前用户数量", num, userNum);
    }

    /**
     * 广播消息
     */
    public void broadcast(String message) throws IOException {
        Set<String> keys = userSession.keySet();
        for (String key : keys) {
            Session session = userSession.get(key);
            sendMessage(session, message);
        }
    }

    /**
     * 发送消息
     */
    @OnMessage
    public void sendMessage(Session session, String message) throws IOException {
        log.info("发送信息{}", message);
        if (session != null) {
            synchronized (session) {
                System.out.println("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 数量自增
     */
    public void addUserNum() {
        userNum.incrementAndGet();
    }

    /**
     * 数量自减
     */
    public void supUserNum() {
        userNum.decrementAndGet();
    }


}
