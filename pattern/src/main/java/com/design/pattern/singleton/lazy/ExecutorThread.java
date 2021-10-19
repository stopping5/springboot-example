package com.design.pattern.singleton.lazy;

/**
 * @Description ExecutorThread
 * @Author stopping
 * @date: 2021/3/16 23:44
 */

public class ExecutorThread implements Runnable {
    @Override
    public void run() {
//        LazySingleton lazySingleton = LazySingleton.getInstance();
        LazyInnerClassSingleton lazySingleton = LazyInnerClassSingleton.getInstance();
        System.out.println("thread name:" + Thread.currentThread().getName() + ":" + lazySingleton);
    }
}
