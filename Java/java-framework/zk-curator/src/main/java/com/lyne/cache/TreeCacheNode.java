package com.lyne.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * TreeCache即可以监控节点的状态，也可以监控子节点的状态，类似于PathCache和NodeCache的结合。
 *
 * @author nn_liu
 * @Created 2017-12-05-9:57
 */
@Slf4j
public class TreeCacheNode {

    private static final String PATH = "/";

    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer();
        CuratorFramework client = null;
        TreeCache cache = null;

        try {
            client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();

            cache = new TreeCache(client, PATH);
            cache.start();

            processCommands(client, cache);
        } finally {
            CloseableUtils.closeQuietly(cache);
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(server);
        }
    }

    private static void processCommands(CuratorFramework client, TreeCache cache) {

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
                }else if (ops.equalsIgnoreCase("list")){
                    list(cache, command, args);
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

        if (args.length != 2) {
            System.err.println("syntax error (expected set <path> <value>): " + command);
            return;
        }
        String name = args[0];
        if (name.contains("/")) {
            System.err.println("Invalid node name" + name);
            return;
        }
        String path = ZKPaths.makePath(PATH, name);
        byte[] bytes = args[1].getBytes();
        try {
            client.setData().forPath(path, bytes);
        } catch (Exception e) {
            try {
                client.create().creatingParentsIfNeeded().forPath(path, bytes);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    private static void remove(CuratorFramework client, String command, String[] args) {

        if (args.length != 1){
            System.err.println("syntax error (expected remove <path>): " + command);
            return;
        }

        String name = args[0];
        if (name.contains("/")){
            System.err.println("Invalid node name" + name);
            return;
        }

        String path = ZKPaths.makePath(PATH, name);

        try {
            client.delete().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void list(TreeCache cache, String command, String[] args) {


        String fullPath = PATH;

        if (args.length == 1){
            String name = args[0];
            fullPath = fullPath.concat(name);
        }

        if (MapUtils.isEmpty(cache.getCurrentChildren(fullPath))){
            System.out.println("* empty *");
        }else {
            for (Map.Entry<String, ChildData> entry : cache.getCurrentChildren(fullPath).entrySet()) {
                System.out.println(entry.getKey() + "=" + String.format("%s", entry.getValue().getData()));
            }
        }
    }


    private static void addListener(final TreeCache cache){
        // 记录log日志信息
        cache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                String data = event.getData() == null ? "" : (event.getData().getData() == null ? "" : new String(event.getData().getData()));
                switch (event.getType()){
                    case NODE_ADDED:
                        log.info("TreeNode added: " + ZKPaths.getNodeFromPath(event.getData().getPath()) + ", value: "
                                + data);
                        break;
                    case NODE_UPDATED:
                        log.info("TreeNode changed: " + ZKPaths.getNodeFromPath(event.getData().getPath()) + ", value: "
                                + data);
                        break;
                    case NODE_REMOVED:
                        log.info("TreeNode removed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
                        break;
                    default:
                        System.out.println("Other event: " + event.getType().name());
                        break;
                }
            }
        });
    }

    private static void printHelp(){

        System.out.println("An example of using PathChildrenCache. This example is driven by entering commands at the prompt:\n");
        System.out.println("set <name> <value>: Adds or updates a node with the given name");
        System.out.println("remove <name>: Deletes the node with the given name");
        System.out.println("list: List the nodes/values in the cache");
        System.out.println("quit: Quit the example");
        System.out.println();

    }

}
