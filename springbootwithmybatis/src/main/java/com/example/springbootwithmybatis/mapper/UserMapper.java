package com.example.springbootwithmybatis.mapper;

import com.example.springbootwithmybatis.pojo.User;

import java.util.List;

/**
 * @Author: stopping
 * @Date: 2020/09/11 10:11
 * 转载注明出处、个人博客网站:www.stopping.top
 */

public interface UserMapper {
    List<User> findAllUser();
}
