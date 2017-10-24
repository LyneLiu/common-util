package org.jvm.loader.serviceloader;

import java.util.ServiceLoader;

/**
 * Created by nn_liu on 2017/6/2.
 */
public class ServiceLoaderMain {
    public static void main(String[] args) {
        ServiceLoader<IService> serviceLoader  = ServiceLoader.load(IService.class);
        for (IService service : serviceLoader) {
            System.out.println(service.getScheme()+"="+service.sayHello());
        }
    }
}
