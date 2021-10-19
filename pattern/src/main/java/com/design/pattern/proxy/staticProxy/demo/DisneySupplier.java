package com.design.pattern.proxy.staticProxy.demo;

/**
 * @Description DisneySupplier
 * @Author stopping
 * @date: 2021/3/31 17:52
 */

public class DisneySupplier implements Supplier {
    @Override
    public void sellGoods() {
        System.out.println("disneySupplier sell goods");
    }
}
