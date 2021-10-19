package com.design.pattern.factory.SimpleMethod;

import com.design.pattern.model.ChineseCourse;
import com.design.pattern.model.ICourse;

/**
 * @Description ChineseCourse
 * @Author stopping
 * @date: 2021/3/11 23:29
 */

public class ChineseCourseFactory implements ICourseFactory {
    @Override
    public ICourse create() {
        return new ChineseCourse();
    }
}
