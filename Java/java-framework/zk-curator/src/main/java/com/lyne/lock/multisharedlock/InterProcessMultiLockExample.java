package com.lyne.lock.multisharedlock;

import com.lyne.lock.FakeLimitedResource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Multi Shared Lock是一个锁的容器。
 * 当调用acquire， 所有的锁都会被acquire，如果请求失败，所有的锁都会被release。
 * 同样调用release时所有的锁都被release(失败被忽略)。
 *
 * @author nn_liu
 * @Created 2017-12-04-11:49
 */
public class InterProcessMultiLockExample {

    private static final String PATH1 = "/examples/locks1";
    private static final String PATH2 = "/examples/locks2";

    public static void main(String[] args) {
        FakeLimitedResource resource = new FakeLimitedResource();
        try (TestingServer server = new TestingServer()) {
            CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();

            InterProcessLock lock1 = new InterProcessMutex(client, PATH1);
            InterProcessLock lock2 = new InterProcessSemaphoreMutex(client, PATH2);

            InterProcessMultiLock lock = new InterProcessMultiLock(Arrays.asList(lock1, lock2));

            if (!lock.acquire(10, TimeUnit.SECONDS)){
                throw new IllegalStateException("could not acquire the lock.");
            }

            System.out.println("has the lock");

            System.out.println("has the lock1:" + lock1.isAcquiredInThisProcess());
            System.out.println("has the lock2:" + lock2.isAcquiredInThisProcess());

            try {
                resource.use();
            }finally {
                System.out.println("release the lock");
                lock.release();
            }

            System.out.println("has the lock1:" + lock1.isAcquiredInThisProcess());
            System.out.println("has the lock2:" + lock2.isAcquiredInThisProcess());

            CloseableUtils.closeQuietly(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
