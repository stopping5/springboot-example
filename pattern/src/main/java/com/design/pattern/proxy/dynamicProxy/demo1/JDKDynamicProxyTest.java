package com.design.pattern.proxy.dynamicProxy.demo1;

/**
 * @Description JDKDynamicProxy
 * @Author stopping
 * @date: 2021/3/31 22:22
 */

public class JDKDynamicProxyTest {
    public static void main(String[] args) {
        Person person = (Person) new JDKProxy().getInstance(new Girl());
        person.findLove();
    }
}
