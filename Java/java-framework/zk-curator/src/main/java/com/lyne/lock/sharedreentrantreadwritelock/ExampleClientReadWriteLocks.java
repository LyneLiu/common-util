package com.lyne.lock.sharedreentrantreadwritelock;

import com.lyne.lock.FakeLimitedResource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

import java.util.concurrent.TimeUnit;

/**
 * InterProcessReadWriteLock（可重入读写锁）
 *
 * http://colobu.com/2014/12/12/zookeeper-recipes-by-example-2/
 * @author nn_liu
 * @Created 2017-11-30-21:21
 */

public class ExampleClientReadWriteLocks {

    private final InterProcessReadWriteLock lock;

    private final InterProcessLock readLock;

    private final InterProcessLock writeLock;

    private final FakeLimitedResource resource;

    private final String clientName;

    public ExampleClientReadWriteLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource, String clientName){
        this.resource = resource;
        this.clientName = clientName;
        // 通过ConnectionStateListener监控链接状态
        client.getConnectionStateListenable().addListener(new StateListener());

        /**
         * InterProcessReadWriteLock创建实例后，根据需求得到读锁或者写锁
         */
        lock = new InterProcessReadWriteLock(client, lockPath);
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    public void doWork(long time, TimeUnit unit) throws Exception {
        if (!writeLock.acquire(time, unit)){
            throw new IllegalStateException(clientName + " could not acquire the write lock.");
        }

        System.out.println(clientName + " has the writeLock.");

        if (!readLock.acquire(time, unit)){
            throw new IllegalStateException(clientName + " could not acquire the read lock.");
        }

        System.out.println(clientName + " has the readLock too.");

        try {
            resource.use();
        }finally {
            System.out.println(clientName + " release the lock.");
            readLock.release();
            writeLock.release();
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
