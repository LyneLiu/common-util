package com.lyne.collection.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by nn_liu on 2017/6/9.
 */
public class LinkedListCommon {

    /**
     * LinkedList(AbstractSequentialList)双向链表，重要成员first、last和size，每个成员为一个Node:
     * 1、implement List接口的get(int location)和remove(int location)方法，通过索引获取、删除节点，
     * 实现原理为首先比较“location”和“双向链表长度的1/2”；若前者大，则从链表头开始（first）往后查找，直到location位置；否则，从链表末尾（last）开始先前查找，直到location位置。
     * 2、非线程安全，通过AbstractList中expectedModCount = modCount控制访问。
     */
    public static void linkedListDemo() {
        List linkedList = new LinkedList();

        // 通过Iterator遍历LinkedList
        iteratorLinkedListThruIterator(getLinkedList());

        // 通过快速随机访问遍历LinkedList(效率最低)
        iteratorLinkedListThruForeach(getLinkedList());

        // 通过for循环的变种来访问遍历LinkedList
        iteratorThroughFor2(getLinkedList());

        // 通过PollFirst()遍历LinkedList
        iteratorThroughPollFirst(getLinkedList());

        // 通过PollLast()遍历LinkedList
        iteratorThroughPollLast(getLinkedList());

        // 通过removeFirst()遍历LinkedList
        iteratorThroughRemoveFirst(getLinkedList());

        // 通过removeLast()遍历LinkedList
        iteratorThroughRemoveLast(getLinkedList());

    }

    private static LinkedList getLinkedList() {
        LinkedList llist = new LinkedList();
        for (int i = 0; i < 100000; i++)
            llist.addLast(i);

        return llist;
    }

    /**
     * 通过快迭代器遍历LinkedList
     */
    private static void iteratorLinkedListThruIterator(LinkedList<Integer> list) {
        if (list == null)
            return;

        // 记录开始时间
        long start = System.currentTimeMillis();

        for (Iterator iter = list.iterator(); iter.hasNext(); )
            iter.next();

        // 记录结束时间
        long end = System.currentTimeMillis();
        long interval = end - start;
        System.out.println("iteratorLinkedListThruIterator：" + interval + " ms");
    }

    /**
     * 通过快速随机访问遍历LinkedList
     */
    private static void iteratorLinkedListThruForeach(LinkedList<Integer> list) {
        if (list == null)
            return;

        // 记录开始时间
        long start = System.currentTimeMillis();

        int size = list.size();
        for (int i = 0; i < size; i++) {
            list.get(i);
        }
        // 记录结束时间
        long end = System.currentTimeMillis();
        long interval = end - start;
        System.out.println("iteratorLinkedListThruForeach：" + interval + " ms");
    }

    /**
     * 通过另外一种for循环来遍历LinkedList
     */
    private static void iteratorThroughFor2(LinkedList<Integer> list) {
        if (list == null)
            return;

        // 记录开始时间
        long start = System.currentTimeMillis();

        for (Integer integ : list)
            ;

        // 记录结束时间
        long end = System.currentTimeMillis();
        long interval = end - start;
        System.out.println("iteratorThroughFor2：" + interval + " ms");
    }

    /**
     * 通过pollFirst()来遍历LinkedList
     */
    private static void iteratorThroughPollFirst(LinkedList<Integer> list) {
        if (list == null)
            return;

        // 记录开始时间
        long start = System.currentTimeMillis();
        while (list.pollFirst() != null)
            ;

        // 记录结束时间
        long end = System.currentTimeMillis();
        long interval = end - start;
        System.out.println("iteratorThroughPollFirst：" + interval + " ms");
    }

    /**
     * 通过pollLast()来遍历LinkedList
     */
    private static void iteratorThroughPollLast(LinkedList<Integer> list) {
        if (list == null)
            return;

        // 记录开始时间
        long start = System.currentTimeMillis();
        while (list.pollLast() != null)
            ;

        // 记录结束时间
        long end = System.currentTimeMillis();
        long interval = end - start;
        System.out.println("iteratorThroughPollLast：" + interval + " ms");
    }

    /**
     * 通过removeFirst()来遍历LinkedList
     */
    private static void iteratorThroughRemoveFirst(LinkedList<Integer> list) {
        if (list == null)
            return;

        // 记录开始时间
        long start = System.currentTimeMillis();
        try {
            while (list.removeFirst() != null)
                ;
        } catch (NoSuchElementException e) {
        }

        // 记录结束时间
        long end = System.currentTimeMillis();
        long interval = end - start;
        System.out.println("iteratorThroughRemoveFirst：" + interval + " ms");
    }

    /**
     * 通过removeLast()来遍历LinkedList
     */
    private static void iteratorThroughRemoveLast(LinkedList<Integer> list) {
        if (list == null)
            return;

        // 记录开始时间
        long start = System.currentTimeMillis();
        try {
            while (list.removeLast() != null)
                ;
        } catch (NoSuchElementException e) {
        }

        // 记录结束时间
        long end = System.currentTimeMillis();
        long interval = end - start;
        System.out.println("iteratorThroughRemoveLast：" + interval + " ms");
    }


}
