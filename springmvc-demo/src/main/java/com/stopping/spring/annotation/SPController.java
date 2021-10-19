package com.stopping.spring.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Description SPController 注解
 * @Author stopping
 * @date: 2021/3/13 21:56
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SPController {
    String value() default "";
}
