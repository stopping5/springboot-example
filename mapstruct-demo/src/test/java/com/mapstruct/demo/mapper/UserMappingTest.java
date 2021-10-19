package com.mapstruct.demo.mapper;

import com.mapstruct.demo.model.book.BookBody;
import com.mapstruct.demo.model.user.UserBody;
import com.mapstruct.demo.model.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class UserMappingTest {
    @Resource
    private UserMapping userMapping;

    @Test
    public void testUserMapping() {
        UserBody body = new UserBody();
        body.setPassword("123");
        body.setUsername("xdp");
        BookBody bookBody = new BookBody();
        bookBody.setDate("2020-01-12");
        bookBody.setName("肖生克的救赎");
        bookBody.setNum(1);
        List<BookBody> list = new ArrayList<>();
        list.add(bookBody);
        body.setBooks(list);
        UserDto dto = userMapping.userBodyToDto(body);
        log.info(dto.toString());
    }
}