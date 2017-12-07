package com.lyne.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 * Curator提供了三种类型的缓存方式：Path Cache,Node Cache 和Tree Cache.
 *
 * Path Cache用来监控一个ZNode的子节点，当一个子节点增加、更新、删除时，Path Cache会改变它的状态，
 * 会包含最新的子节点、子节点的数据和状态。
 *
 * @author nn_liu
 * @Created 2017-12-04-18:30
 */
@Slf4j
public class PathCacheExample {

    private static final String PATH = "/example/cache";

    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer();
        CuratorFramework client = null;
        PathChildrenCache cache = null;

        try {
            client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();

            // in this example we will cache data. Notice that this is optional.
            cache = new PathChildrenCache(client, PATH, true);
            cache.start();

            processCommands(client, cache);
        } finally {
            CloseableUtils.closeQuietly(cache);
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(server);
        }
    }

    private static void processCommands(CuratorFramework client, PathChildrenCache cache) {

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
                    list(cache);
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

    private static void list(PathChildrenCache cache) {

        if (cache.getCurrentData().size() == 0){
            System.out.println("* empty *");
        }else {
            for (ChildData childData : cache.getCurrentData()) {
                System.out.println(childData.getPath() + "=" + String.format("%s", childData.getData()));
            }
        }
    }


    private static void addListener(final PathChildrenCache cache){
        // 记录log日志信息
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()){
                    case CHILD_ADDED:
                        log.info("Node added: " + ZKPaths.getNodeFromPath(event.getData().getPath()) + ", value: "
                                + new String(event.getData().getData()));
                        break;
                    case CHILD_UPDATED:
                        log.info("Node changed: " + ZKPaths.getNodeFromPath(event.getData().getPath()) + ", value: "
                                + new String(event.getData().getData()));
                        break;
                    case CHILD_REMOVED:
                        log.info("Node removed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
                        break;
                    default:
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
