package com.mapstruct.demo.mapper;

import com.alibaba.fastjson.JSONObject;
import com.mapstruct.demo.model.school.SchoolBody;
import com.mapstruct.demo.model.school.SchoolDto;
import com.mapstruct.demo.model.user.UserBody;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SchoolMapping {

    SchoolBody schoolDtoToBody(SchoolDto dto);

    default List<UserBody> userJsonToBean(String users) {
        if ("".equals(users)) {
            return null;
        }
        return JSONObject.parseArray(users, UserBody.class);
    }
}
