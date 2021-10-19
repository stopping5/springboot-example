package com.design.pattern.singleton.lazy;

/**
 * @Description LazyInnerClassSingleton 懒汉式内部类单例模式
 * 可以避免使用synchronize关键字 减少性能的影响
 * 区别于饿汉式单例，当外部类LazyInnerClassSingleton被调用时内部类LazyHolder才会进行实例化保存到内存空间。
 * 性能最优的一种写法,了解内部类的流程
 * 缺点：可以被反射机制破坏
 * @Author stopping
 * @date: 2021/3/17 0:01
 */

public class LazyInnerClassSingleton {
    /**
     * 构造方法私有化
     */
    private LazyInnerClassSingleton() {
        if (LazyHolder.lazy != null) {
            throw new RuntimeException("单例模式只能实例化一个对象");
        }
    }

    /**
     * 获取单例实例对象
     */
    public static LazyInnerClassSingleton getInstance() {
        return LazyHolder.lazy;
    }

    /**
     * 实现静态内部类，利用JVM的加载机制线程安全，实现单例模式
     */
    private static class LazyHolder {
        private static final LazyInnerClassSingleton lazy = new LazyInnerClassSingleton();
    }
}
