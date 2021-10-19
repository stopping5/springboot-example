package com.design.pattern.factory.build.builder;

import com.design.pattern.model.build.Computer1;
import lombok.Data;

/**
 * @Description MacComputerBuilder
 * @Author stopping
 * @date: 2021/3/13 9:47
 */
public class MacComputerBuilder extends ComputerBuilder {
    private Computer1 computer1;

    public MacComputerBuilder(String cup, String ram) {
        this.computer1 = new Computer1(cup, ram);
    }

    @Override
    public ComputerBuilder setUsbNum() {
        computer1.setUsbNum(4);
        return this;
    }

    @Override
    public ComputerBuilder setKeyboard() {
        computer1.setKeyboard("apple keyboard");
        return this;
    }

    @Override
    public ComputerBuilder setDisplay() {
        computer1.setDisplay("apple display");
        return this;
    }

    @Override
    public Computer1 getComputer() {
        return computer1;
    }
}
