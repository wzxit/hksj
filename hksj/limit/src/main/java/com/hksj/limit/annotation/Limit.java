package com.hksj.limit.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limit {
    //资源的名字
    String name() default "";

    //资源的key
    String key() default "";
    /**
     * 给定的时间段
     * 单位秒
     * @return int
     */
    int limitPeriod();
    /**
     * 最多的访问限制次数
     * @return int
     */
    int limitCount();
}
