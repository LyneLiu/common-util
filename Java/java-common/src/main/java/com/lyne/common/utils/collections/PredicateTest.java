package com.lyne.common.utils.collections;


import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.EqualPredicate;

/**
 * 函数式编程之Predicate（断言）
 * Created by nn_liu on 2017/6/7.
 */
public class PredicateTest {

    public static void main(String[] args) {
        // 相等判断
        Predicate<String> pre = EqualPredicate.equalPredicate("viking");
        boolean ret = pre.evaluate(new String("test"));
        System.out.println(ret);

        // Java 8也提供了断言的功能
        java.util.function.Predicate<String> funPre = java.util.function.Predicate.isEqual("test");
        boolean result = funPre.equals(new String("viking"));
        System.out.println(result);
    }

}
