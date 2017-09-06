package com.lyne.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nn_liu on 2017/1/19.
 */
public class DateAndCalendarDemo {

    public static void main(String[] args) throws ParseException {
        //将日期转换为指定格式输出
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
        String date1 = simpleDateFormat.format(date);
        System.out.println(date1);
        //字符串日期转换成日期格式输出
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
        Date date2 = simpleDateFormat1.parse(date1);
        System.out.println(date2);


        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());  //每刷新一次时间秒数会发生对应的改变 Date类构建的日期的秒数不变
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        System.out.println("当前时间：" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
        System.out.println(c.getTimeInMillis());

    }

}
