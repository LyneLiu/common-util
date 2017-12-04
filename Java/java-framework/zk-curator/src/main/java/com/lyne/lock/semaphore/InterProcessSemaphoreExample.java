package com.lyne.lock.semaphore;

import com.lyne.lock.FakeLimitedResource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 信号量Shared Semaphore：
 * JDK中Semaphore维护的一组许可(permits)，而Cubator中称之为租约(Lease)。
 * @author nn_liu
 * @Created 2017-12-04-11:01
 */

public class InterProcessSemaphoreExample {

    private static final int MAX_LEASE = 10;
    private static final String PATH = "/examples/locks";

    public static void main(String[] args) {
        FakeLimitedResource resource = new FakeLimitedResource();

        try (TestingServer server = new TestingServer()){

            CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));

            client.start();

            InterProcessSemaphoreV2 semaphoreV2 = new InterProcessSemaphoreV2(client, PATH, MAX_LEASE);

            Collection<Lease> leases = semaphoreV2.acquire(5);

            System.out.println("get " + leases.size() + " leases");

            Lease lease = semaphoreV2.acquire();

            System.out.println("get another lease");

            resource.use();

            // 已经请求6个租约（Lease）的情况下，还剩下4个租约，这个时候请求5个租约，因为租约不够，阻塞到超时，还是没能满足，返回结果为null
            Collection<Lease> leases2 = semaphoreV2.acquire(5, 10, TimeUnit.SECONDS);
            System.out.println("Should timeout and acquire return " + leases2);

            System.out.println("return one release");
            semaphoreV2.returnLease(lease);
            System.out.println("return another 5 leases");
            semaphoreV2.returnAll(leases);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
