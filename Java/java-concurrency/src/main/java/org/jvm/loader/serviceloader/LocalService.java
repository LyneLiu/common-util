package org.jvm.loader.serviceloader;

/**
 * Created by nn_liu on 2017/6/2.
 */
public class LocalService implements IService {
    @Override
    public String sayHello() {
        return "Hello LocalService";
    }
    @Override
    public String getScheme() {
        return "local";
    }
}
