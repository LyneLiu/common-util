package com.lyne.barrier;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Barrier阻塞所有节点等待进程，直到某一个被满足，然后所有的节点继续进行。
 * 设置栅栏 setBarrier()  -->   需要阻塞的线程调用waitOnBarrier()等待放行条件   -->  移除栅栏，所有等待的线程继续执行
 *
 * @author nn_liu
 * @Created 2017-12-04-14:26
 */

public class DistributedBarrierExample {

    private static final int QTY = 5;
    private static final String PATH = "/examples/barrier";

    public static void main(String[] args) {
        try (TestingServer server = new TestingServer()){

            CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();

            ExecutorService service = Executors.newFixedThreadPool(QTY);
            DistributedBarrier controlBarrier = new DistributedBarrier(client, PATH);

            controlBarrier.setBarrier();

            for (int i = 0; i < QTY; i++) {
                final DistributedBarrier barrier = new DistributedBarrier(client, PATH);
                final int index = i;
                Callable<Void> task = new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {

                        Thread.sleep((long) (3 * Math.random()));
                        System.out.println("Client #" + index + " waits on Barriers");
                        barrier.waitOnBarrier();
                        System.out.println("Client #" + index + " begins");
                        return null;
                    }
                };

                service.submit(task);
            }

            Thread.sleep(10000);
            System.out.println("all Barrier instances should wait the condition");

            // 移除栅栏后，所有通过waitOnbarrier()阻塞的线程才继续执行。
            controlBarrier.removeBarrier();

            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

            service.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
