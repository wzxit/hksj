package com.hksj.limit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ AquariusConfiguration.class, LimitAopConfiguration.class, RedisLimitConfiguration.class, LocalLimitConfiguration.class })
public class LimitConfiguration {
}
