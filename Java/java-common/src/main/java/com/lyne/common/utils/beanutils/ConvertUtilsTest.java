package com.lyne.common.utils.beanutils;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ConvertUtils的简单使用：
 * 这个工具类的职能是在字符串和指定类型的实例之间进行转换，已支持的类型可以在ConvertUtilsBean中查看到。
 *
 * Created by nn_liu on 2017/6/7.
 */
public class ConvertUtilsTest {

    public static void main(String[] args) {
        // date converter
        DateConverter dateConverter = new DateConverter();
        dateConverter.setUseLocaleFormat(true);
        dateConverter.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
        ConvertUtils.register(dateConverter,Date.class);
        Date date = (Date) ConvertUtils.convert("2017-06-01",Date.class);
        System.out.println(date.getTime());

        // boolean converter
        Boolean isTure = (Boolean) ConvertUtils.convert("true",Boolean.class);
        System.out.println(isTure);

        // integer converter
        Integer num = (Integer)ConvertUtils.convert("20",Integer.class);
        System.out.println(num);

        // bigdecimal converter
        BigDecimal decimal = (BigDecimal)ConvertUtils.convert("20.1563",BigDecimal.class);
        System.out.println(decimal);
    }

}
