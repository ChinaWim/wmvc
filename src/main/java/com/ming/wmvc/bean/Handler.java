package com.ming.wmvc.bean;

import java.lang.reflect.Method;
import java.util.*;
/**
 * 方法参数Bean
 * Created by Ming on 2018/2/2.
 */
public class Handler {
    private Method method;
    private Object instance;
    private Map<String,Integer> paramMap;

    public Handler(Method method, Object instance, Map<String, Integer> paramMap) {
        this.method = method;
        this.instance = instance;
        this.paramMap = paramMap;
    }


    public Method getMethod() {
        return method;
    }

    public Object getInstance() {
        return instance;
    }

    public Map<String, Integer> getParamMap() {
        return paramMap;
    }
}
