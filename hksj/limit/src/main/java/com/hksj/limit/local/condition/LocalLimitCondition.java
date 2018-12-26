package com.hksj.limit.local.condition;

import com.hksj.limit.condition.AquariusCondition;
import com.hksj.limit.constant.LimitConstant;

public class LocalLimitCondition extends AquariusCondition {
    public LocalLimitCondition() {
        super(LimitConstant.LIMIT_TYPE, LimitConstant.LIMIT_TYPE_LOCAL);
    }
}
