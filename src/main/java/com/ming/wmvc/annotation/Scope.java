package com.ming.wmvc.annotation;

import com.ming.wmvc.enums.ScopeValue;

import java.lang.annotation.*;

/**
 * Created by Ming on 2018/2/2.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    ScopeValue value() default ScopeValue.SINGLETON;
}
