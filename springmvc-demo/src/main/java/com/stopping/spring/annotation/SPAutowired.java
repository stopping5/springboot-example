package com.stopping.spring.annotation;

import java.lang.annotation.*;

/**
 * @Description SPAutowired
 * @Author stopping
 * @date: 2021/3/13 22:56
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPAutowired {
    boolean required() default true;

    String value();
}
