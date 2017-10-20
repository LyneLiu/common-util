package com.lyne.lambda;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Comparator;

/**
 * 开发中通常会有比较字段是否相等的使用场景。
 * Comparable vs Comparator:
 * Comparable表示当前对象可以与另外一个对象相比较；
 * Comparator通常可以用来定义对象特定属性的比较，比如，sort(),TreeMap()等使用场景。
 * 参考链接：
 * https://www.programcreek.com/2011/12/examples-to-demonstrate-comparable-vs-comparator-in-java/
 *
 * @author nn_liu
 * @Created 2017-10-20-13:12
 */

public class ComparatorDemo {


    /**
     * 通过ObjectUtils工具对Integer、String等primitive类型进行判断
     */
    public static void compareWithObjectUtils(){

        // null
        System.out.println("compare 'null' result:" + ObjectUtils.compare(null, null));

        // Integer
        System.out.println("compare 'Integer' result:" + ObjectUtils.compare(new Integer(1), new Integer(2)));
        System.out.println("compare 'Integer' result:" + ObjectUtils.compare(new Integer(1), new Integer(1)));
        System.out.println("compare 'Integer' result:" + ObjectUtils.compare(new Integer(1), 1));

        //String
        System.out.println("compare 'String' result:" + ObjectUtils.compare(new String("test"), null));
        System.out.println("compare 'String' result:" + ObjectUtils.compare(new String("test"), new String("test")));
        System.out.println("compare 'String' result:" + ObjectUtils.compare(new String("test"), new String("test-a")));
    }


    public static void compareWithComparator(){
        // TODO: 创建Comparable对象，以及Comparator使用示例
    }


    public static void main(String[] args) {
        compareWithObjectUtils();

    }

}
