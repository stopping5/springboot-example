package com.design.pattern.proxy.staticProxy.datasource;

/**
 * @Description OrderService
 * @Author stopping
 * @date: 2021/3/31 20:15
 */

public class OrderService implements IOrderservice {

    private OrderDao orderDao;

    public OrderService() {
        this.orderDao = new OrderDao();
    }

    @Override
    public int createOrder(Order order) {
        orderDao.insert(order);
        return 1;
    }
}
