package com.lyne.counters;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.test.TestingServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * DistributedAtomicLong乐观锁方式：
 * 1、get(): 获取当前值
 * 2、increment()： 加一
 * 3、decrement(): 减一
 * 4、add()： 增加特定的值
 * 5、subtract(): 减去特定的值
 * 6、trySet(): 尝试设置计数值
 * 7、forceSet(): 强制设置计数值
 *
 * @author nn_liu
 * @Created 2017-12-04-17:41
 */

public class DistributedAtomicLongExample{

    private static final int QTY = 5;
    private static final String PATH = "/examples/counter";
    private static Random rand = new Random();

    public static void main(String[] args) {
        try (TestingServer server = new TestingServer()) {
            CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.getConnectionStateListenable().addListener(new StateListener());
            client.start();
            List<DistributedAtomicLong> examples = Lists.newArrayList();
            ExecutorService service = Executors.newFixedThreadPool(QTY);
            for (int i = 0; i < QTY; ++i) {
                final DistributedAtomicLong count = new DistributedAtomicLong(client, PATH, new RetryNTimes(10, 10));

                examples.add(count);
                Callable<Void> task = new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        try {
                            Thread.sleep(rand.nextInt(1000));
                            //AtomicValue<Long> value = count.increment();
                            //AtomicValue<Long> value = count.decrement();
                            AtomicValue<Long> value = count.add((long)rand.nextInt(20));
                            System.out.println("succeed: " + value.succeeded());
                            if (value.succeeded())
                                System.out.println("Increment: from " + value.preValue() + " to " + value.postValue());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

/**
 * 处理连接状态的改变，当连接lost时不再拥有锁。
 */
class StateListener implements ConnectionStateListener {

    public void stateChanged(CuratorFramework client, ConnectionState newState) {

        switch (newState){
            case LOST:
                //一旦丢失链接，zk server会删除锁数据
                //lock.acquire(defaultTime, TimeUnit.SECONDS);
                System.out.println(client.getNamespace() + " has lost the lock.");
                break;
            default:
                System.out.println(client.getNamespace() + " current connection state:" + newState.toString());
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}
