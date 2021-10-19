package com.example.securityapidemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@Slf4j
public class SecurityApiDemoApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SecurityApiDemoApplication.class, args);
        String[] name = applicationContext.getBeanDefinitionNames();
        for (String beanName : name) {
            log.info(beanName);
        }
    }

}
