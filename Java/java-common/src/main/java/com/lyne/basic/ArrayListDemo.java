package com.lyne.basic;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Java addAll(collection) vs new ArrayList(collection)
 *
 * 参考链接：
 * https://stackoverflow.com/questions/4238654/java-addallcollection-vs-new-arraylistcollection
 * @author nn_liu
 * @Created 2018-03-28-16:02
 */

public class ArrayListDemo {

    public static void main(String[] args) {

        List<String> arrs = Lists.newArrayList();
        arrs.add("1");
        arrs.add("2");
        arrs.add("3");
        arrs.add("4");

        System.out.println("arrs address: " + arrs);

        List<String> newArrs = Lists.newArrayList();

        newArrs.clear();
        newArrs.addAll(arrs);

        System.out.println("addAll address: " + newArrs);

        newArrs = new ArrayList<>(arrs);
        System.out.println("new ArrayList address: " + newArrs);
    }

}
