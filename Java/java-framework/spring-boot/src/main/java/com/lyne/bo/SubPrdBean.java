package com.lyne.bo;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by nn_liu on 2017/5/18.
 */

@Component
public class SubPrdBean implements Serializable {

    @NotNull
    private String school;


}
