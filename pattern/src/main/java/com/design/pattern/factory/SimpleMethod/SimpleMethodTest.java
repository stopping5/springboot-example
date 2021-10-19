package com.design.pattern.factory.SimpleMethod;

import com.design.pattern.model.ICourse;

/**
 * @Description SimpleMethodTest
 * @Author stopping
 * @date: 2021/3/11 23:32
 */

public class SimpleMethodTest {
    public static void main(String[] args) {
        ICourse course = new ChineseCourseFactory().create();
        course.study();

        ICourse course1 = new EnglishCourseFactory().create();
        course1.study();
    }
}
