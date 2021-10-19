package com.design.pattern.proxy.staticProxy.demo;

/**
 * @Description Client
 * @Author stopping
 * @date: 2021/3/31 17:56
 */

public class Client {
    public static void main(String[] args) {
        MeituanProxy meituanProxy = new MeituanProxy(new DisneySupplier());
        meituanProxy.sellGoods();
    }
}
