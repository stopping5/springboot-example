package com.example.chat.controller;

import com.example.chat.model.CommonResult;
import com.example.chat.service.websocket.ChartServiceImp;
import com.example.chat.utils.RedisUtil;
import com.example.websocket.model.User;
import com.example.websocket.websocket.ChatWebSocket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stopping
 * @since 2021-01-27
 */
@RestController
@Api(tags = "用户模块")
@RequestMapping("/user")
public class UserController {
    @Resource
    private RedisUtil redisUtil;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public CommonResult login(@RequestBody User user, HttpServletRequest request) {
        request.getSession().setAttribute("user", user);
        return CommonResult.success("success");
    }

    @GetMapping("/users")
    @ApiOperation(value = "当前用户列表")
    public CommonResult getUser(@ApiIgnore HttpServletRequest request) {
        Set<String> usernames = ChartServiceImp.getUserSession().keySet();
        return CommonResult.success(usernames, "success");
    }
}

