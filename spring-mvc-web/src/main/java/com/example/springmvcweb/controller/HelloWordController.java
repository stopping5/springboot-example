package com.example.springmvcweb.controller;

import com.example.springmvcweb.Annotation.SPAutowired;
import com.example.springmvcweb.Annotation.SPController;
import com.example.springmvcweb.Annotation.SPRequestMapping;
import com.example.springmvcweb.Annotation.SPRequestParam;
import com.example.springmvcweb.service.HelloService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description HelloWordController
 * @Author stopping
 * @date: 2021/3/14 15:06
 */
@SPController
@SPRequestMapping(value = "/demo")
public class HelloWordController {
    @SPAutowired
    private HelloService helloService;

    private Integer i = 0;

    @SPRequestMapping(value = "/sayHello")
    public void helloWord(HttpServletRequest req, HttpServletResponse resp) {
        String say = helloService.sayHello();
        try {
            resp.getWriter().write(say);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SPRequestMapping(value = "/returnSay")
    public String helloWordParam(HttpServletRequest req, HttpServletResponse resp,
                                 @SPRequestParam(value = "name") String name, @SPRequestParam("age") String age) {
        String say = helloService.sayHello();
        System.out.println("request:" + name + ",age" + age + ",requestTime:" + (i++));
        return say;
    }
}
