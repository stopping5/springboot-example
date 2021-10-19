package com.stopping.spring.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * SpService
 *
 * @author stopping
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPService {
    String value() default "";
}
