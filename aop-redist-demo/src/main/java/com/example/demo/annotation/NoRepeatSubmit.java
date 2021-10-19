package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @author Administrator
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmit {
    /**
     * 过期时间 默认60s
     */
    long timeOut() default 60;
}
