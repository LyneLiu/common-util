package org.jvm.loader.serviceloader;

/**
 * ServiceLoader vs ClassLoader
 * 1、ServiceLoader装载的是一系列有某种共同特征的实现类，而ClassLoader是个万能加载类；
 * 2、ServiceLoader转载时需要特殊的配置(在META-INF文件夹下创建service文件，并将要加载的Service添加至对应文本中)，使用时也与ClassLoader有所区别；
 * 3、ServiceLoader还实现了Iterator接口。
 *
 * Created by nn_liu on 2017/6/2.
 */
public interface IService {

    String sayHello();

    String getScheme();

}
