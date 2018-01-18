package com.lyne.basic;

/**
 * @author nn_liu
 * @Created 2017-12-11-18:45
 */
public enum AppType {

    CONNECTED("running", 100),
    
    DISCONNECTED("shutdown", 200);

    private final String status;

    private final int code;

    AppType(String status, int code) {
        this.status = status;
        this.code = code;
    }

    public static AppType from(String status){
        for (AppType each : AppType.values()){
            if (each.getStatus().equals(status)){
                return each;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }
}
