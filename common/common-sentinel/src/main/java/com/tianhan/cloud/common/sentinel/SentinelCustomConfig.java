package com.tianhan.cloud.common.sentinel;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author NieAnTai
 * @Date 2021/12/7 4:08 下午
 * @Version 1.0r
 * @Email nieat@foxmail.com
 * @Description
 **/
@ConfigurationProperties(prefix = "sentinel.custom")
public class SentinelCustomConfig {
    private Boolean randomPort;
    private Integer defaultPort;

    public Boolean getRandomPort() {
        return randomPort;
    }

    public void setRandomPort(Boolean randomPort) {
        this.randomPort = randomPort;
    }

    public Integer getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(Integer defaultPort) {
        this.defaultPort = defaultPort;
    }
}
