package com.stopping.demo;

import com.stopping.demo.pojo.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindDemo {
    public static void main(String[] args) {
        reduceDemo();
    }
    /**
     * 所有stream用法
     * */
    public void filterDemo(){
        List<Integer> list = Arrays.asList(1,2,22,42,123,12,2);

        //filter 筛选元素
        list.stream().filter(x -> x > 20).forEach(System.out::println);
        System.out.println(list.stream().filter(x -> x > 20).max(Integer::max));
        //匹配第一个
        Optional<Integer> firstFind = list.stream().filter(x -> x > 20).findFirst();
        //匹配任意（适用并行流）
        Optional<Integer> firstAny = list.parallelStream().filter(x->x >20).findAny();
        //是否包含符合特定条件的元素
        Boolean b = list.stream().filter(x -> x > 20).anyMatch(x -> x > 140);

        System.out.println("匹配第一个:"+firstFind.get());
        System.out.println("匹配所有:"+firstAny.get());
        System.out.println("是否存在匹配结果："+b);
    }

    /**
     * 案例：筛选并且生成新的集合
     * */
    public static void filterAndCreate(){
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7000, 26, "female", "New York"));

        List<String> filterList = personList.stream().filter(x -> x.getSalary() > 8000).map(Person::getName).collect(Collectors.toList());
        System.out.println(filterList);
        //获取最大值
        Optional<Person> max = personList.stream().max(Comparator.comparingInt(Person::getSalary));
        System.out.println("最高薪资:"+max.get().getSalary());
        Optional<Person> min = personList.stream().min(Comparator.comparingInt(Person::getSalary));
        System.out.println("最低薪资:"+min.get().getSalary());
        //计算获取结果的个数
        Long count = personList.stream().filter(x -> x.getSalary() > 8000).count();
        System.out.println("薪资高于8000的员工人数:"+count);
    }

    /**
     * 案例二：映射
     * */
    public static void reflect(){
        String[] strArr = { "abcd", "bcdd", "defde", "fTr" };
        List<String> strList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());

        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> intListNew = intList.stream().map(x -> x + 3).collect(Collectors.toList());

        System.out.println("每个元素大写：" + strList);
        System.out.println("每个元素+3：" + intListNew);

        //员工薪资全部加1000
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7000, 26, "female", "New York"));

        // 不改变原来员工集合的方式
        List<Person> personListNew = personList.stream().map(
            person -> {
                Person personNew = new Person(person.getName(), person.getSalary()+10000, person.getAge(), person.getSex(), person.getArea());
//                personNew.setSalary(person.getSalary() + 10000);
                return personNew;
            }
        ).collect(Collectors.toList());
        System.out.println("一次改动前：" + personList.get(0).getName() + "-->" + personList.get(0).getSalary());
        System.out.println("一次改动后：" + personListNew.get(0).getName() + "-->" + personListNew.get(0).getSalary());
        //此案例没有new对象直接用stream里面的对象，person在此处是参数 用stream里面的值输入
        List<Person> personList1 = personList.stream().map(
                person -> {
                    person.setSalary(person.getSalary()+10000);
                    return person;
                }
        ).collect(Collectors.toList());
        System.out.println("二次改动前：" + personList.get(0).getName() + "-->" + personListNew.get(0).getSalary());
        System.out.println("二次改动后：" + personList1.get(0).getName() + "-->" + personList1.get(0).getSalary());
    }

    /**
     * 案例三:将两个字符数组合并成一个新的字符数组
     * */
    public static void towStringMergeOne(){
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> listNew = list.stream().flatMap(s -> {
            // 将每个元素转换成一个stream
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        }).collect(Collectors.toList());

        System.out.println("处理前的集合：" + list);
        System.out.println("处理后的集合：" + listNew);
    }

    /**
     * 案例三:归约 reduce 求和、求积、求最值
     * */
    public static void reduceDemo(){
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        //求和方式1
        Optional<Integer> sum2 = list.stream().reduce((x,y) -> x+y);
        System.out.println("求和方式1:"+sum2.get());
        //求和方式2
        Integer sum3 = list.stream().reduce(Integer::sum).get();
        System.out.println("求和方式2:"+sum3);

        //求积方式1
        Integer result1 = list.stream().reduce((x,y)->x*y).get();
        System.out.println("求积方式1:"+result1);

        //最大值
        Integer max1 = list.stream().reduce((x,y)->x>y?x:y).get();
        System.out.println("max:"+max1);
        //最大值2
        Integer max2 = list.stream().reduce(Integer::max).get();
        System.out.println("max2:"+max2);

        //所有员工的工资之和和最高工资
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

        //工资总和
        Integer salarySum =  personList.stream().map(Person::getSalary).reduce(Integer::sum).get();
        //工资求和 方式2
        Integer salarySum1 = personList.stream().reduce(0,(sum,person) -> sum += person.getSalary(),Integer::sum);
        //工资求和 方式3
        Integer salarySun2 = personList.stream().reduce(0,(sum,person)-> sum += person.getSalary() , (sum4,sum5) -> sum4 + sum5);
        System.out.println("工资总和1："+salarySum);
        System.out.println("工资总和2："+salarySum1);
        System.out.println("工资总和3："+salarySun2);

        //求最高工资
        Integer maxSalary1 = personList.stream().reduce(0,
                (max,person) -> max > person.getSalary() ? max : person.getSalary(),
                (s1,s2)->s1>s2?s1:s2);
        System.out.println("最高工资方式 1 ："+maxSalary1);

        Integer maxSalary2 = personList.stream().reduce(0,(max,p)->max>p.getSalary()?max : p.getSalary(),Integer::max);
        System.out.println("最高工资方式 2:"+maxSalary2);

    }
}
