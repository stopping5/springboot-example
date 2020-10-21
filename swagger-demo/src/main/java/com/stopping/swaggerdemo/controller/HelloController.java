package com.stopping.swaggerdemo.controller;

import com.stopping.swaggerdemo.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: stopping
 * @Date: 2020/10/21 16:21
 * 转载注明出处、个人博客网站:www.stopping.top
 */
@RestController
@Api(description = "Hello模块接口文档")
public class HelloController {
    @GetMapping("/hello")
    @ApiOperation(value = "say hello 接口")
    public String hello(){
        User u = new User(1L,"stopping","123456");
        return "hello"+u.toString();
    }
    @ApiOperation("通过用户ID 查询用户信息接口")
    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable("id")@ApiParam("用户唯一ID") Long id){
        User u = new User(1L,"stopping","123456");
        u.getId();
        return u;
    }
    @ApiOperation("新增用户接口")
    @PostMapping("/addUser")
    public String insertUser(@RequestBody @ApiParam("用户body") User user){
        return "ok";
    }

}
