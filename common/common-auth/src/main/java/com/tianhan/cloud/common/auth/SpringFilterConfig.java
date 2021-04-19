package com.tianhan.cloud.common.auth;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.RequestContextFilter;

/**
 * @Author NieAnTai
 * @Date 2021/4/19 9:03 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@ConditionalOnClass(RequestContextFilter.class)
@ConditionalOnBean(RequestContextFilter.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SpringFilterConfig {

    @Bean
    public AuthContextFilter initFilter() {
        return new AuthContextFilter();
    }
}
