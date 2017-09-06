package com.lyne.event;

import java.util.EventObject;

/**
 * Created by nn_liu on 2016/10/21.
 */

/**
 * 事件机制的三个要素：
 * 1、event object：事件的状态对象；
 * 2、event source：具体的事件；
 * 3、event listener：监听的事件类。
 */
public class DoorEvent extends EventObject {

    /*门的两种状态*/
    private String doorState = "";

    public DoorEvent(Object source,String doorState) {
        super(source);
        this.doorState = doorState;
    }

    public String getDoorState() {
        return doorState;
    }

    public void setDoorState(String doorState) {
        this.doorState = doorState;
    }
}
