package com.stopping.demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description ReentrantLockExample
 * @Author stopping
 * @date: 2021/2/28 18:00
 */

public class ReentrantLockExample {
    private Lock lock = new ReentrantLock();

    public void func() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.print(i);
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockExample example = new ReentrantLockExample();
        ReentrantLockExample example1 = new ReentrantLockExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> example.func());
        executorService.execute(() -> example.func());
    }
}
