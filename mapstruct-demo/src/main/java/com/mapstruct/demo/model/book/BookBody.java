package com.mapstruct.demo.model.book;

import lombok.Data;

/**
 * 图书实体
 * */
@Data
public class BookBody {
    private String name;

    private String date;

    private Integer num;
}
