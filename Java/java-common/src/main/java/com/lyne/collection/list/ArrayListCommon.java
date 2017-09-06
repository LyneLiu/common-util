package com.lyne.collection.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/**
 * Created by nn_liu on 2017/6/9.
 */
public class ArrayListCommon {

    /**
     * ArrayList(AbstractList)包含了两个重要的对象elementData 和 size:
     * 1、ArrayList通过elementData这个对象数组保存数据，使用默认构造函数的情况下，初始化容量大小为10；
     * 2、当使用add(element)添加元素的时候，容量不足以容纳全部元素的情况下，会重新设置容量：新的容量=“(原始容量x3)/2 + 1”；
     * 3、ArrayList的克隆函数，即是将全部元素克隆到一个数组中；
     * 4、ArrayList实现java.io.Serializable的方式。当写入到输出流时，先写入“容量”，再依次写入“每一个元素”；当读出输入流时，先读取“容量”，再依次读取“每一个元素”;
     * 5、非线程安全，通过AbstractList中expectedModCount = modCount控制访问。
     */
    public static void arrayListDemo() {
        List list = new ArrayList();
        for (int i = 0; i < 100000; i++)
            list.add(i);

        arrayListAPIDemo();

        // 随机访问(即，通过索引序号访问)效率最高，而使用迭代器的效率最低！
        System.out.println("\nAccess efficiency test: >>>>>>");
        isRandomAccessSupported(list);
        iteratorThroughRandomAccess(list);
        iteratorThroughIterator(list);
        iteratorThroughFor2(list);

    }

    private static void isRandomAccessSupported(List list) {
        if (list == null)
            return;

        if (list instanceof RandomAccess) {
            System.out.println("RandomAccess implemented!");
        } else {
            System.out.println("RandomAccess not implemented!");
        }

    }

    public static void iteratorThroughRandomAccess(List list) {
        if (list == null)
            return;

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            list.get(i);
        }
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughRandomAccess：" + interval + " ms");
    }

    public static void iteratorThroughIterator(List list) {
        if (list == null)
            return;

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            iter.next();
        }
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughIterator：" + interval + " ms");
    }


    public static void iteratorThroughFor2(List list) {
        if (list == null)
            return;

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (Object obj : list)
            ;
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughFor2：" + interval + " ms");
    }

    private static void arrayListAPIDemo() {
        // 创建ArrayList
        ArrayList list = new ArrayList();

        // 添加元素
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        // 将下面的元素添加到第1个位置
        list.add(0, "5");

        // 获取第1个元素
        System.out.println("the first element is: " + list.get(0));
        // 删除“3”
        list.remove("3");
        // 获取ArrayList的大小
        System.out.println("Arraylist size=: " + list.size());
        // 判断list中是否包含"3"
        System.out.println("ArrayList contains 3 is: " + list.contains(3));
        // 设置第2个元素为10
        list.set(1, "10");

        // 通过Iterator遍历ArrayList
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            System.out.println("next is: " + iter.next());
        }

        // 将ArrayList转换为数组
        String[] arr = (String[]) list.toArray(new String[0]);
        for (String str : arr)
            System.out.println("str: " + str);

        // 清空ArrayList
        list.clear();
        // 判断ArrayList是否为空
        System.out.println("ArrayList is empty: " + list.isEmpty());
    }

}
