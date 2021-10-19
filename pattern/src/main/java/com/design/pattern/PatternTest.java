package com.design.pattern;

import com.design.pattern.singleton.hungry.HungrySingleton;
import com.design.pattern.singleton.lazy.ExecutorThread;
import com.design.pattern.singleton.lazy.LazyInnerClassSingleton;
import com.design.pattern.singleton.lazy.LazySingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Description PatternTest
 * @Author stopping
 * @date: 2021/3/16 23:20
 */

public class PatternTest {
    /**
     * 饿汉式调试
     * */
    /*public static void main(String[] args) {
        HungrySingleton hungrySingleton = HungrySingleton.getInstance();
        hungrySingleton.sayHello();
    }*/

    /**
     * 懒汉式调试
     */
    public static void main(String[] args) {
        /*Thread t1 = new Thread(new ExecutorThread());
        Thread t2 = new Thread(new ExecutorThread());
        t1.start();
        t2.start();*/
        //通过反射机制破解
        Class clazz = LazyInnerClassSingleton.class;
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object o1 = constructor.newInstance();
            Object o2 = constructor.newInstance();
            System.out.println("o1:" + o1);
            System.out.println("o2" + o2);
            System.out.println(o1 == o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
