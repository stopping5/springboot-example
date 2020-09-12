package com.example.springbootwithmybatis.service;

import com.example.springbootwithmybatis.mapper.UserMapper;
import com.example.springbootwithmybatis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: stopping
 * @Date: 2020/09/11 10:11
 * 转载注明出处、个人博客网站:www.stopping.top
 */
@Service
public class UserServiceImp implements UserService{
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }
}
