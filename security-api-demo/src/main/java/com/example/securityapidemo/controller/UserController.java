package com.example.securityapidemo.controller;

import com.example.securityapidemo.model.RequestBodyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制层
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @PostMapping("/u")
    public String saveUser(@RequestBody RequestBodyVo requestBodyVo) {
        log.info("user请求:" + requestBodyVo.toString());
        return "SUCCESS";
    }
}
