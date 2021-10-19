package com.design.pattern.factory.build.builder;

import com.design.pattern.model.build.Computer1;

/**
 * @Description ComputerBuilder
 * @Author stopping
 * @date: 2021/3/13 9:46
 */

public abstract class ComputerBuilder {
    public abstract ComputerBuilder setUsbNum();

    public abstract ComputerBuilder setKeyboard();

    public abstract ComputerBuilder setDisplay();

    public abstract Computer1 getComputer();
}
