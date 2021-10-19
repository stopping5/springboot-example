package com.example.springmvcweb.Annotation;

import java.lang.annotation.*;

/**
 * 服务层注解
 *
 * @author stopping
 * @date 2021-03-15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPService {
    String value() default "";
}
