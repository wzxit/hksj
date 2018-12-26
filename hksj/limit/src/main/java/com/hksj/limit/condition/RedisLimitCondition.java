package com.hksj.limit.condition;

import com.hksj.limit.constant.LimitConstant;

public class RedisLimitCondition extends AquariusCondition {
    public RedisLimitCondition() {
        super(LimitConstant.LIMIT_TYPE, LimitConstant.LIMIT_TYPE_REDIS);
    }
}
