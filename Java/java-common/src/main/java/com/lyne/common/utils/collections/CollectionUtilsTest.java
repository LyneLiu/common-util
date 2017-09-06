package com.lyne.common.utils.collections;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nn_liu on 2017/6/7.
 */
public class CollectionUtilsTest {

    public static void main(String[] args) {

        List<Integer> numberList = new ArrayList<>();
        numberList.add(3);
        numberList.add(5);
        numberList.add(9);

        Closure printClosure = new Closure() {
            @Override
            public void execute(Object input) {
                System.out.println(input);
            }
        };

        CollectionUtils.forAllDo(numberList, printClosure);

        List<String> strList = new ArrayList<>();
        strList.add("luffy");
        strList.add("zoro");

        strList.stream().map(e -> e + " -- The Straw Hats").forEach(e -> System.out.println(e));

    }

}
