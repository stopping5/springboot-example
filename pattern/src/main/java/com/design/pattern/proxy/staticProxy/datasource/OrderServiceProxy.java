package com.design.pattern.proxy.staticProxy.datasource;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description OrderServiceProxy
 * @Author stopping
 * @date: 2021/3/31 20:24
 */

public class OrderServiceProxy implements IOrderservice {

    private IOrderservice orderService;

    public OrderServiceProxy(IOrderservice orderService) {
        this.orderService = orderService;
    }

    @Override
    public int createOrder(Order order) {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        before();
        Date time = order.getDate();
        Integer dbRouter = Integer.valueOf(yearFormat.format(time));
        DynamicDataSourceEntry.set(dbRouter);
        orderService.createOrder(order);
        after();
        return 0;
    }

    private void before() {
        System.out.println("Proxy before method.");
    }

    private void after() {
        System.out.println("Proxy after method.");
    }

}
