package com.hksj.limit.config;

import com.hksj.limit.aop.LimitDelegate;
import com.hksj.limit.aop.LimitExecutor;
import com.hksj.limit.local.condition.LocalLimitCondition;
import com.hksj.limit.local.impl.GuavaLocalLimitExecutorImpl;
import com.hksj.limit.local.impl.LocalLimitDelegateImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalLimitConfiguration {
    @Bean
    @Conditional(LocalLimitCondition.class)
    public LimitDelegate localLimitDelegate() {
        return new LocalLimitDelegateImpl();
    }

    @Bean
    @Conditional(LocalLimitCondition.class)
    public LimitExecutor localLimitExecutor() {
        return new GuavaLocalLimitExecutorImpl();

        // return new JdkLimitExecutorImpl();
    }
}
