package com.design.pattern.singleton.hungry;

/**
 * @Description HungrySingleton 饿汉式单例
 * @Author stopping
 * @date: 2021/3/16 23:15
 */
public class HungrySingleton {
    /**
     * 构造方法私有化
     */
    private HungrySingleton() {
    }

    /**
     * static 静态变量加载的时候实例化对象、全局访问
     * final 防止反射机制破坏访问到实例化对象
     */
    private static final HungrySingleton hungrySingleton = new HungrySingleton();

    /**
     * 获取单例对象唯一方法
     */
    public static HungrySingleton getInstance() {
        return hungrySingleton;
    }

    public void sayHello() {
        System.out.println("hello");
    }
}
