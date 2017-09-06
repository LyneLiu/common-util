package com.lyne.collection.list;

import java.util.*;

/**
 * Created by nn_liu on 2017/6/9.
 */
public class VectorCommon {

    /**
     * Vector是线程安全的，包含三个重要成员：elementData、elementCount、capacityIncrement：
     * 1、elementData是动态数组，保存数据；elementCount指动态数组的实际大小，默认的初始化大小为10；capacityIncrement为动态数组的增长系数，位指定的情况下为elementData.length；
     * 2、Vector的克隆函数，即是将全部元素克隆到一个数组中；
     * 3、Vector线程安全是相关方法通过关键字synchronized为数据访问加锁。
     */
    public static void vectorDemo() {
        List list = new Vector();
        for (int i = 0; i < 100000; i++)
            list.add(i);
        isRandomAccessSupported(list);
        iteratorThroughRandomAccess(list);
        iteratorThroughIterator(list);
        iteratorThroughFor2(list);
        iteratorThroughEnumeration((Vector) list);

    }

    private static void isRandomAccessSupported(List list) {
        if (list instanceof RandomAccess) {
            System.out.println("RandomAccess implemented!");
        } else {
            System.out.println("RandomAccess not implemented!");
        }

    }

    public static void iteratorThroughRandomAccess(List list) {

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

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (Object obj : list)
            ;
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughFor2：" + interval + " ms");
    }

    public static void iteratorThroughEnumeration(Vector vec) {

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (Enumeration enu = vec.elements(); enu.hasMoreElements(); ) {
            enu.nextElement();
        }
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughEnumeration：" + interval + " ms");
    }

}
