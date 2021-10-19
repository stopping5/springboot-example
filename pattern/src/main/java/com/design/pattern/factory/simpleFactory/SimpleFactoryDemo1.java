package com.design.pattern.factory.simpleFactory;

import com.design.pattern.model.ChineseCourse;
import com.design.pattern.model.EnglishCourse;
import com.design.pattern.model.ICourse;
import org.springframework.util.StringUtils;

/**
 * @Description SimpleFactoryDemo1 简单工厂不是23种设计模式但是循序渐进学习工厂方法模式
 * @Author stopping
 * @date: 2021/3/11 22:51
 */

public class SimpleFactoryDemo1 {
    /**
     * @Description 通过识别标志工厂生产的对象，容易出错
     * @Param 课程名
     * @Return void
     * @Date 2021-03-11
     * @Author stopping
     */
    public ICourse createCourse(String course) {
        switch (course) {
            case "english":
                return new EnglishCourse();
            case "chinese":
                return new ChineseCourse();
        }
        return null;
    }

    /**
     * 通过类路径使用反射机制
     */
    public ICourse create(String classPath) {
        try {
            if (!StringUtils.isEmpty(classPath)) {
                return (ICourse) Class.forName(classPath).newInstance();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过clazz对象作为入参
     */
    public ICourse create(Class<? extends ICourse> clazz) {
        if (clazz != null) {
            try {
                return (ICourse) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SimpleFactoryDemo1 simpleFactoryDemo1 = new SimpleFactoryDemo1();
        //传名字入参
        //ICourse e = simpleFactoryDemo1.createCourse("english");
        //传类路径反射机制完成
        //ICourse e = simpleFactoryDemo1.create("com.design.pattern.model.ChineseCourse");
        //反射机制优化版本
        ICourse e = simpleFactoryDemo1.create(ChineseCourse.class);
        e.study();
    }
}
