package com.example.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description CorsConfig
 * @Author stopping
 * @date: 2021/2/16 11:24
 */

@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /*registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);*/
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .allowCredentials(true)
                .maxAge(3600); // 1小时内不需要再预检（发OPTIONS请求）
    }
}