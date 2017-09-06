package com.lyne.lambda;

import java.time.*;

/**
 * Java 8 时间处理相关类：
 * Clock、LocalDate、LocalTime、LocalDateTime、ZonedDateTime、Duration
 * Created by nn_liu on 2017/6/6.
 */
public class TimeDemo {

    public static void main(String[] args) {
        // get the system clock as UTC offset
        final Clock clock = Clock.systemUTC();
        final Clock defaultZoneClock = Clock.systemDefaultZone();
        System.out.println(clock.instant());
        System.out.println(clock.millis());

        System.out.println("===================================");

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

        // get the local date/time
        final LocalDateTime dateTime = LocalDateTime.now();
        final LocalDateTime dateTimeFromClock = LocalDateTime.now(defaultZoneClock);
        System.out.println(dateTime);
        System.out.println(dateTimeFromClock);

        System.out.println("===================================");

        // get zoned date/time
        final ZonedDateTime zonedDatetime = ZonedDateTime.now();
        final ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now(defaultZoneClock);
        final ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now(ZoneId.of( "America/Los_Angeles" ) );

        System.out.println(zonedDatetime);
        System.out.println(zonedDatetimeFromClock);
        System.out.println(zonedDatetimeFromZone);

        System.out.println("===================================");

        // get duration between two dates
        final LocalDateTime from = LocalDateTime.of(2014, Month.APRIL, 16, 0, 0, 0 );
        final LocalDateTime to = LocalDateTime.of(2016, Month.APRIL, 16, 0, 0, 0 );

        final Duration duration = Duration.between(from,to);
        System.out.println("Duration in days:" + duration.toDays());
        System.out.println("Duration in hours:" + duration.toHours());
        
    }

}
