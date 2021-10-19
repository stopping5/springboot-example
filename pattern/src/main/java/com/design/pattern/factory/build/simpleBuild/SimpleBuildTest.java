package com.design.pattern.factory.build.simpleBuild;

import com.design.pattern.model.build.Computer;

/**
 * @Description SimpleBuildTest
 * @Author stopping
 * @date: 2021/3/13 1:22
 */

public class SimpleBuildTest {
    public static void main(String[] args) {
        Computer computer = new Computer.Builder("Intel", "金士顿")
                .setDisplay("飞利浦")
                .setKeyboard("达尔优")
                .setUsbNum(4).build();
        System.out.println("computer" + computer.toString());
    }
}
