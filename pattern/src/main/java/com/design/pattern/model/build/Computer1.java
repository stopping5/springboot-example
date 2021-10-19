package com.design.pattern.model.build;

import lombok.Data;

/**
 * @Description Computer1
 * @Author stopping
 * @date: 2021/3/13 9:44
 */
@Data
public class Computer1 {
    private String cpu;//need
    private String ram;//need
    private int usbNum;
    private String keyboard;
    private String display;

    public Computer1(String cpu, String ram) {
        this.cpu = cpu;
        this.ram = ram;
    }
}
