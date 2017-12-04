package com.lyne.barrier;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * DistributedDoubleBarrier当足够的线程加入到双栅栏时，进程开始计算，当计算完成后，离开栅栏。
 *
 * 当栅栏中线程数据不足时会阻塞进程，直至足够的线程调用enter方法进入栅栏。
 *
 * @author nn_liu
 * @Created 2017-12-04-14:57
 */

public class DistributionDoubleBarrierExample {

    private static final int QTY = 5;
    private static final String PATH = "/examples/barrier";

    public static void main(String[] args) {
        try (TestingServer server = new TestingServer()) {

            CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();

            ExecutorService service = Executors.newFixedThreadPool(QTY);

            for (int i = 0; i < QTY + 1; i++) {
                final DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, PATH, QTY);
                final int index = i;
                Callable<Void> task = new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {

                        Thread.sleep((long) (3 * Math.random()));
                        System.out.println("Client #" + index + " enters");
                        // Enter the barrier and block until all members have entered
                        barrier.enter();
                        System.out.println("Client #" + index + " begins");
                        Thread.sleep((long) (3000 * Math.random()));
                        // Leave the barrier and block until all members have left
                        barrier.leave();
                        System.out.println("Client #" + index + " left");
                        return null;
                    }
                };
                service.submit(task);
            }

            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

            service.shutdown();
            service.awaitTermination(10, TimeUnit.MINUTES);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
