package com.hksj.limit.redis.redisson.handler;

import com.hksj.limit.common.exception.LimitException;
import com.hksj.limit.common.util.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedissonHandlerImpl implements RedissonHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RedissonHandlerImpl.class);

    private RedissonClient redisson;

    public RedissonHandlerImpl(String yamlConfigPath) {
        try {
            Config config = RedissonUtil.createYamlFileConfig(yamlConfigPath);

            initialize(config);
        } catch (Exception e) {
            LOG.error("初始化 Redisson 失败", e);
        }
    }

    public RedissonHandlerImpl(Config config) {
        try {
            initialize(config);
        } catch (Exception e) {
            LOG.error("初始化 Redisson 失败", e);
        }
    }

    // 创建Redisson
    private void initialize(Config config) {
        create(config);
    }

    // 使用config创建Redisson
    private void create(Config config) {
        LOG.info("开始初始化 Redisson...");

        if (redisson != null) {
            throw new LimitException("Redisson 不能为空, 已经初始化");
        }

        redisson = Redisson.create(config);
    }

    // 关闭Redisson客户端连接
    @Override
    public void close() {
        LOG.info("Start to close Redisson...");

        validateStartedStatus();

        redisson.shutdown();
    }

    // 获取Redisson客户端是否初始化
    @Override
    public boolean isInitialized() {
        return redisson != null;
    }

    // 获取Redisson客户端连接是否正常
    @Override
    public boolean isStarted() {
        if (redisson == null) {
            throw new LimitException("Redisson未初始化");
        }

        return !redisson.isShutdown() && !redisson.isShuttingDown();
    }

    // 检查Redisson是否是启动状态
    @Override
    public void validateStartedStatus() {
        if (redisson == null) {
            throw new LimitException("Redisson未初始化");
        }

        if (!isStarted()) {
            throw new LimitException("Redisson is closed");
        }
    }

    // 检查Redisson是否是关闭状态
    @Override
    public void validateClosedStatus() {
        if (redisson == null) {
            throw new LimitException("Redisson未初始化");
        }

        if (isStarted()) {
            throw new LimitException("Redisson is started");
        }
    }

    // 获取Redisson客户端
    @Override
    public RedissonClient getRedisson() {
        return redisson;
    }
}
