package com.lyne.common;


/**
 * Created by nn_liu on 2016/9/22.
 */

/**
 * 关于Java数字类型精度的问题
 */
public class DoubleDemo {

    public static void main(String[] args) {

        /*Note:2的指数或者幂数才会在计算机中准确表示并进行运算*/
        System.out.println(0.1 + (0.2 + 0.3));
        System.out.println((0.1 + 0.2) + 0.3);

    }
}
