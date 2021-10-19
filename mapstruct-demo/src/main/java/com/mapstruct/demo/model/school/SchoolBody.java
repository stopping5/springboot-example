package com.mapstruct.demo.model.school;

import com.mapstruct.demo.model.user.UserBody;
import lombok.Data;

import java.util.List;

/**
 * 学校 body
 */
@Data
public class SchoolBody {
    /**
     * school name
     */
    private String name;
    /**
     * all student in school
     */
    private List<UserBody> users;
}
