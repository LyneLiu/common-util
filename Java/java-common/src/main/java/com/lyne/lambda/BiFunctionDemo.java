package com.lyne.lambda;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by nn_liu on 2016/9/22.
 */

/**
 * BiFunction匿名函数使用demo
 */
public class BiFunctionDemo {


    public static void main(String[] args) {

        BiFunction<String, String, String> bi = (x, y) -> {
            return x + "^_^" + y;
        };

        Function<String, String> f = (x) -> {
            return x + " BiFunction andThen!";
        };

        System.out.println(bi.andThen(f).andThen(f).apply("luffy","zoro"));
    }

}
