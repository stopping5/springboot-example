package com.example.securityapidemo.model;

import lombok.Data;

/**
 * @Description 通过请求实体类
 * @Author stopping
 */
@Data
public class RequestBodyVo {
    /**
     * 验签
     */
    private String signed;
    /**
     * 请求数据
     */
    private Object data;
}
