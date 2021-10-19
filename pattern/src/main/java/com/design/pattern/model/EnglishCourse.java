package com.design.pattern.model;

/**
 * @Description EnglishCourse
 * @Author stopping
 * @date: 2021/3/11 22:52
 */

public class EnglishCourse implements ICourse {
    @Override
    public void study() {
        System.out.println("学习了English");
    }
}
