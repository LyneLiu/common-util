package com.lyne.scheduler.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nn_liu on 2016/12/5.
 */

/**
 * 通过Job定义任务：输出当前日期的demo
 */
public class MyJob implements Job {

    private static final Logger logger = Logger.getLogger(MyJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        logger.warn(simpleDateFormat.format(new Date()));
    }
}
