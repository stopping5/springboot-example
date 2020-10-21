package com.stopping.swaggerdemo.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: stopping
 * @Date: 2020/10/21 16:21
 * 转载注明出处、个人博客网站:www.stopping.top
 */
@ApiModel(value = "用户实体类")
@Getter
@Setter
@AllArgsConstructor
public class User {
    @ApiModelProperty(value = "用户id")
    /**
     * 用户ID
     * */
    private Long id;
    @ApiModelProperty(value = "用户名")
    private String name;
    @ApiModelProperty(value = "用户密码")
    private String password;
}
