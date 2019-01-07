package com.hksj.limit.local.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.hksj.limit.aop.LimitExecutor;
import com.hksj.limit.common.constant.LimitConstant;
import com.hksj.limit.common.exception.LimitException;
import com.hksj.limit.common.util.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuavaLocalLimitExecutorImpl implements LimitExecutor {
    @Value("${" + LimitConstant.PREFIX + "}")
    private String prefix;

    @Value("${" + LimitConstant.FREQUENT_LOG_PRINT + "}")
    private Boolean frequentLogPrint;

    //ConcurrentHashMap是现成安全的map
    private volatile Map<String, RateLimiterEntity> rateLimiterEntityMap = new ConcurrentHashMap<String, RateLimiterEntity>();

    @Override
    public boolean tryAccess(String name, String key, int limitPeriod, int limitCount) throws Exception {
        if (StringUtils.isEmpty(name)) {
            throw new LimitException("Name不能为空");
        }

        if (StringUtils.isEmpty(key)) {
            throw new LimitException("Key不能为空");
        }

        String compositeKey = KeyUtil.getCompositeKey(prefix, name, key);

        return tryAccess(compositeKey, limitPeriod, limitCount);
    }

    @Override
    public boolean tryAccess(String compositeKey, int limitPeriod, int limitCount) throws Exception {
        if (StringUtils.isEmpty(compositeKey)) {
            throw new LimitException("Composite值null或空");
        }

        //谷歌函数还不熟悉，只会每秒多少次访问
        if (limitPeriod != 1) {
            throw new LimitException("Limit period对于Guava速率限制器，必须是1秒");
        }

        RateLimiterEntity rateLimiterEntity = getRateLimiterEntity(compositeKey, limitCount);
        RateLimiter rateLimiter = rateLimiterEntity.getRateLimiter();

        return rateLimiter.tryAcquire();
    }

    private RateLimiterEntity getRateLimiterEntity(String compositeKey, double rate) {
        RateLimiterEntity rateLimiterEntity = rateLimiterEntityMap.get(compositeKey);
        if (rateLimiterEntity == null) {
            RateLimiter newRateLimiter = RateLimiter.create(rate);

            RateLimiterEntity newRateLimiterEntity = new RateLimiterEntity();
            newRateLimiterEntity.setRateLimiter(newRateLimiter);
            newRateLimiterEntity.setRate(rate);

            rateLimiterEntity = rateLimiterEntityMap.putIfAbsent(compositeKey, newRateLimiterEntity);
            if (rateLimiterEntity == null) {
                rateLimiterEntity = newRateLimiterEntity;
            }
        } else {
            if (rateLimiterEntity.getRate() != rate) {
                rateLimiterEntity.getRateLimiter().setRate(rate);
                rateLimiterEntity.setRate(rate);
            }
        }

        return rateLimiterEntity;
    }

    // 因为 rateLimiter.setRate(permitsPerSecond)会执行一次synchronized，为避免不必要的同步，故通过RateLimiterEntity去封装，做一定的冗余设计
    private class RateLimiterEntity {
        private RateLimiter rateLimiter;
        private double rate;

        public RateLimiter getRateLimiter() {
            return rateLimiter;
        }

        public void setRateLimiter(RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }
    }
}
