package com.lyne.leader.leaderlatch;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 1、LeaderLatch从连接到同一个zk server的同一个latchPath的client中随机选举一个节点作为leader。
 * 2、LeaderLatch只有在调用close方法的时候才会释放领导权。
 *
 * @author nn_liu
 * @Created 2017-11-30-14:04
 */

public class LeaderLatchExample {

    private static final int CLIENT_QTY = 10;
    private static final String PATH = "/examples/leader";

    public static void main(String[] args) throws Exception {

        List<CuratorFramework> clients = Lists.newArrayList();
        List<LeaderLatch> examples = Lists.newArrayList();
        TestingServer server = new TestingServer(2181);

        System.out.println("zk server port: " + server.getPort());

        try {
            for (int i = 0; i < CLIENT_QTY; i++) {
                CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
                clients.add(client);
                LeaderLatch example = new LeaderLatch(client, PATH, "Client #" + i);
                examples.add(example);
                client.start();
                example.start();
            }

            Thread.sleep(2000);

            LeaderLatch currentLeader = null;
            for (LeaderLatch example : examples) {
                if (example.hasLeadership()){
                    currentLeader = example;
                }
            }

            System.out.println("current leader is" + currentLeader.getId());
            System.out.println("release the leader " + currentLeader.getId());

            currentLeader.close();

            examples.get(0).await(2, TimeUnit.SECONDS);
            System.out.println("Client #0 maybe is elected as the leader or not although it want to be");
            System.out.println("the new leader is " + examples.get(0).getLeader().getId());

            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("Shutting down...");
            for (LeaderLatch exampleClient : examples) {
                CloseableUtils.closeQuietly(exampleClient);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }
            CloseableUtils.closeQuietly(server);
        }

    }

}
