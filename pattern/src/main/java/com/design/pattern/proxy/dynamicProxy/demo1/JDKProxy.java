package com.design.pattern.proxy.dynamicProxy.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description JDKProxy
 * @Author stopping
 * @date: 2021/3/31 22:20
 */

public class JDKProxy implements InvocationHandler {
    private Object target;

    public Object getInstance(Object target) {
        //赋值target
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    /**
     * @param method 代理的方法
     * @param args   参数
     * @Description 代理增强类用于在目标方法实现AOP操作。
     * @Param proxy 代理对象
     * @Return java.lang.Object
     * @Date 2020/5/23 20:26
     * @Author stopping
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //before、after AOP实现
        before();
        //通过反射机制实现调用方法
        Object obj = method.invoke(this.target, args);
        after();
        return obj;
    }

    private void after() {
        System.out.println("after");
    }

    private void before() {
        System.out.println("before");
    }

}
