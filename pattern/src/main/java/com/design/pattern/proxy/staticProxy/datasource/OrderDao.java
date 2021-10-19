package com.design.pattern.proxy.staticProxy.datasource;

/**
 * @Description OrderDao
 * @Author stopping
 * @date: 2021/3/31 20:26
 */

public class OrderDao {
    public int insert(Order order) {
        System.out.println("创建订单" + order.toString() + "成功");
        return 1;
    }
}
