package com.hksj.limit.aop;

import com.hksj.limit.annotation.EnableLimit;
import com.hksj.limit.constant.LimitConstant;
import com.hksj.limit.matrix.selector.AbstractImportSelector;
import com.hksj.limit.matrix.selector.RelaxedPropertyResolver;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class LimitImportSelector  extends AbstractImportSelector<EnableLimit> {
    @Override
    protected boolean isEnabled() {
        return new RelaxedPropertyResolver(getEnvironment()).getProperty(LimitConstant.LIMIT_ENABLED, Boolean.class, Boolean.TRUE);
    }
}
