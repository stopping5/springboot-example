package com.design.pattern.factory.SimpleMethod;

import com.design.pattern.model.ICourse;

/**
 * @Description SimpleMethodFactory 工厂方法模式
 * @Author stopping
 * @date: 2021/3/11 23:26
 */

public interface ICourseFactory {
    /**
     * 创建课程方法
     */
    ICourse create();
}
