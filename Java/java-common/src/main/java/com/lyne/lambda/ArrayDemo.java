package com.lyne.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Java 8 lambda filter/map/reduce 特性
 * Created by nn_liu on 2017/6/6.
 */
public class ArrayDemo {

    public static void main(String[] args) {
        System.out.println("================ foreach ===================");
        List<String> list= Arrays.asList("c","b","d","a");
        list.forEach(e -> System.out.println(e));
        System.out.println("================ sort ===================");
        list.sort((e1, e2) -> e1.compareTo(e2));
        list.forEach(e -> System.out.println(e));

        System.out.println("================ filter ===================");
        List<String> filteredList = list.stream().filter(e -> e.compareTo("c") >= 0).collect(Collectors.toList());
        filteredList.forEach(e -> System.out.println(e));

        System.out.println("================ map ===================");
        List<String> mappedList = list.stream().mapToInt(e -> (int)e.charAt(0) + 1).mapToObj(e -> String.valueOf(Character.toChars(e))).collect(Collectors.toList());
        mappedList.forEach(e -> System.out.println(e));

        System.out.println("================ reduce ===================");
        Integer sum = list.stream().mapToInt(e -> (int)e.charAt(0)).boxed().reduce(0,Integer :: sum);
        System.out.println(sum);

    }
}
