package com.lyne.multithread.timer;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author nn_liu
 * @Created 2017-11-07-15:50
 */

@Slf4j public class TimerSchedulerDemo {

    private Timer timer = new Timer();

    private Map<String, TimerTask> tasks = Maps.newConcurrentMap();

    public static void main(String[] args) throws InterruptedException {

        TimerTask task1 = new TimerTask() {
            @Override public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        log.info("task#01>>>", "num_1_" + i);
                        SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        log.info("task#02###", "num_2_" + i);
                        SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        TimerTask task3 = new TimerTask() {
            @Override public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        log.info("task#03@@@", "num_3_" + i);
                        SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        TimerSchedulerDemo demo = new TimerSchedulerDemo();
        demo.tasks.put("task1", task1);
        demo.tasks.put("task2", task2);
        demo.tasks.put("task3", task3);

        for (Map.Entry<String, TimerTask> entry : demo.tasks.entrySet()) {
            demo.timer.schedule(entry.getValue(), 30L, 30L);
        }

        SECONDS.sleep(20);
        //cancel指定线程
        demo.tasks.get("task2").cancel();

    }

}
