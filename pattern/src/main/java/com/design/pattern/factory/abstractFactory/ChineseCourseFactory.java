package com.design.pattern.factory.abstractFactory;

import com.design.pattern.model.ChineseHomeWork;
import com.design.pattern.model.ChineseNote;
import com.design.pattern.model.IHomeWork;
import com.design.pattern.model.INote;

/**
 * @Description ChineseCourseFactory
 * @Author stopping
 * @date: 2021/3/12 0:25
 */

public class ChineseCourseFactory implements CourseFactory {
    @Override
    public INote createNote() {
        return new ChineseNote();
    }

    @Override
    public IHomeWork createHomework() {
        return new ChineseHomeWork();
    }
}
