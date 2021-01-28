package com.stopping.datainteractiondemo.model;

import lombok.Data;

@Data
public class RequestBody<T> {
    private String version;
    private String saasCode;
    private String sign;
    private T data;
}
