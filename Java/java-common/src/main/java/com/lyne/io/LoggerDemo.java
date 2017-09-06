package com.lyne.io;


import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

/**
 * Created by nn_liu on 2016/10/28.
 */

/**
 * Logger的配置文件加载通过LogManager的static静态代码块加载。
 */
public class LoggerDemo {

    private static Logger logger1;

    private static Logger logger2;

    static {
        LogLog.setInternalDebugging(true);  //  可以看到log4j启动的内部日志
        System.out.println("start>>>>>>>>>>>>>>");
        logger1 = Logger.getLogger(LoggerDemo.class);
        System.out.println("end>>>>>>>>>>>>>>>>");
    }

    /*调用getLogger方法时，如果当前属性（String）不存在时，默认会创建new一个新的Logger，即new Logger(name)*/
    static {
        System.out.println("start..............");
        logger2 = Logger.getLogger("luffy");
        System.out.println("end................");
    }

    public static void main(String[] args) {
        logger2.info("for logger test!");
    }

}
