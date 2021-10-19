package com.design.pattern.factory.abstractFactory;

/**
 * @Description AbstrctFactoryTest
 * @Author stopping
 * @date: 2021/3/12 0:26
 */

public class AbstrctFactoryTest {
    public static void main(String[] args) {
        CourseFactory factory = new ChineseCourseFactory();
        factory.createHomework().study();
        factory.createNote().edit();
    }
}
