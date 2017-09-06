package com.lyne;

import java.util.logging.Logger;

/**
 * @author nn_liu
 * @Created 2017-08-18-18:41
 */

public class LogUtil {

    private final static Logger logger = Logger.getLogger("logback.xml");

    public static void warn(String msg){
        logger.warning(msg);
    }
    public static void info(String msg){
        logger.info(msg);
    }

}
