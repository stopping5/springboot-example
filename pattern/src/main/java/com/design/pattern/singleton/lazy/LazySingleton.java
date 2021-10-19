package com.design.pattern.singleton.lazy;

/**
 * @Description LazySingleton 懒汉式单例
 * @Author stopping
 * @date: 2021/3/16 23:23
 */

public class LazySingleton {
    /**
     * 私有化构造函数
     */
    private LazySingleton() {
    }

    /**
     * 初始化不创建对象
     * volatile 解决指令重排序问题
     */
    private volatile static LazySingleton lazySingleton = null;

    /**
     * 未能实现单例
     * */
    /*private static LazySingleton getInstance(){
        if (lazySingleton == null){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }*/
    /**
     * 同步锁类同步，影响性能 -> 双重检查锁[同步锁方法，两次判断空]
     * */
/*    public synchronized static LazySingleton getInstance(){
        if (lazySingleton == null){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }*/

    /**
     * 双重检查锁 懒人单例模式
     * 同步锁方法，两次判断空
     */
    public static LazySingleton getInstance() {
        if (lazySingleton == null) {
            synchronized (LazySingleton.class) {
                //此处防止第一个线程进来，但是第二个线程已经经过第一次判断，当第一个线程出去，第二个线程依然可以实例化对象。
                //加上多一重判断就可以防止这种问题了
                if (lazySingleton == null) {
                    lazySingleton = new LazySingleton();
                }
            }
        }
        return lazySingleton;
    }
}
