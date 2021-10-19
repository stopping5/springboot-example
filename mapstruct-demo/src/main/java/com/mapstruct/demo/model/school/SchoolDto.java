package com.mapstruct.demo.model.school;

import lombok.Data;

/**
 * school dto
 */
@Data
public class SchoolDto {
    /**
     * school name
     */
    private String name;
    /**
     * all user message in json
     */
    private String users;
}
