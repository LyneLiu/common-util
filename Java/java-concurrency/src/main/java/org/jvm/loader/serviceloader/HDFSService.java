package org.jvm.loader.serviceloader;

/**
 * Created by nn_liu on 2017/6/2.
 */
public class HDFSService implements IService {
    @Override
    public String sayHello() {
        return "Hello HDFSService";
    }
    @Override
    public String getScheme() {
        return "hdfs";
    }
}
