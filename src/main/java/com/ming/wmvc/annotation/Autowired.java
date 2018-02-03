package com.ming.wmvc.annotation;

import java.lang.annotation.*;

/**
 * Created by Ming on 2018/2/2.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
