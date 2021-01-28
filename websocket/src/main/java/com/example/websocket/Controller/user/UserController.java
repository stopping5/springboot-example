package com.example.websocket.Controller.user;

import com.example.websocket.model.CommonResult;
import com.example.websocket.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stopping
 * @since 2021-01-27
 */
/**
 * @Description UserController 用户控制器
 * @Author stopping
 * @date: 2021/1/27 21:49
 */
@RestController
@Api(tags = "用户模块")
public class UserController {
    /**
     * 用户登录
     * */
    @GetMapping("/login")
    @ApiOperation(value = "用户登录")
    public CommonResult login(@RequestParam(value = "username")String username, @ApiIgnore HttpSession session){
        User user = new User();
        user.setUsername(username);
        session.setAttribute("user",user);
        return CommonResult.success("success");
    }

    @GetMapping("/user")
    @ApiOperation(value = "获取用户信息")
    public CommonResult getUser(@ApiIgnore HttpSession session){
        User user = (User) session.getAttribute("user");
        return CommonResult.success(user,"success");
    }
}

