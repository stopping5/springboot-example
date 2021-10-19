package com.example.chat.model;

import lombok.Data;

/**
 * @Description Message 消息类
 * @Author stopping
 * @date: 2021/2/15 21:56
 */
@Data
public class Message {
    /**
     * 发送者名
     */
    private String sendName;
    /**
     * 接收者名
     */
    private String recipientName;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 发送消息模式:0 群发 1 指定人发送
     */
    private Integer MessageModel;
}
