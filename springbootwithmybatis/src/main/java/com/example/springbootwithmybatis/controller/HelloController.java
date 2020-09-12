package com.example.springbootwithmybatis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: stopping
 * @Date: 2020/09/11 10:11
 */
@RestController
public class HelloController {

    @RequestMapping(value = "hello")
    public String hello(){
        return "hello world";
    }
}
