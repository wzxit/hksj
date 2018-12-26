package com.hksj.limit.annotation;

import com.hksj.limit.aop.LimitImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(LimitImportSelector.class)
public @interface EnableLimit {
}
