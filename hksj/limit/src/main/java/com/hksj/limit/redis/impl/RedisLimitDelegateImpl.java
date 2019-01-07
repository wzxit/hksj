package com.hksj.limit.redis.impl;

import com.hksj.limit.aop.LimitDelegate;
import com.hksj.limit.aop.LimitExecutor;
import com.hksj.limit.common.exception.LimitException;
import com.hksj.limit.constant.LimitConstant;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class RedisLimitDelegateImpl implements LimitDelegate {
    private static final Logger LOG = LoggerFactory.getLogger(RedisLimitDelegateImpl.class);

    @Autowired
    private LimitExecutor limitExecutor;

    @Value("${" + LimitConstant.LIMIT_AOP_EXCEPTION_IGNORE + ":true}")
    private Boolean limitAopExceptionIgnore;

    @Override
    public Object invoke(MethodInvocation invocation, String key, int limitPeriod, int limitCount) throws Throwable {
        boolean status = true;
        try {
            status = limitExecutor.tryAccess(key, limitPeriod, limitCount);
        } catch (Exception e) {
            if (limitAopExceptionIgnore) {
                LOG.error("Redis exception 限制期发生异常", e);

                return invocation.proceed();
            } else {
                throw e;
            }
        }

        if (status) {
            return invocation.proceed();
        } else {
            throw new LimitException("Reach max limited access count=" + limitCount + " within period=" + limitPeriod + " seconds");
        }
    }
}
