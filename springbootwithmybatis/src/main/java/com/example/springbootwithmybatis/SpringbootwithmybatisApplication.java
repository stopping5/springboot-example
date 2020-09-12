package com.example.springbootwithmybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springbootwithmybatis.mapper")
public class SpringbootwithmybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootwithmybatisApplication.class, args);
    }

}
