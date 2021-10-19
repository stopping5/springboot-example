package com.design.pattern.proxy.dynamicProxy.demo1;

/**
 * @Description Gril
 * @Author stopping
 * @date: 2021/3/31 22:22
 */

public class Girl implements Person {
    @Override
    public void findLove() {
        System.out.println("高富帅");
        System.out.println("身高180cm");
        System.out.println("有6块腹肌");
    }
}
