package com.ming.wmvc.helper;

import com.ming.wmvc.annotation.RequestParam;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Set;

import static com.ming.wmvc.helper.ScannerHelper.scannerClass;

/**
 * Created by Ming on 2018/2/2.
 */
public class ScannerHelperTest {

    @Test
    public void method1(){
        Map<String, Class<?>> classMap = scannerClass("com.ming.wmvc");
        Set<Map.Entry<String, Class<?>>> entries = classMap.entrySet();
        for (Map.Entry<String, Class<?>> entry : entries) {
            System.out.println(entry.getKey()+" : "+entry.getValue().getName());
        }
    }
    private String name;
    private String age;
    public void test(@RequestParam("name2") String name, String age){

    }

    @Test
    public void method2() throws NoSuchMethodException {
        Class<ScannerHelperTest> clazz = ScannerHelperTest.class;
        Method test = clazz.getDeclaredMethod("test", String.class, String.class);
        Parameter[] parameters = test.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println(parameter.getName());
        }


    }




}