package com.hksj.limit.config;

import com.hksj.limit.aop.LimitDelegate;
import com.hksj.limit.aop.LimitExecutor;
import com.hksj.limit.condition.RedisLimitCondition;
import com.hksj.limit.redis.handler.RedisHandler;
import com.hksj.limit.redis.handler.RedisHandlerImpl;
import com.hksj.limit.redis.impl.RedisLimitDelegateImpl;
import com.hksj.limit.redis.impl.RedisLimitExecutorImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ RedisConfiguration.class })
public class RedisLimitConfiguration {
    @Bean
    @Conditional(RedisLimitCondition.class)
    public LimitDelegate redisLimitDelegate() {
        return new RedisLimitDelegateImpl();
    }

    @Bean
    @Conditional(RedisLimitCondition.class)
    public LimitExecutor redisLimitExecutor() {
        return new RedisLimitExecutorImpl();
    }

    @Bean
    @Conditional(RedisLimitCondition.class)
    @ConditionalOnMissingBean
    public RedisHandler redisHandler() {
        return new RedisHandlerImpl();
    }
}
