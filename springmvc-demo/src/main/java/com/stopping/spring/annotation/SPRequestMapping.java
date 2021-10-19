package com.stopping.spring.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * RequestMapping
 *
 * @author stopping
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Mapping
public @interface SPRequestMapping {
    String name() default "";

    @AliasFor("path")
    String value() default "";

    @AliasFor("value")
    String[] path() default {};

    RequestMethod[] method() default {};

    String[] params() default {};

    String[] headers() default {};

    String[] consumes() default {};

    String[] produces() default {};
}
