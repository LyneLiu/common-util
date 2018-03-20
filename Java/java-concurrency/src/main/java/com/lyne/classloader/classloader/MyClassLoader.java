package com.lyne.classloader.classloader;

import java.io.*;

/**
 * 1、覆盖父类的findClass(String name)方法；
 *
 * 类加载器的委托机制：当一个类加载器收到类加载任务，会先交给其父类加载器去完成，因此最终加载任务都会传递到顶层的启动类加载器，只有当父类加载器无法完成加载任务时，才会尝试执行加载任务。
 *
 * 启动类加载器(Bootstrap ClassLoader)：负责加载JAVA_HOME\lib目录中的class，或通过-XBootclasspath参数指定路径中的class；
 * 扩展类加载器(Extension ClassLoader)：负责加载JAVA_HOME\lib\ext目录中的class，或通过java.ext.dirs系统变量指定路径中的类库；
 * 应用程序类加载器(Application ClassLoader)：负责加载用户路径上的类库；
 * 自定义类加载器(User ClassLoader)
 * 
 * Created by nn_liu on 2017/5/23.
 */
public class MyClassLoader extends ClassLoader {

    private String loaderName;   //类加载器名称
    private String path = "";    //类加载路径
    private final String fileType = ".class";

    public MyClassLoader(String loaderName){
        super();
        this.loaderName = loaderName;
    }

    public MyClassLoader(ClassLoader parent, String loaderName){
        super(parent);
        this.loaderName = loaderName;
    }

    public String getPath(){
        return this.path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String toString(){
        return this.loaderName;
    }

    public Class<?> findClass(String name){
        byte[] data = loaderClassData(name);
        return this.defineClass(name,data,0,data.length);
    }

    private byte[] loaderClassData(String name) {

        InputStream is = null;
        byte[] data = null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            is = new FileInputStream(new File(path+name+fileType));
            int c = 0;
            while (-1 != (c = is.read())){
                baos.write(c);
            }
            data = baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (is != null){
                    is.close();
                }
                if (baos != null){
                    baos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return data;
    }


}
