package com.design.pattern.proxy.staticProxy.datasource;

import lombok.Data;

import java.util.Date;

/**
 * @Description Order 订单信息
 * @Author stopping
 * @date: 2021/3/31 20:12
 */
@Data
public class Order {
    private Object orderInfo;

    private Long id;

    private Date date;
}
