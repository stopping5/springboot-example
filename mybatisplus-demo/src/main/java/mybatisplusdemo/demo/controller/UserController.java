package mybatisplusdemo.demo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stopping
 * @since 2020-12-21
 */
@RestController
@RequestMapping("/user")
@Api("用户接口")
public class UserController {
    @ApiOperation("获取用户接口")
    @GetMapping("/getUser")
    public String getUser() {
        return "SUCCESS";
    }
}
