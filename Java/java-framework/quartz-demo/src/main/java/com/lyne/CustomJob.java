package com.lyne;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.Map;

/**
 * @author nn_liu
 * @Created 2017-12-05-17:14
 */

@Slf4j
public class CustomJob implements Job {

    private String property;

    private int num = 0;

    public CustomJob(){
        num++;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        /**
         * ===================================================
         * JobDetail
         * ===================================================
         */
        log.info("Hello!  Custom Job is executing."+new Date());
        //取得job详情
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        // 取得job名称
        String jobName = jobDetail.getClass().getName();
        log.info("Name: " + jobDetail.getClass().getSimpleName());
        //取得job的类
        log.info("Job Class: " + jobDetail.getJobClass());
        //取得job开始时间
        log.info(jobName + " fired at " + jobExecutionContext.getFireTime());
        log.info("Next fire time " + jobExecutionContext.getNextFireTime());
        /**
         * ===================================================
         * JobDataMap
         * ===================================================
         */
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        log.info("job key:" + key);
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        for (Map.Entry<String, Object> data:dataMap.entrySet()) {
            log.info(data.getKey() + "=" + data.getValue());
        }

        /**
         * ===================================================
         * JobDataMap 设置job属性:
         * 每次trigger触发器触发的时候都会创建一个新的job实例，详见PropertySettingJobFactory。
         * When a trigger fires, the JobDetail (instance definition) it is associated to is loaded,
         * and the job class it refers to is instantiated via the JobFactory configured on the Scheduler.
         * The default JobFactory simply calls newInstance() on the job class,
         * then attempts to call setter methods on the class that match the names of keys within the JobDataMap.
         *
         * 参考链接：http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/tutorial-lesson-03.html
         * ===================================================
         */
        log.info("Job property:" + property);
        log.info("Job num:" + num);
    }

    public void startSchedule(){
        try {
            // 1、创建一个JobDetail实例，指定Quartz
            JobDetail jobDetail = JobBuilder.newJob(CustomJob.class)   // 任务执行类
                    .withIdentity("job1_1", "jGroup1")// 任务名，任务组
                    .usingJobData("desc", "test")
                    .usingJobData("date", String.valueOf(new Date()))
                    .build();

            jobDetail.getJobDataMap().put("property", "测试属性");

            //2、创建Trigger
            SimpleScheduleBuilder builder=SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(5)       //设置间隔执行时间
                    .repeatSecondlyForTotalCount(5);//设置执行次数

            Trigger trigger=TriggerBuilder.newTrigger()
                    .withIdentity("trigger1_1","tGroup1")
                    .startNow().withSchedule(builder).build();
            //3、创建Scheduler
            Scheduler scheduler= StdSchedulerFactory.getDefaultScheduler();
            //4、调度执行
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CustomJob().startSchedule();
    }
}
