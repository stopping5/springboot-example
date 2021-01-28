package com.example.demo.serviceImp;

import com.example.demo.entity.User;
import com.example.demo.dao.UserMapper;
import com.example.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stopping
 * @since 2020-12-22
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

}
