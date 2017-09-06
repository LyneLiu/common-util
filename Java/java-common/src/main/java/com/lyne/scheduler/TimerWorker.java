package com.lyne.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

/**
 * Created by nn_liu on 2016/9/20.
 */

/**
 * JDK Timer：JDK自带的任务调度，可以实现简单的任务调度，如果实现比较复杂的可以考虑Quartz
 * 组成部分：任务（TimerTask）、任务队列（TaskQueue）和任务调度者（TimerThread）
 */
public class TimerWorker extends TimerTask {

    @Override
    public void run() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("current system time:" + simpleDateFormat.format(new Date()));
    }

    public static void main(String[] args) {
        Timer timer =  new Timer();
        TimerWorker worker = new TimerWorker();
        /*schedule任务调度方法：下一次执行时间相对于上一次实际执行完成的时间点，所以执行时间会不断延后*/
        timer.schedule(worker,1000,2000);
        /*scheduleAtFixedRate任务调度方法：下一次执行时间相对于上一次实际执行开始的时间点，所以执行时间不会延后，存在并发性*/
        timer.scheduleAtFixedRate(worker,1000,2000);
    }
}
