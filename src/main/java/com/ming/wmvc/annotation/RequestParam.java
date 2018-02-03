package com.ming.wmvc.annotation;

import java.lang.annotation.*;

/**
 * Created by Ming on 2018/2/2.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    String value() default "";
    boolean required() default true;
}
