package com.lyne.collection.list;

import java.util.Iterator;
import java.util.Stack;

/**
 * Stack继承自Vector
 * Created by nn_liu on 2017/6/13.
 */
public class StackCommon {

    /**
     * Stack实际上是通过数组实现的：
     * 1、执行push操作(即，将元素推入栈中)，是通过将元素追加的数组的末尾中。
     * 2、执行peek操作(即，取出栈顶元素，不执行删除)，是返回数组末尾的元素。
     * 3、执行pop操作(即，取出栈顶元素，并将该元素从栈中删除)，是取出数组末尾的元素，然后将该元素从数组中删除。
     */
    public static void stackDemo(){

        Stack stack = new Stack();
        // 将1,2,3,4,5添加到栈中
        for(int i=1; i<6; i++) {
            stack.push(String.valueOf(i));
        }

        // 遍历并打印出该栈
        iteratorThroughRandomAccess(stack) ;

        // 查找“2”在栈中的位置，并输出
        int pos = stack.search("2");    // 从数组最后一个元素开始遍历，获取某个元素位置
        System.out.println("the postion of 2 is:"+pos);

        // pup栈顶元素之后，遍历栈
        stack.pop();
        iteratorThroughRandomAccess(stack) ;

        // peek栈顶元素之后，遍历栈
        String val = (String)stack.peek();
        System.out.println("peek:"+val);
        iteratorThroughRandomAccess(stack) ;

        // 通过Iterator去遍历Stack
        iteratorThroughIterator(stack) ;

    }


    /**
     * 通过快速访问遍历Stack
     */
    public static void iteratorThroughRandomAccess(Stack list) {
        String val = null;
        for (int i=0; i<list.size(); i++) {
            val = (String)list.get(i);
            System.out.print(val+" ");
        }
        System.out.println();
    }

    /**
     * 通过迭代器遍历Stack
     */
    public static void iteratorThroughIterator(Stack list) {

        String val = null;
        for(Iterator iter = list.iterator(); iter.hasNext(); ) {
            val = (String)iter.next();
            System.out.print(val+" ");
        }
        System.out.println();
    }

}
