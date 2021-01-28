package com.stopping.demo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class createStream {
    public Stream<String> craeteStream(){
        List list = Arrays.asList("a","b","c");
        //创建顺序流
        Stream<String> stringStream = list.stream();
        //创建并行流
        Stream<String> parallelStream = list.parallelStream();
        //通过数组方式创建
        int [] array = {1,2,3,4,5,6,7};
        IntStream intStream = Arrays.stream(array);
        //使用Stream的静态方法 of
        Stream<Integer> ofStream = Stream.of(1,3,45,2,1,23);
        //使用Stream的静态方法 iterate
        Function<Integer, Integer> function1 = x -> x * 2;
        Stream.iterate(0,(x) -> x + 3).limit(4);
        return stringStream;
    }

    public static void main(String[] args) {
        System.out.println("xxx");
    }
}
