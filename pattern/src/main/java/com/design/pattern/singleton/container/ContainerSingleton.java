package com.design.pattern.singleton.container;

import com.design.pattern.ConcurrentExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description ContainerSingleton 容器式单例 【注册式】
 * @Author stopping
 * @date: 2021/3/31 16:02
 */

public class ContainerSingleton {
    private ContainerSingleton() {
    }

    /**
     * 线程安全容器
     */
    private static ConcurrentMap<String, Object> ioc = new ConcurrentHashMap<>();

    /**
     * 通过类名查找对象
     */
    public static Object getInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //concurrentMap内部线程安全也就是put是线程安全
        //但是下面getbean的步骤并非安全，所以加了同步
        synchronized (ioc) {
            if (!ioc.containsKey(className)) {
                Object bean = Class.forName(className).newInstance();
                ioc.put(className, bean);
                return bean;
            }
            return ioc.get(className);
        }
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ConcurrentExecutor.execute(new ConcurrentExecutor.RunHandler() {
            @Override
            public void handler() {
                try {
                    Object o = ContainerSingleton.getInstance("com.design.pattern.model.EnglishNote");
                    System.out.println(System.currentTimeMillis() + ":" + o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 20000, 1000);
        long end = System.currentTimeMillis();
        System.out.println("时间:" + (end - start));
    }
}
