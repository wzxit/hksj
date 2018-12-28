package com.hksj.limit.demo.config;

import com.hksj.limit.demo.interceptor.OverallInterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configurable
public class WebAppConfigurer extends  WebMvcConfigurerAdapter {
    @Bean
    OverallInterInterceptor getAccountInterceptor(){ //使用Bean 注解，不然使用时不能自动注入
        return  new OverallInterInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(getAccountInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
