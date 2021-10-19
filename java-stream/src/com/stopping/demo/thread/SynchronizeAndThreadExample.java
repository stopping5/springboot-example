package com.stopping.demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description SynchronizeAndThreadExample
 * @Author stopping
 * @date: 2021/2/28 17:38
 */

public class SynchronizeAndThreadExample {
    /**
     * 同步方法
     */
    public void fun1() {
        synchronized (this) {
            System.out.println("");
            for (int i = 0; i < 10; i++) {
                System.out.print(i + ",");
            }
        }
    }

    public static void main(String[] args) {
        SynchronizeAndThreadExample example = new SynchronizeAndThreadExample();
        //不同对象
        SynchronizeAndThreadExample example1 = new SynchronizeAndThreadExample();
        ExecutorService e = Executors.newCachedThreadPool();
        e.execute(() -> example.fun1());
        e.execute(() -> example1.fun1());
    }

}
