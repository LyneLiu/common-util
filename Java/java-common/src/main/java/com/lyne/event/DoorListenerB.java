package com.lyne.event;

/**
 * Created by nn_liu on 2016/10/21.
 */
public class DoorListenerB implements DoorListener {
    @Override
    public void doorEvent(DoorEvent doorEvent) {

        if (doorEvent.getDoorState() != null && doorEvent.getDoorState().equals("open")){
            System.out.println("the door B is open!");
        }else {
            System.out.println("the door B is closed!");
        }

    }
}
