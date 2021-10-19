package com.design.pattern.proxy.staticProxy.demo;

/**
 * @Description MeituanProxy 美团代理平台
 * @Author stopping
 * @date: 2021/3/31 17:53
 */
public class MeituanProxy implements Supplier {
    private Supplier supplier;

    public MeituanProxy(Supplier supplier) {
        this.supplier = supplier;
    }

    private void before() {
        System.out.println("meituan proxy before");
    }

    private void after() {
        System.out.println("meituan proxy after");
    }

    @Override
    public void sellGoods() {
        before();
        supplier.sellGoods();
        after();
    }
}
