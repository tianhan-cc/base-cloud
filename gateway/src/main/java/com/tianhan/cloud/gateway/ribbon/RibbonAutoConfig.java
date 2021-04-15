package com.tianhan.cloud.gateway.ribbon;

import com.netflix.loadbalancer.IRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author NieAnTai
 * @Date 2021/4/12 11:15 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description Ribbon 自动加载
 **/
@Configuration
@ConditionalOnProperty(value = "nacos.isolation.enabled", havingValue = "true")
public class RibbonAutoConfig {
    @Bean
    public IRule rule() {
        return new ServerIsolationRule();
    }
}
