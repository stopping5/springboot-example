package com.stopping.swaggerdemo.controller;

import com.stopping.swaggerdemo.ResponseVo.Response;
import com.stopping.swaggerdemo.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: stopping
 * @Date: 2020/10/22 09:22
 * 转载注明出处、个人博客网站:www.stopping.top
 */
@Api(description = "用户访问接口")
@RestController
public class UserController {
    List<User> list = new ArrayList();

    @GetMapping("/user")
    @ApiOperation(value = "获取用户信息接口")
    public Response getUser(){
        return  new Response().success(list);
    }

    @PostMapping("/user")
    @ApiOperation(value = "新增用户接口")
    public Response addUser(@RequestBody @ApiParam("用户信息") User user){
        list.add(user);
        return new Response().success("");
    }
    @ApiOperation("删除用户接口")
    @DeleteMapping("/user/{id}")
    public Response insertUser(@PathVariable @ApiParam("用户body") long id){
        for(int i = 0;i<list.size();i++){
            if (list.get(i).getId() == id){
                list.remove(i);
                return new Response().success(list.get(i));
            }
        }
        return new Response().fail();
    }
}
