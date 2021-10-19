package com.stopping.demo.implement;

/**
 * @Description ImpExample
 * @Author stopping
 * @date: 2021/2/21 19:05
 */

public interface ImpExample {
    void fun1();

    default void fun2() {
        System.out.println("fun2");
    }
}
