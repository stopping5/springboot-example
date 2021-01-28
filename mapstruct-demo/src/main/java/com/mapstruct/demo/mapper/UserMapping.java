package com.mapstruct.demo.mapper;


import com.mapstruct.demo.model.book.BookBody;
import com.mapstruct.demo.model.book.BookDto;
import com.mapstruct.demo.model.user.UserBody;
import com.mapstruct.demo.model.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 用户映射接口
 * */
@Mapper(componentModel = "spring")
public interface UserMapping {
    /**
     * 用户 body to dto
     * */
    @Mapping(source = "username",target = "name")
    UserDto userBodyToDto(UserBody body);

    @Mapping(source = "name",target = "bookName")
    @Mapping(source = "date",target = "lendDate")
    BookDto bookBodyToDto(BookBody bookBody);
}
