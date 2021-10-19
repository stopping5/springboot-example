package com.mapstruct.demo.model.user;

import com.mapstruct.demo.model.book.BookDto;
import lombok.Data;

import java.util.List;

/**
 * 用户dto
 */
@Data
public class UserDto {
    private String name;

    private String password;

    private List<BookDto> books;
}
