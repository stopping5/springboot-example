package com.design.pattern.model.build;

import lombok.Data;

/**
 * @Description Computer 计算机类
 * @Author stopping
 * @date: 2021/3/13 1:14
 */
@Data
public class Computer {
    private String cpu;//need
    private String ram;//need
    private int usbNum;
    private String keyboard;
    private String display;

    //build生成的属性值赋给生成类
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.usbNum = builder.usbNum;
        this.keyboard = builder.keyboard;
        this.display = builder.display;
    }

    //构造方法
    public static class Builder {
        private String cpu;//need
        private String ram;//need
        private int usbNum;
        private String keyboard;
        private String display;

        //必传属性放在Builder的构造函数
        public Builder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }

        //返回父类对象
        public Computer build() {
            return new Computer(this);
        }

        public int getUsbNum() {
            return usbNum;
        }

        public Builder setUsbNum(int usbNum) {
            this.usbNum = usbNum;
            return this;
        }

        public String getKeyboard() {
            return keyboard;
        }

        public Builder setKeyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public String getDisplay() {
            return display;
        }

        public Builder setDisplay(String display) {
            this.display = display;
            return this;
        }
    }
}
