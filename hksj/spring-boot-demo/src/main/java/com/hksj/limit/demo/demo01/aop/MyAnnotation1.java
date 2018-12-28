package com.hksj.limit.demo.demo01.aop;


import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyAnnotation1 {
    String name();

    String label();

    String description() default "";
}