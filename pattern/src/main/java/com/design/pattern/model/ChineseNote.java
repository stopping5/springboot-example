package com.design.pattern.model;

/**
 * @Description ChineseNote
 * @Author stopping
 * @date: 2021/3/12 0:21
 */

public class ChineseNote implements INote {
    @Override
    public void edit() {
        System.out.println("edit Chinese Note");
    }
}
