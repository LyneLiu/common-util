package com.lyne.leader.leaderselector;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author nn_liu
 * @Created 2017-11-30-19:31
 */

public class ExampleClient extends LeaderSelectorListenerAdapter implements Closeable {

    private final String name;

    private final LeaderSelector leaderSelector;

    private final AtomicInteger leaderCount = new AtomicInteger();

    public ExampleClient(String name, String path, CuratorFramework client) {
        this.name = name;
        this.leaderSelector = new LeaderSelector(client, path, this);
        // 保证当前实例释放领导权后还可能再次获得领导权
        leaderSelector.autoRequeue();
    }


    public void start() {
        leaderSelector.start();
    }

    public void close() throws IOException {
        leaderSelector.close();
    }

    public void takeLeadership(CuratorFramework client) throws Exception {
        final int waitSeconds = (int) (5 * Math.random()) + 1;
        System.out.println(name + " is now the leader. Waiting " + waitSeconds + " seconds...");
        System.out.println(name + " has been leader " + leaderCount.getAndIncrement() + " time(s) before. ");
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
        } catch (InterruptedException e) {
            System.err.println(name + " was interrupted.");
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(name + " relinquishing leadership.\n");
        }
    }
}
