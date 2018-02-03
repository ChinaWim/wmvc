package com.ming.wmvc.helper;

import com.ming.wmvc.annotation.*;
import com.ming.wmvc.bean.Handler;
import com.ming.wmvc.utils.ParameterUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ming on 2018/2/2.
 */
public class IocHelper {

    //将包下的class文件创建实例
    public  static void init(Map<String,Class<?>>classMap,Map<String,Object> instances,Map<String,Handler> methods) throws Exception {
        if(classMap.isEmpty()) return;

        Set<Map.Entry<String, Class<?>>> entries = classMap.entrySet();
        for (Map.Entry<String, Class<?>> entry : entries) {
            String className = entry.getKey();
            Class<?> clazz = entry.getValue();
            //获取className 没写ClassName则默认是全限定名
            if(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class)){
                Object object = clazz.newInstance();
                //如果是Controller
                if( clazz.isAnnotationPresent(Controller.class) ){
                    String value = clazz.getAnnotation(Controller.class).value().trim();
                    if (!value.equals("")) instances.put(value,object);
                }else {
                    String value = clazz.getAnnotation(Service.class).value().trim();
                    if (!value.equals(""))
                        instances.put(value,object);
                    //存储Service接口全限定名
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> i : interfaces) {
                        instances.put(i.getName(),object);
                    }
                }
                instances.put(className,object);
            }
        }
        //全部类要注入数据
        doAutoWired(instances,instances);
        doHandlerMapping(instances,methods);


    }

    //方法映射
    private static void doHandlerMapping(Map<String,Object> instances,Map<String,Handler> methods){
        if(instances.isEmpty()) return;

        Set<Map.Entry<String, Object>> entries = instances.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            String path = "";
            if(clazz.isAnnotationPresent(RequestMapping.class)){
                path = clazz.getAnnotation(RequestMapping.class).value();
            }
            Method[] ms = clazz.getMethods();
            for (Method method : ms) {
                if(!method.isAnnotationPresent(RequestMapping.class)) continue;
                String requestPath = path + method.getAnnotation(RequestMapping.class).value();
                //该方法的参数
                Parameter[] parameters = method.getParameters();
                //该方法的变量名 使用javassist包
                String[] paramNames = ParameterUtil.getParamNames(method);
                Map<String,Integer> paramMap = new HashMap<>();

                Annotation[][] annotations = method.getParameterAnnotations();
                for(int i = 0; i <annotations.length; i++){
                    Annotation[] annotation = annotations[i];
                    //如果该参数没有注解,若是request,response 则取类名，否则取变量名
                    if(annotation.length == 0){
                        //看看是不是request response
                        Class<?> paramClass = parameters[i].getType();
                        if(paramClass == HttpServletRequest.class || paramClass == HttpServletResponse.class){
                            paramMap.put(paramClass.getSimpleName(),i);
                        }else{
                            paramMap.put(paramNames[i],i);
                        }
                        continue;
                    }

                    for (Annotation anno : annotation) {
                        //如果是ReuqestParam注解
                        if(anno.annotationType() == RequestParam.class){
                            RequestParam requestParam = (RequestParam) anno;
                            String value = requestParam.value();
                            paramMap.put(value,i);
                            break;
                        }
                    }
                }

                Handler handler = new Handler(method,instance,paramMap);
                methods.put(requestPath,handler);
            }
        }

    }



    //字段AutoField  target要注入的类，source 注入什么类
    public  static void doAutoWired(Map<String,Object> targetInstances ,Map<String,Object> sourceInstances) throws Exception {
        if(targetInstances.isEmpty() || sourceInstances.isEmpty() ) return;

        Set<Map.Entry<String, Object>> entries = targetInstances.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Object obj = entry.getValue();//往当前的类注入
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) continue;
                Autowired annotation = field.getAnnotation(Autowired.class);
                Object value = new Object();
                //如果autowired value不为空
                if(!annotation.value().trim().equals("")) value = sourceInstances.get(annotation.value());
                else {
                    value = sourceInstances.get(field.getType().getName());
                }
                field.setAccessible(true);
                field.set(obj,value);
            }
        }
    }



}
