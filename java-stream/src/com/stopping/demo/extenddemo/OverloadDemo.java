package com.stopping.demo.extenddemo;

/**
 * @Description OverloadDemo
 * @Author stopping
 * @date: 2021/2/24 23:02
 */

public class OverloadDemo {
    private int heigh;

    public OverloadDemo() {
        System.out.println("树苗");
    }

    public OverloadDemo(int heigh) {
        this.heigh = heigh;
        System.out.println("具有" + heigh + "m的树木");
    }

    public static void main(String[] args) {
        OverloadDemo overloadDemo = new OverloadDemo(1);
        OverloadDemo overloadDemo1 = new OverloadDemo();

    }
}
