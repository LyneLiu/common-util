package com.lyne.common;


/**
 * Created by nn_liu on 2016/9/22.
 */

import java.math.BigDecimal;

/**
 * 1、关于Java数字类型精度的问题
 *
 * 2、当使用double、float或者int存储数据的时候，会有比较的场景（==、=<、>=、>等等），
 * double和float数据会存在精度的问题，比较的方案有两种：
 *    # 将数据统一转换为Double类型，通过compareTo进行数据的比较；
 *    # 将数据统一转换为BigDecimal类型，通过compareTo进行数据的比较。
 * Note：BigDecimal类型对数据的处理相对比较强大，但是可能会存在性能方面的问题，占用更多的存储空间。
 *
 * 3、整数类型计算获取long类型的数据，通常需要注意下类型转换。
 */
public class DoubleDemo {

    private static int intNum = 10;
    private static double doubleNum = 10.0;

    public static void compareDoubleAndInteger(){
        System.out.println("=================Double类型比较====================");
        System.out.println(Double.valueOf(Integer.valueOf(intNum).toString()).compareTo(Double.valueOf(doubleNum)));
    }

    public static void compareWithBigDecimal(){
        System.out.println("================BigDecimal类型比较==================");
        System.out.println(BigDecimal.valueOf(intNum).compareTo(BigDecimal.valueOf(doubleNum)));
    }

    public static void calculateForLong(){
        System.out.println("==================整数类型计算===================");
        // long cal = 24 * 60 * 60 * 1000;   /* bad code */
        long cal = 24 * 60 * 60 * 1000L;   /* better code */
        System.out.println(cal);
    }

    public static void doubleAccuracy(){
        System.out.println("==================Double类型精度===================");
        /*Note:2的指数或者幂数才会在计算机中准确表示并进行运算*/
        System.out.println(0.1 + (0.2 + 0.3));
        System.out.println((0.1 + 0.2) + 0.3);
    }

    public static void main(String[] args) {

        compareDoubleAndInteger();

        compareWithBigDecimal();

        calculateForLong();

        doubleAccuracy();

    }
}
