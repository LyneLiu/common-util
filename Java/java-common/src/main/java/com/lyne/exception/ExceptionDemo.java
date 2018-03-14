package com.lyne.exception;

/**
 * @author nn_liu
 * @Created 2018-03-14-15:05
 */

public class ExceptionDemo {


    /**
     * result: 3
     * @return
     */
    public static int calMethod(){
        try {
            int result = 9 /0;
        } catch (Exception e){
            // catch 程序块中如果遇到return语句，并且存在finally程序块的情况下
            // 会先执行finally程序块中语句！
            return 2;
        } finally {
            return 3;
        }
    }

    public static void main(String[] args) {
        System.out.println(calMethod());
    }


}
