package com.lyne.lambda;

import com.google.common.collect.Lists;
import com.lyne.common.utils.DateUtil;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Java 8 时间处理相关类：
 * Clock、LocalDate、LocalTime、LocalDateTime、ZonedDateTime、Duration
 * Created by nn_liu on 2017/6/6.
 */
public class TimeDemo {

    private static final Clock defaultZoneClock = Clock.systemDefaultZone();

    public static void main(String[] args) {

        Date begin = new Date();
        Date end = new Date("2017/11/21");

        List<Date> dates = DateUtil.getDurationDays(begin, end);

        for (Date date : dates) {
            System.out.println(date);
        }

    }

    /**
     * 获取begain和end时间区间的所有日期
     * @param begain
     * @param end
     * @return
     */
    public static List<Date> getDuration(Date begain, Date end){
        List<Date> lDate = Lists.newArrayList();
        lDate.add(begain);
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(begain);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        while (end.after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    public static void testClock(){
        // get the system clock as UTC offset
        final Clock clock = Clock.systemUTC();

        System.out.println(clock.instant());
        System.out.println(clock.millis());

        System.out.println("===================================");
    }

    public static void testDuration(){
        // get duration between two dates
        final LocalDateTime from = LocalDateTime.of(2014, Month.APRIL, 16, 0, 0, 0 );
        final LocalDateTime to = LocalDateTime.of(2016, Month.APRIL, 16, 0, 0, 0 );

        final Duration duration = Duration.between(from,to);
        System.out.println("Duration in days:" + duration.toDays());
        System.out.println("Duration in hours:" + duration.toHours());
    }

    public static void testLocalDate(){
        // get the local date and local time
        final LocalDate date = LocalDate.now();
        final LocalDate dateFromClock = LocalDate.now(defaultZoneClock);
        System.out.println(date);
        System.out.println(dateFromClock);

        final LocalTime time = LocalTime.now();
        final LocalTime timeFromClock = LocalTime.now(defaultZoneClock);
        System.out.println(time);
        System.out.println(timeFromClock);

        System.out.println("===================================");
    }

    public static void testZonedDateTime(){
        // get zoned date/time
        final ZonedDateTime zonedDatetime = ZonedDateTime.now();
        final ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now(defaultZoneClock);
        final ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now(ZoneId.of( "America/Los_Angeles" ) );

        System.out.println(zonedDatetime);
        System.out.println(zonedDatetimeFromClock);
        System.out.println(zonedDatetimeFromZone);

        System.out.println("===================================");
    }

    public static void testLocalDateTime(){
        // get the local date/time
        final LocalDateTime dateTime = LocalDateTime.now();
        final LocalDateTime dateTimeFromClock = LocalDateTime.now(defaultZoneClock);
        System.out.println(dateTime);
        System.out.println(dateTimeFromClock);

        System.out.println("===================================");
    }

}
