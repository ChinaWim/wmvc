package com.ming.wmvc.utils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Ming on 2018/2/3.
 */
public class ParameterUtil {

    //获取请求方法的形参名字  如 example(String sex,String name) 得到 [sex,name]
    //使用第三方库 javassist
    public static String [] getParamNames(Method method){
        Class<?> clazz = method.getDeclaringClass();
        ClassPool pool = ClassPool.getDefault();
        String[] paramNames = null;
        try {
            CtClass ctClass = pool.get(clazz.getName());
            CtMethod ctMethod = ctClass.getDeclaredMethod(method.getName());
            //获取方法的信息
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attribute = (LocalVariableAttribute)codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if(attribute == null){
                throw new RuntimeException
                        ("(LocalVariableAttribute)codeAttribute.getAttribute(LocalVariableAttribute.tag)错误！");
            }
            paramNames = new String [ctMethod.getParameterTypes().length];
            // 静态方法第一个参数就是方法的参数，如果是实例方法，第一个参数是this,所以要加1
            int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
            //写入参数名
            for (int i = 0; i < paramNames.length; i++) {
                paramNames[i] = attribute.variableName(i + pos);
            }

        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return paramNames;
    }




}


