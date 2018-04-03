package com.lyne.basic;

/**
 *
 * If either operand is of type double, the other is converted to double.
 * Otherwise, if either operand is of type float, the other is converted to float.
 * Otherwise, if either operand is of type long, the other is converted to long.
 * Otherwise, both operands are converted to type int.
 *
 * @author nn_liu
 * @Created 2018-03-27-18:45
 */

public class ByteDemo {

    public static void main(String[] args) {
        byte b1 = 1 , b2 = 2, b3, b6, b8;
        final byte b4 = 4, b5 = 6, b7;
        // b3 = (b1 + b2); // 自动向上类型转换，结果需要强制向下类型转换
        b6 = b4 + b5;  // final不会向上类型转换
        // b8 = (b1 + b4);
        // b7 = (b2 + b5);
        // System.out.println(b3 + b6);
    }

}
