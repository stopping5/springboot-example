package com.stopping.demo.thread;

/**
 * @Description RunnableExample
 * @Author stopping
 * @date: 2021/2/28 11:48
 */

public class RunnableExample implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("runnable" + i);
        }
    }

    public static void main(String[] args) {
        RunnableExample example = new RunnableExample();
        new Thread(example).start();
    }
}
