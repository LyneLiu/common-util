package com.lyne.lock.sharedlock;

import com.lyne.lock.FakeLimitedResource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

import java.util.concurrent.TimeUnit;

/**
 * InterProcessSemaphoreMutex（不可重入锁）
 * @author nn_liu
 * @Created 2017-11-30-21:21
 */

public class ExampleClientSemaphoreLocks {

    private final InterProcessSemaphoreMutex lock;

    private final FakeLimitedResource resource;

    private final String clientName;

    public ExampleClientSemaphoreLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource, String clientName){
        this.resource = resource;
        this.clientName = clientName;
        // 通过ConnectionStateListener监控链接状态
        client.getConnectionStateListenable().addListener(new StateListener());
        lock = new InterProcessSemaphoreMutex(client, lockPath);
    }

    public void doWork(long time, TimeUnit unit) throws Exception {
        if (!lock.acquire(time, unit)){
            throw new IllegalStateException(clientName + " could not acquire the lock.");
        }

        System.out.println(clientName + " has the lock.");

        // InterProcessMutex多次调用acquire，至少调用一次release，当前线程不会阻塞；
        // InterProcessSemaphoreMutex调用acquire两次，当前线程会阻塞至第二次的acquire。
        if (!lock.acquire(time, unit)){
            throw new IllegalStateException(clientName + " could not acquire the lock again！");
        }

        System.out.println(clientName + " has the lock again.");

        try {
            System.out.println(clientName + " has the lock.");
            resource.use();
        }finally {
            System.out.println(clientName + " release the lock.");
            lock.release(); // always release the lock in a finally block
            lock.release(); // always release the lock in a finally block
        }
    }

    /**
     * 处理连接状态的改变，当连接lost时不再拥有锁。
     */
    class StateListener implements ConnectionStateListener{

        public void stateChanged(CuratorFramework client, ConnectionState newState) {

            switch (newState){
                case LOST:
                    //一旦丢失链接，zk server会删除锁数据
                    //lock.acquire(defaultTime, TimeUnit.SECONDS);
                    System.out.println(clientName + " has lost the lock.");
                    break;
                default:
                    System.out.println(clientName + " current connection state:" + newState.toString());
            }

        }
    }
}
