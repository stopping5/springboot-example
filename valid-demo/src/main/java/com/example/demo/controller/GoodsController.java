package com.example.demo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stopping
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/goods")
@Api(tags = "商品接口")
public class GoodsController {
    @GetMapping("goods")
    @ApiOperation("获取商品接口")
    public String getGoods(){
        return "SUCCESS";
    }
}
