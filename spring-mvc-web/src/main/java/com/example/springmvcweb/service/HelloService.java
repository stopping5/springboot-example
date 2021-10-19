package com.example.springmvcweb.service;

import com.example.springmvcweb.Annotation.SPService;

/**
 * @Description HelloService
 * @Author stopping
 * @date: 2021/3/14 15:09
 */
@SPService
public class HelloService {
    public String sayHello() {
        System.out.println("service hello");
        return "hello word";
    }
}
