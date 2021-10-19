package com.stopping.demo.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Description ThreadExample
 * @Author stopping
 * @date: 2021/2/22 16:49
 */

public class ThreadExample extends Thread {
    public void fun1() {
        System.out.println("hello thread");
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadExample print");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread runnableThread = new Thread(new RunnableExample());
        runnableThread.setDaemon(true);
        runnableThread.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程关闭");
    }
}
