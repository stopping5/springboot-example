package com.mapstruct.demo.model.book;

import lombok.Data;

/**
 * book dto
 */
@Data
public class BookDto {
    /**
     * 借用数量
     */
    private String bookName;
    /**
     * 借用日期
     */
    private String lendDate;
    /**
     * 借用数量
     */
    private Integer num;
}
