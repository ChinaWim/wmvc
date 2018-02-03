package com.ming.wmvc.annotation;

import com.ming.wmvc.enums.RequestMethod;

import java.lang.annotation.*;

/**
 * Created by Ming on 2018/2/2.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value() default "";
    RequestMethod method() default RequestMethod.GET_POST;
}
