package com.lyne.lambda;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by nn_liu on 2016/9/22.
 */

/**
 * program:apache tinkerpop
 * TriFunction匿名函数demo
 */
@FunctionalInterface
public interface TriFunction<A, B, C, R> {


    public R apply(final A a, final B b, final C c);

    public default <V> TriFunction<A, B, C, V> andThen(final Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c) -> after.apply(apply(a, b, c));
    }

}
