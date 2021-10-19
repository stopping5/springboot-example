package com.example.websocket.model;

import lombok.Data;

/**
 * @Description Message
 * @Author stopping
 * @date: 2021/1/27 21:52
 */
@Data
public class Message {
    /**
     * 消息内容
     */
    private String msg;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 指定用户
     */
    private String username;
}
