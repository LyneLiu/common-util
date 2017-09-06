package com.lyne.lambda;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by nn_liu on 2016/9/22.
 */
public class TriFunctionDemo {

    public static void main(String[] args) {

        TriFunction<String, String, String, String> tri = (x, y, z) -> {
            return x + "^_^" + y + "~#~" + z;
        };

        Function<String, String> f = (x) -> {
            return x + " BiFunction andThen!";
        };

        System.out.println(tri.andThen(f).andThen(f).apply("luffy", "zoro", "nami"));
    }

}
