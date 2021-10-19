package com.example.rabbitmqdemo.Config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("139.224.113.113");
        connectionFactory.setPort(34566);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");
        return connectionFactory;
    }
}
