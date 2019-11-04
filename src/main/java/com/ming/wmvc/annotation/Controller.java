package com.ming.wmvc.annotation;

import java.lang.annotation.*;

/**
 * Created by Ming on 2018/2/2.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {

    String value() default "";
}
