package com.stopping.demo.implement;

/**
 * @Description ImpExampleImp
 * @Author stopping
 * @date: 2021/2/21 19:06
 */

public class ImpExampleImp implements ImpExample {
    @Override
    public void fun1() {
        System.out.println("fun1");
    }

    @Override
    public void fun2() {

    }

    public static void main(String[] args) {
        ImpExampleImp impExampleImp = new ImpExampleImp();
        impExampleImp.fun1();
        impExampleImp.fun2();
    }
}
