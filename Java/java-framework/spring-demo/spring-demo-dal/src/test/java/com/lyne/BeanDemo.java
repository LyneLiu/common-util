package com.lyne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author nn_liu
 * @Created 2017-11-13-10:16
 */

@AllArgsConstructor
public class BeanDemo {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
