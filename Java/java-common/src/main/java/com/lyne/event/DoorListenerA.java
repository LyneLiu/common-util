package com.lyne.event;

/**
 * Created by nn_liu on 2016/10/21.
 */
public class DoorListenerA implements DoorListener {
    @Override
    public void doorEvent(DoorEvent doorEvent) {

        if (doorEvent.getDoorState() != null && doorEvent.getDoorState().equals("open")){
            System.out.println("open door A!");
        }else {
            System.out.println("close door A!");
        }

    }
}
