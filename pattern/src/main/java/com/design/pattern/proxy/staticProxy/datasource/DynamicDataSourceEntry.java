package com.design.pattern.proxy.staticProxy.datasource;

/**
 * @Description DynamicDataSourceEntry
 * @Author stopping
 * @date: 2021/3/31 20:18
 */

public class DynamicDataSourceEntry {
    //默认数据源
    public final static String DEFAULT_SOURCE = null;

    private final static ThreadLocal<String> local = new ThreadLocal<>();

    private DynamicDataSourceEntry() {
    }

    /**
     * 清空数据源
     */
    public static void clear() {
        local.remove();
    }

    /**
     * 获取当前正在使用的数据源
     */
    public static String get() {
        return local.get();
    }

    /**
     * 还原当前切面的数据源
     */
    public static void restore() {
        local.set(DEFAULT_SOURCE);
    }

    /**
     * 设置已知的数据源
     */
    public static void set(String source) {
        local.set(source);
    }

    public static void set(int year) {
        local.set("DB_" + year);
    }
}
