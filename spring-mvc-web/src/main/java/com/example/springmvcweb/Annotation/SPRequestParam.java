package com.example.springmvcweb.Annotation;

import java.lang.annotation.*;

/**
 * 请求参数注解
 *
 * @author stopping
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPRequestParam {
    /**
     * 入参别名
     */
    String value() default "";
}
