package com.example.springmvcweb.Annotation;

import java.lang.annotation.*;

/**
 * 注入bean注解
 *
 * @author stopping
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPAutowired {
    String value() default "";
}
