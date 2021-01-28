package com.stopping.datainteractiondemo.controller;

import cn.shuibo.annotation.Decrypt;
import cn.shuibo.annotation.Encrypt;
import com.stopping.datainteractiondemo.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestBody User user){
        System.out.println(user.getName() + "&" + user.getPassword());
        return "hello";
    }

    @PostMapping("/encrypt")
    public String encrypt(){
        System.out.println("okokokok");
        return "SUCCESS";
    }
}
