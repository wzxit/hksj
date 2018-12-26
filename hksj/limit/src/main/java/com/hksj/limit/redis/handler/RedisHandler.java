package com.hksj.limit.redis.handler;

import org.springframework.data.redis.core.RedisTemplate;

public interface RedisHandler {
    // 获取RedisTemplate
    RedisTemplate<String, Object> getRedisTemplate();
}
