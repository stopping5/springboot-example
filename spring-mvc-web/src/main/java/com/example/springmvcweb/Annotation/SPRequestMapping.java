package com.example.springmvcweb.Annotation;

import java.lang.annotation.*;

/**
 * 请求转发路径注解
 *
 * @author stopping
 * @date 2021-03-15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPRequestMapping {
    String value() default "";
}
