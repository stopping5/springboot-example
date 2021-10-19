package com.design.pattern.factory.build.builder;

import com.design.pattern.model.build.Computer1;

/**
 * @Description Director 指导者
 * @Author stopping
 * @date: 2021/3/13 9:53
 */

public class Director {
    public void makeComputer(ComputerBuilder builder) {
        builder.setDisplay();
        builder.setKeyboard();
        builder.setUsbNum();
    }

    /**
     * 客户端
     */
    public static void main(String[] args) {
        Director director = new Director();
        MacComputerBuilder macComputerBuilder = new MacComputerBuilder("M1", "三星");
        //director.makeComputer(macComputerBuilder);
        Computer1 computer1 = macComputerBuilder.setDisplay().getComputer();
        System.out.println(computer1);
    }
}
