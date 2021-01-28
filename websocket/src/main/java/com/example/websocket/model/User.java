package com.example.websocket.model;

import lombok.Data;

/**
 * @Description User
 * @Author stopping
 * @date: 2021/1/27 21:51
 */
@Data
public class User {
    /**
     * 用户名
     * */
    private String username;
    /**
     * 密码
     * */
    private String password;

    /**
     * id
     * */
    private int id;
}
