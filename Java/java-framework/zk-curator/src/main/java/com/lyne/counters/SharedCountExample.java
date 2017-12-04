package com.lyne.counters;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
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
 *
 * Zookeeper的一致性保证只要使用相同的path就可以获取到最新的计数器值。
 * Curator提供了两个计数器，一个是用int来计数，一个是用long来计数。
 *
 * @author nn_liu
 * @Created 2017-12-04-15:43
 */

@Slf4j
public class SharedCountExample {

    private static final int QTY = 5;
    private static final String PATH = "/examples/counter";

    public static void main(String[] args) {
        final Random rand = new Random();
        CustomeSharedCountListener exampleListener = new CustomeSharedCountListener();

        try (TestingServer server = new TestingServer()){
            CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();

            // 任意的SharedCount，只要使用相同的path，都可以得到这个计数值
            SharedCount baseCount = new SharedCount(client, PATH, 0);
            baseCount.addListener(exampleListener);
            baseCount.start();

            List<SharedCount> examples = Lists.newArrayList();
            ExecutorService service = Executors.newFixedThreadPool(QTY);
            for (int i = 0; i < QTY; i++) {
                final SharedCount count = new SharedCount(client, PATH, 0);
                examples.add(count);
                Callable<Void> task = new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {

                        //注意计数器必须start,使用完之后必须调用close关闭它。
                        count.start();
                        Thread.sleep(rand.nextInt(10000));

                        log.warn("Current count version:" + count.getVersionedValue() + ", current count:" + count.getCount());
                        System.out.println("Increment:" + count.trySetCount(count.getVersionedValue(), count.getCount() + rand.nextInt(10)));

                        return null;
                    }
                };

                service.submit(task);
            }

            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

            service.shutdown();
            service.awaitTermination(10, TimeUnit.MINUTES);

            for (int i = 0; i < QTY; ++i) {
                examples.get(i).close();
            }
            baseCount.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

@Slf4j
class CustomeSharedCountListener implements SharedCountListener{

    @Override
    public void countHasChanged(SharedCountReader sharedCount, int newCount) throws Exception {
        log.warn("Counter's value is changed to " + newCount);
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        log.warn("State changed: " + newState.toString());
    }
}
