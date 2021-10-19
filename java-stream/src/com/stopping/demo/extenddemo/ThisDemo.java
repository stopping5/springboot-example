package com.stopping.demo.extenddemo;

/**
 * @Description ThisDemo
 * @Author stopping
 * @date: 2021/2/24 23:17
 */

public class ThisDemo {
    static class Person {
        public void eat(Apple apple) {
            Apple peeled = apple.getPeeled();
            System.out.println("吃苹果~");
        }
    }

    static class Apple {
        public Apple getPeeled() {
            return Peeler.peel(this);
        }
    }

    static class Peeler {
        static Apple peel(Apple apple) {
            return apple;
        }
    }

    public static void main(String[] args) {
        new Person().eat(new Apple());
    }
}
