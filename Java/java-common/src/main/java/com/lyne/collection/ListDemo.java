package com.lyne.collection;

import com.lyne.collection.list.ArrayListCommon;
import com.lyne.collection.list.LinkedListCommon;
import com.lyne.collection.list.StackCommon;
import com.lyne.collection.list.VectorCommon;

/**
 * ArrayList、LinkedList、Vector、Stack：
 * <p>
 * Created by nn_liu on 2017/6/8.
 */
public class ListDemo {

    public static void main(String[] args) {

        // ArrayList
        System.out.println("============= ArrayList ==============");
        ArrayListCommon.arrayListDemo();

        // LinkedList
        System.out.println("============= LinkedList ==============");
        LinkedListCommon.linkedListDemo();

        // Vector
        System.out.println("============= Vector ==============");
        VectorCommon.vectorDemo();

        // Stack
        System.out.println("============= Stack ==============");
        StackCommon.stackDemo();

    }

}
