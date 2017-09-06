package com.lyne.scheduler.quartz;

/**
 * Created by nn_liu on 2016/12/5.
 */

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Job代理中心
 */
public class JobAgent {

    public void executeJob() throws SchedulerException {
        // 首先，必需要取得一个Scheduler的引用
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        //jobs可以在scheduled的sched.start()方法前被调用

        //job 1将每隔20秒执行一次
        JobDetail job = newJob(MyJob.class).withIdentity("date_job_1", "date_group").build();
        CronTrigger trigger = newTrigger().withIdentity("date_trigger_1", "date_group").withSchedule(cronSchedule("0/20 * * * * ?")).build();
        Date ft = sched.scheduleJob(job, trigger);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        System.out.println(job.getKey() + " 已被安排执行于: " + sdf.format(ft) + "，并且以如下重复规则重复执行: " + trigger.getCronExpression());

        // job 2将每2分钟执行一次（在该分钟的第15秒)
        job = newJob(MyJob.class).withIdentity("date_job_2", "date_group").build();
        trigger = newTrigger().withIdentity("date_trigger_2", "date_group").withSchedule(cronSchedule("15 0/2 * * * ?")).build();
        ft = sched.scheduleJob(job, trigger);
        System.out.println(job.getKey() + " 已被安排执行于: " + sdf.format(ft) + "，并且以如下重复规则重复执行: "+ trigger.getCronExpression());

        // 开始执行，start()方法被调用后，计时器就开始工作，计时调度中允许放入N个Job
        sched.start();
        try {
            //主线程等待一分钟
            Thread.sleep(60L * 1000L);
        } catch (Exception e) {}
        //关闭定时调度，定时器不再工作
        sched.shutdown(true);
    }

    public static void main(String[] args) throws SchedulerException {
        JobAgent jobAgent = new JobAgent();
        jobAgent.executeJob();
    }


}
