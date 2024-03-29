package com.example.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Description WebsocketConfig 配置类
 * @Author stopping
 * @date: 2021/1/25 22:57
 */

@Configuration
public class WebsocketConfig {
    /**
     * 注入一个ServerEndpointExporter,该Bean会自动注册使用
     *
     * @ServerEndpoint注解申明的websocket endpoint
     */
    @Bean
    public ServerEndpointExporter registerWebSocketHandlers() {
        return new ServerEndpointExporter();
    }

}
