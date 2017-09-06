package com.lyne.event;

import java.util.EventListener;

/**
 * Created by nn_liu on 2016/10/21.
 */
public interface DoorListener extends EventListener {
    void doorEvent(DoorEvent doorEvent);
}
