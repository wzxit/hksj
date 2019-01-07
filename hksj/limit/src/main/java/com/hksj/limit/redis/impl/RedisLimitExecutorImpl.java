package com.hksj.limit.redis.impl;

import com.hksj.limit.aop.LimitExecutor;
import com.hksj.limit.common.constant.LimitConstant;
import com.hksj.limit.common.exception.LimitException;
import com.hksj.limit.common.util.KeyUtil;
import com.hksj.limit.redis.handler.RedisHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class RedisLimitExecutorImpl implements LimitExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(RedisLimitExecutorImpl.class);

    @Autowired
    private RedisHandler redisHandler;

    @Value("${" + LimitConstant.PREFIX + "}")
    private String prefix;

    @Value("${" + LimitConstant.FREQUENT_LOG_PRINT + "}")
    private Boolean frequentLogPrint;

    private RedisScript<Number> redisScript;

    @PostConstruct
    public void initialize() {
        String luaScript = buildLuaScript();
        redisScript = new DefaultRedisScript<Number>(luaScript, Number.class);
    }

    private String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c");
        lua.append("\nc = redis.call('get',KEYS[1])");
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then"); // 调用不超过最大值，则直接返回
        lua.append("\nreturn c;");
        lua.append("\nend");
        lua.append("\nc = redis.call('incr',KEYS[1])"); // 执行计算器自加
        lua.append("\nif tonumber(c) == 1 then");
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])"); // 从第一次调用开始限流，设置对应键值的过期
        lua.append("\nend");
        lua.append("\nreturn c;");

        return lua.toString();
    }

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
            throw new LimitException("Composite key不能为空");
        }

        List<String> keys = new ArrayList<String>();
        keys.add(compositeKey);

        RedisTemplate<String, Object> redisTemplate = redisHandler.getRedisTemplate();
        Number count = redisTemplate.execute(redisScript, keys, limitCount, limitPeriod);


        if (frequentLogPrint) {
            LOG.info("Access try count is {} for key={}", count, compositeKey);
        }
        if(count==null){
            return false;
        }


        return count.intValue() <= limitCount;
    }
}
