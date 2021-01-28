package com.mapstruct.demo.model.user;

import com.mapstruct.demo.model.book.BookBody;
import lombok.Data;

import java.util.List;

/**
 * 用户实体类
 * */
@Data
public class UserBody {
    /**
     * 用户名
     * */
    private String username;
    /**
     * 密码
     * */
    private String password;
    /**
     * 用户借用图书
     * */
    private List<BookBody> books;
}
