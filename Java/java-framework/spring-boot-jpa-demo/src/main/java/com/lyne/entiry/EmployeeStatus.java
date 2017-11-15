package com.lyne.entiry;

/**
 * https://www.javabrahman.com/corejava/the-complete-java-enums-tutorial-with-examples/
 *
 * @author nn_liu
 * @Created 2017-11-15-17:13
 */

public enum EmployeeStatus {

    UNKNOWN("unknown", 1001), ONSERVICE("onservice", 2001), QUIT("quit", 3001);

    private String status;

    private Integer code;

    EmployeeStatus(String status, Integer index) {
        this.status = status;
        this.code = index;
    }

    public String getStatus(){
        return this.status;
    }

    public Integer getIndex(){
        return this.code;
    }

}
