package com.hksj.limit.redis.redisson.adapter;

import com.hksj.limit.redis.redisson.handler.RedissonHandler;

public interface RedissonAdapter {

    RedissonHandler getRedissonHandler();
}
