package com.mapstruct.demo.mapper;

import com.alibaba.fastjson.JSONObject;
import com.mapstruct.demo.model.school.SchoolBody;
import com.mapstruct.demo.model.school.SchoolDto;
import com.mapstruct.demo.model.user.UserBody;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class SchoolMappingTest {
    @Resource
    private SchoolMapping schoolMapping;

    @Test
    public void test(){
        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setName("大学");
        UserBody userBody = new UserBody();
        userBody.setPassword("123");
        userBody.setUsername("xdp");

        UserBody userBody1 = new UserBody();
        userBody1.setPassword("1234");
        userBody1.setUsername("xdp1");

        List<UserBody> userBodies = new ArrayList<>();
        userBodies.add(userBody);
        userBodies.add(userBody1);

        String json =  JSONObject.toJSONString(userBodies);
        schoolDto.setUsers(json);

        SchoolBody body = schoolMapping.schoolDtoToBody(schoolDto);
        log.info(body.toString());
    }
}