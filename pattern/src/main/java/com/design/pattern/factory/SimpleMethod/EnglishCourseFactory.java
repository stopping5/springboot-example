package com.design.pattern.factory.SimpleMethod;

import com.design.pattern.model.EnglishCourse;
import com.design.pattern.model.ICourse;

/**
 * @Description EnglishCourseFactory
 * @Author stopping
 * @date: 2021/3/11 23:30
 */

public class EnglishCourseFactory implements ICourseFactory {
    @Override
    public ICourse create() {
        return new EnglishCourse();
    }
}
