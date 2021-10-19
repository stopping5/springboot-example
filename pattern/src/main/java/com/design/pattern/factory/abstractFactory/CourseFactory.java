package com.design.pattern.factory.abstractFactory;

import com.design.pattern.model.IHomeWork;
import com.design.pattern.model.INote;

/**
 * @Description CourseFactory
 * @Author stopping
 * @date: 2021/3/12 0:17
 */
public interface CourseFactory {
    /**
     * 生成笔记
     */
    INote createNote();

    /**
     * 生成作业
     */
    IHomeWork createHomework();
}
