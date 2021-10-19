package com.design.pattern.model;

/**
 * @Description ChinessCourse
 * @Author stopping
 * @date: 2021/3/11 22:57
 */

public class ChineseCourse implements ICourse {
    @Override
    public void study() {
        System.out.println("学习了中文");
    }
}
