package com.example.rabbitmqdemo.Controller;

import com.example.rabbitmqdemo.Config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@RestController
public class SendController {
    public String QUEUE_NAME = "send_msg_demo";

    private String EXCHANGE_NAMEW = "SEND_EXCHANGE";
    @Autowired
    private RabbitMqConfig rabbitMqConfig;

    @RequestMapping("/send")
    public String sendMsg(String message) {
        ConnectionFactory connectionFactory = rabbitMqConfig.connectionFactory();
        try {
            Connection connection = connectionFactory.newConnection();
            //创建Channel
            //The channel can now be used to send and receive messages 通道可以用来发送和接收消息
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAMEW, "direct");
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("send message :" + message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }
}
