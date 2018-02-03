package com.ming.wmvc.utils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类型转换 用户请求的都是String
 * 添加了日期转换
 *
 * Created by Ming on 2018/2/2.
 */
public class TypeConvertUtil {

    //常用类型转换
    public static Object baseConvert(String parameter,Class<?> targetClass) {
        try {
            if(targetClass == String.class) return parameter;
            if(targetClass == Byte.class || targetClass == byte.class) return Byte.parseByte(parameter);
            if(targetClass == Short.class || targetClass == short.class) return Short.parseShort(parameter);
            if(targetClass == Integer.class || targetClass == int.class) return Integer.parseInt(parameter);
            if(targetClass == Long.class || targetClass == long.class) return Long.parseLong(parameter);
            if(targetClass == Float.class || targetClass == float.class) return Float.parseFloat(parameter);
            if(targetClass == Double.class || targetClass == double.class) return Double.parseDouble(parameter);
            if(targetClass == Character.class || targetClass == char.class) return parameter.charAt(0);
            if(targetClass == Boolean.class || targetClass == boolean.class) return Boolean.parseBoolean(parameter);
            if(targetClass == Date.class) {
                SimpleDateFormat sdf = new SimpleDateFormat(PropertiesUtil.getValue("wmvc.dateFormat"));
                return sdf.parse(parameter);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //简易pojo类型转换
    public static Object beanConvert(HttpServletRequest request,Class<?> targetClass){
        Object instance = null;
        try {
            Field[] fields = targetClass.getDeclaredFields();
            instance = targetClass.newInstance();
            for (Field field : fields) {
                field.setAccessible(true);
                String parameter = request.getParameter(field.getName());
                Object o = null;
                if(parameter != null)  o = baseConvert(parameter, field.getType());
                field.set(instance,o);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    //判断这个方法参数是不是基本类型
    public static boolean isBaseType(Class<?> targetClass){
        if(targetClass == String.class ||
                targetClass == Byte.class || targetClass == byte.class ||
                targetClass == Short.class || targetClass == short.class ||
                targetClass == Integer.class || targetClass == int.class ||
                targetClass == Long.class || targetClass == long.class ||
                targetClass == Float.class || targetClass == float.class ||
                targetClass == Double.class || targetClass == double.class ||
                targetClass == Double.class || targetClass == double.class ||
                targetClass == Character.class || targetClass == char.class ||
                targetClass == Boolean.class || targetClass == boolean.class ||
                targetClass == Date.class ) return true;
        return false;
    }

}
