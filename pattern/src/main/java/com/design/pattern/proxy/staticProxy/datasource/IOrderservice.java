package com.design.pattern.proxy.staticProxy.datasource;

/**
 * @Description IOrderservice 订单接口
 * @Author stopping
 * @date: 2021/3/31 20:15
 */
public interface IOrderservice {
    /**
     * 添加数据
     */
    int createOrder(Order order);
}
