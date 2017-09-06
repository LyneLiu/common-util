package com.lyne.collection.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * fail-fast机制：
 * java集合(Collection)中的一种错误机制。当多个线程对同一个集合的内容进行操作时，就可能会产生fail-fast事件。
 * http://www.cnblogs.com/skywang12345/p/3323085.html
 * Created by nn_liu on 2017/6/8.
 */
public class FastFailDemo {

    /**
     *
     * ArrayList单线程安全，多线程并发访问的情况下会出现ConcurrentModificationException异常。
     * Note：AbstractList中expectedModCount = modCount;控制iterator访问。
     */
    //private static List<String> list = new ArrayList<String>();

    /**
     * CopyOnWriteArrayList多线程可以并发访问，但是存在数据不一致的问题。
     * Note：COWIterator通过snapshot = elements;的方式存储获取到的副本，并修改snapshot。
     */
    private static List<String> list = new CopyOnWriteArrayList<String>();

    //private static Vector<String> list = new Vector<>();

    public static void main(String[] args) {

        // 同时启动三个线程对list进行操作！
        // 注：在多核的情况下，一两个线程的并发问题好像不容易暴漏出来，所以创建三个线程对list并发操作。
        new ThreadOne().start();
        new ThreadTwo().start();
        new ThreadThree().start();
    }

    private static void printAll() {
        System.out.println("");

        String value = null;
        Iterator iter = list.iterator();
        while(iter.hasNext()) {
            value = (String)iter.next();
            System.out.print(value+", ");
        }
    }

    /**
     * 向list中依次添加0,1,2,3,4,5，每添加一个数之后，就通过printAll()遍历整个list
     */
    private static class ThreadOne extends Thread {
        public void run() {
            int i = 0;
            while (i<6) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add(String.valueOf(i));
                printAll();
                i++;
            }
        }
    }

    /**
     * 向list中依次添加10,11,12,13,14,15，每添加一个数之后，就通过printAll()遍历整个list
     */
    private static class ThreadTwo extends Thread {
        public void run() {
            int i = 10;
            while (i<16) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add(String.valueOf(i));
                printAll();
                i++;
            }
        }
    }

    /**
     * 向list中依次添加20,21,22,23,24,25，每添加一个数之后，就通过printAll()遍历整个list
     */
    private static class ThreadThree extends Thread {
        public void run() {
            int i = 20;
            while (i<26) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add(String.valueOf(i));
                printAll();
                i++;
            }
        }
    }


}
