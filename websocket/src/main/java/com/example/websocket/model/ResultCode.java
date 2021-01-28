package com.example.websocket.model;

import lombok.Getter;

/**
 * @Description ResultCode
 * @Author stopping
 * @date: 2021/1/27 22:22
 */
@Getter
public enum ResultCode {
    SUCCESS(200,"操作成功"),
    FAILED(499,"操作失败,业务异常"),
    ERROR(500,"系统异常")
    ;
    private final long  code;
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
