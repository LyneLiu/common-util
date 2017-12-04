package com.lyne.lock.sharedreentrantreadwritelock;

import com.lyne.lock.FakeLimitedResource;
import com.lyne.lock.sharedlock.ExampleClientSemaphoreLocks;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author nn_liu
 * @Created 2017-12-01-9:55
 */
@Slf4j
public class InterProcessReadWriteExample {

    private static final int QTY = 5;
    private static final int REPETITIONS = QTY * 2;
    private static final String PATH = "/examples/locks";

    public static void main(String[] args) throws Exception {
        final FakeLimitedResource resource = new FakeLimitedResource();
        ExecutorService service = Executors.newFixedThreadPool(QTY);
        final TestingServer server = new TestingServer();

        try {
            for (int i = 0; i < QTY; i++) {
                final int index = i;
                Callable<Void> task = new Callable<Void>() {
                    public Void call() throws Exception {
                        CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
                        try {
                            client.start();
                            final ExampleClientReadWriteLocks example = new ExampleClientReadWriteLocks(client, PATH, resource, "Client " + index);
                            for (int j = 0; j < REPETITIONS; j++) {
                                example.doWork(10, TimeUnit.SECONDS);
                            }
                        }catch (Throwable e){
                            e.printStackTrace();
                        }finally {
                            CloseableUtils.closeQuietly(client);
                        }

                        return null;
                    }
                };

                service.submit(task);
            }

            // 线程池阻塞一定时间等待task任务执行完成
            log.info("shutdown server...");
            service.shutdown();
            service.awaitTermination(100, TimeUnit.SECONDS);
        }finally {
            CloseableUtils.closeQuietly(server);
        }
    }

}
