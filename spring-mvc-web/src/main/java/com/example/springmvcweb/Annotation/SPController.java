package com.example.springmvcweb.Annotation;

import java.lang.annotation.*;

/**
 * 控制层注解
 *
 * @author stopping
 * @date 2021-03-15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPController {
    String value() default "";
}
