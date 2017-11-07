package com.lyne.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 通常使用double和float类型进行数据计算会存在各种各样的精度问题。
 * 推荐使用new BigDecimal(String val)创建数据。
 *
 * 参考链接：
 * https://stackoverflow.com/questions/10089927/how-to-insert-into-sql-server-decimal-column
 * http://superivan.iteye.com/blog/963628
 * @author nn_liu
 * @Created 2017-10-23-13:16
 */

public class BigDecimalDemo {

    public static void main(String[] args) {

        // 直接创建BigDecimal数据
        BigDecimal decimalData = new BigDecimal(0.05);
        BigDecimal decimalData1 = new BigDecimal(0.00000005);

        System.out.println("===============================");
        System.out.println(decimalData);  // result: 0.05000000000000000277555756156289135105907917022705078125
        System.out.println(decimalData1);  // result: 4.999999999999999773740559129431293428069693618454039096832275390625E-8

        // 通过String创建BigDecimal数据
        BigDecimal decimalDataFromStr = new BigDecimal("0.05");
        BigDecimal decimalDataFromStr1 = new BigDecimal("50.0");
        BigDecimal decimalDataFromStr2 = new BigDecimal("5.05");
        BigDecimal decimalDataFromStr3 = new BigDecimal("0.00000005");

        System.out.println("===============================");
        System.out.println(decimalDataFromStr);  // result:0.05
        System.out.println(decimalDataFromStr1);  // result:50.0
        System.out.println(decimalDataFromStr2);  // result:5.05
        System.out.println(decimalDataFromStr3);  // result:5E-8

        // 通过DecimalFormat指定精度
        BigDecimal decimalDataFormatter = new BigDecimal(new DecimalFormat("0.0000").format(0.05));
        BigDecimal decimalDataFormatter1 = new BigDecimal(new DecimalFormat("0.00000000").format(0.00000005));

        System.out.println("===============================");
        System.out.println(decimalDataFormatter);  // result:0.0500
        System.out.println(decimalDataFormatter1);  // result:5E-8
    }

}
