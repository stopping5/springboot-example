package com.design.pattern.factory.abstractFactory;

import com.design.pattern.model.EnglishHomework;
import com.design.pattern.model.EnglishNote;
import com.design.pattern.model.IHomeWork;
import com.design.pattern.model.INote;

/**
 * @Description EnglishCourseFactory
 * @Author stopping
 * @date: 2021/3/12 0:26
 */

public class EnglishCourseFactory implements CourseFactory {
    @Override
    public INote createNote() {
        return new EnglishNote();
    }

    @Override
    public IHomeWork createHomework() {
        return new EnglishHomework();
    }
}
