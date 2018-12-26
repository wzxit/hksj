package com.hksj.limit.config;

import com.hksj.limit.aop.LimitAutoScanProxy;
import com.hksj.limit.aop.LimitInterceptor;
import com.hksj.limit.constant.LimitConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LimitAopConfiguration {
    @Value("${" + LimitConstant.LIMIT_SCAN_PACKAGES + ":}")
    private String scanPackages;

    @Bean
    public LimitAutoScanProxy limitAutoScanProxy() {
        return new LimitAutoScanProxy(scanPackages);
    }

    @Bean
    public LimitInterceptor limitInterceptor() {
        return new LimitInterceptor();
    }
}
