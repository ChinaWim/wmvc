package com.ming.wmvc.helper;

import com.ming.wmvc.annotation.Scope;
import com.ming.wmvc.bean.Handler;
import com.ming.wmvc.enums.ScopeValue;
import com.ming.wmvc.utils.TypeConvertUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**执行方法
 * Created by Ming on 2018/2/2.
 */
public class HandlerHelper {

    public static void invokeHandler(Handler handler, Map<String,Object> sourceInstances,HttpServletRequest request, HttpServletResponse response) throws Exception {
        Method method = handler.getMethod();
        Object instance = handler.getInstance();
        Map<String, Integer> paramMap = handler.getParamMap();
        Object [] args = null;
        //请求参数的类型
        Parameter[] parameters = method.getParameters();
        //如果这个方法有参数
        if(!paramMap.isEmpty()) {
            args = new Object[paramMap.size()];
            Set<Map.Entry<String, Integer>> entries = paramMap.entrySet();
            for (Map.Entry<String, Integer> entry : entries) {
                String paramName = entry.getKey();
                Integer index = entry.getValue();
                if(paramName.equals("HttpServletRequest") ){
                    args[index] = request;
                }else if( paramName.equals("HttpServletResponse")){
                    args[index] = response;
                }else{
                    Class<?> typeClass = parameters[index].getType();
                    Object value = null;
                    if(TypeConvertUtil.isBaseType(typeClass) && request.getParameter(paramName) != null){
                        value = TypeConvertUtil.baseConvert(request.getParameter(paramName),typeClass );
                    }else if (!TypeConvertUtil.isBaseType(typeClass)){
                        value = TypeConvertUtil.beanConvert(request,typeClass);
                    }
                    args[index] = value ;
                }
            }
        }
        //单例 多例 controller切换
        Class<?> clazz = method.getDeclaringClass();
        if(clazz.isAnnotationPresent(Scope.class) &&
                clazz.getAnnotation(Scope.class).value() == ScopeValue.PROTOTYPE){
            Object newInstance = clazz.newInstance();
            Map<String,Object> targetInstances = new HashMap<>();
            targetInstances.put(clazz.getName(),newInstance);
            //重新注入数据
            IocHelper.doAutoWired(targetInstances,sourceInstances);
            method.invoke(newInstance,args);
        }else{
            method.invoke(instance,args);
        }

    }
}
