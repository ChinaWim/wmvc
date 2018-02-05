package com.ming.wmvc.enums;

/**
 * Created by Ming on 2018/2/2.
 */
public enum  RequestMethod {
    GET("GET"),POST("POST"),GET_POST("GET_POST");
    private String method;
    RequestMethod(String method){
        this.method = method;
    }
    public String getMethod() {
        return method;
    }
}
