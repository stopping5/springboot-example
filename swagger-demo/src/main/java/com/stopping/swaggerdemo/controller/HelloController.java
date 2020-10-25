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

    @GetMapping
    @ApiOperation(value = "hello 测试")
    public String sayHello(){
        return "Hello";
    }
}
