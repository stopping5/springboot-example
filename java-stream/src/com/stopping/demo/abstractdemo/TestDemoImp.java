package com.stopping.demo.abstractdemo;

/**
 * @Description TestDemoImp
 * @Author stopping
 * @date: 2021/2/24 16:39
 */

public class TestDemoImp extends Testdemo {
    @Override
    void fun1() {
        super.fun1();
    }

    public static void main(String[] args) {
        TestDemoImp testDemoImp = new TestDemoImp();
        testDemoImp.fun1();
    }
}
