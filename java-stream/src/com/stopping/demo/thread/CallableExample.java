package com.stopping.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description CallableExample
 * @Author stopping
 * @date: 2021/2/28 11:58
 */

public class CallableExample implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("callable开始返回值");
        int i = 1;
        int j = 2;
        return i + j;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableExample callableExample = new CallableExample();
        FutureTask<Integer> futureTask = new FutureTask<>(callableExample);
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("futureTask return value:" + futureTask.get());
    }
}
