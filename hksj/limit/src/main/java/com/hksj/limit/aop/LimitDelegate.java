package com.hksj.limit.aop;

import org.aopalliance.intercept.MethodInvocation;

public interface LimitDelegate {
    Object invoke(MethodInvocation invocation, String key, int limitPeriod, int limitCount) throws Throwable;
}
