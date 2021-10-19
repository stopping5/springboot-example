package com.example.rabbitmqdemo.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQproducer {
    private static String host = "127.0.0.1";
    private static String userName = "root";
    private static String passWord = "root";
    private static int port = 5672;

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(userName);
            factory.setPassword(passWord);
            Connection connect = factory.newConnection();
            Channel channel = connect.createChannel();
            String msg = "test message";
            channel.basicPublish("SIMPLE_EXCHANGE", "gupao.best", null, msg.getBytes());
            channel.close();
            connect.close();
            System.out.println("public finish");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
