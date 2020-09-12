package com.example.springbootwithmybatis.controller;

import com.example.springbootwithmybatis.pojo.User;
import com.example.springbootwithmybatis.service.UserService;
import com.example.springbootwithmybatis.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: stopping
 * @Date: 2020/09/11 10:11
 * 转载注明出处、个人博客网站:www.stopping.top
 */
@RestController
public class UserController {
    @Autowired
    private UserServiceImp userService;

    @RequestMapping("/findUser")
    public List<User> findUser(){
        return userService.findAllUser();
    }
}
