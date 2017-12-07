package com.lyne.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * NodeCache对应于Node的操作，PathCache为NodeCache的子集操作。
 *
 * @author nn_liu
 * @Created 2017-12-04-19:20
 */
@Slf4j
public class NodeCacheExample {

    private static final String PATH = "/example/nodeCache";

    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer();
        CuratorFramework client = null;
        NodeCache cache = null;

        try {
            client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();

            cache = new NodeCache(client, PATH);
            cache.start();

            processCommands(client, cache);
        } finally {
            CloseableUtils.closeQuietly(cache);
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(server);
        }
    }

    private static void processCommands(CuratorFramework client, NodeCache cache) {

        printHelp();

        try {
            addListener(cache);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            boolean done = false;

            while (!done){
                System.out.print("> ");
                String line = in.readLine();
                if (line == null){
                    continue;
                }

                String command = line.trim();
                String[] parts = command.split("\\s");
                if (parts.length == 0) {
                    continue;
                }

                String ops = parts[0];
                String args[] = Arrays.copyOfRange(parts, 1, parts.length);
                if (ops.equalsIgnoreCase("help") ||ops.equalsIgnoreCase("?")){
                    printHelp();
                }else if (ops.equalsIgnoreCase("quit") || ops.equalsIgnoreCase("q")){
                    done = true;
                }else if (ops.equalsIgnoreCase("set")){
                    setValue(client, command, args);
                }else if (ops.equalsIgnoreCase("remove")){
                    remove(client, command, args);
                }else if (ops.equalsIgnoreCase("show")){
                    show(cache);
                }

                Thread.sleep(1000);     //// just to allow the console output to catch up
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            // do nothing
        }
    }

    private static void setValue(CuratorFramework client, String command, String[] args) {

        if (args.length != 1) {
            System.err.println("syntax error (expected set <value>): " + command);
            return;
        }
        byte[] bytes = args[0].getBytes();

        try {
            client.setData().forPath(PATH, bytes);
        } catch (Exception e) {
            try {
                client.create().creatingParentsIfNeeded().forPath(PATH, bytes);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    private static void remove(CuratorFramework client, String command, String[] args) {

        try {
            client.delete().forPath(PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void show(NodeCache cache) {

        if (cache.getCurrentData() != null){
            System.out.println(cache.getCurrentData().getPath() + " = " + new String(cache.getCurrentData().getData()));
        }else {
            System.out.println("cache don't set a value.");
        }
    }


    private static void addListener(final NodeCache cache){
        // 记录log日志信息
        cache.getListenable().addListener(new NodeCacheListener() {

            @Override
            public void nodeChanged() throws Exception {
                log.info("Node changed: " + cache.getCurrentData().getPath() + ", value: " + new String(cache.getCurrentData().getData()));
            }
        });
    }

    private static void printHelp(){

        System.out.println("An example of using PathChildrenCache. This example is driven by entering commands at the prompt:\n");
        System.out.println("set <value>: Adds or updates a node with the given name");
        System.out.println("remove: Deletes the node with the given name");
        System.out.println("show: Display the nodes/values in the cache");
        System.out.println("quit: Quit the example");
        System.out.println();

    }

}
