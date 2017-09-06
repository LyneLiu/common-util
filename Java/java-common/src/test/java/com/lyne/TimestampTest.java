package com.lyne;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by nn_liu on 2016/12/21.
 */


public class TimestampTest {

    private Logger logger = LoggerFactory.getLogger(TimestampTest.class);

    @Test
    public void testTimestamp() {
        /*Java Timestamp默认为13位，注意：时间戳的长度有位数的区分，比如10位、13位的时间戳*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        System.out.println(simpleDateFormat.format(new Date(1482305990225L)));
        try {
            int result = 1 / 0;
            System.out.println(result);
        } catch (Exception e) {
            logger.warn(String.format("calculate error:%s",e));
        }
    }

}
