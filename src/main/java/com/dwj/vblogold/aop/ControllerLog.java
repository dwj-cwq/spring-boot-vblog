package com.dwj.vblogold.aop;

import java.lang.annotation.*;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerLog {
    String value() default "";
}
