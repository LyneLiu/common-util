package org.jvm.loader.classloader;

import java.io.*;

/**
 * 1、覆盖父类的findClass(String name)方法；
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
