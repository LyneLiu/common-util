package com.lyne.premain;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 添加VM启动参数：
 * -javaagent:system\path\java-premain-agent-1.0-SNAPSHOT.jar
 *
 * @author nn_liu
 * @Created 2017-11-03-9:39
 */

public class PremainAgentDemo02{

    /**
     * 当前进程获取Class静态数据
     * @param classObject
     */
    private static void processBizMethods(Class classObject) {
        if (BusinessClass.class.equals(classObject)){
            Method[] allMethods = classObject.getDeclaredMethods();
            for (Method aMethod : allMethods){
                System.out.println("============== method name ================");
                System.out.print(aMethod.getName());
                int modifiers = aMethod.getModifiers();
                if (Modifier.isPrivate(modifiers)){
                    System.out.print(" :: Method \"" +
                            aMethod.getName() + "\" is private\n");
                }else if (Modifier.isPublic(modifiers)){
                    System.out.print(" :: Method \"" +
                            aMethod.getName() + "\" is public\n");
                }else if(Modifier.isProtected(modifiers)){
                    System.out.print(" :: Method \"" +
                            aMethod.getName() + "\" is protected\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        //processBizMethods(BusinessClass.class);

        // 通过代理获取Class静态数据
        BusinessClass object = new BusinessClass();
        System.out.println("Biz Object " + object);
    }
}

class BusinessClass {

    public void bizMethod1(){
        System.out.println("Biz Method 1");
    }

    @SuppressWarnings("unused")
    private void bizMethod2(){
        System.out.println("Biz Method 2");
    }

}
