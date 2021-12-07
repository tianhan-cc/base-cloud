package com.tianhan.cloud.common.sentinel;

import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * @Author NieAnTai
 * @Date 2021/12/7 3:25 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@EnableConfigurationProperties({SentinelCustomConfig.class})
@Configuration
public class SentinelConfiguration {
    @Resource
    private SentinelCustomConfig config;

    @PostConstruct
    public void init() {
        if (config.getRandomPort()) {
            TransportConfig.setRuntimePort(getRandomPort(config.getDefaultPort()));
        } else {
            TransportConfig.setRuntimePort(config.getDefaultPort());
        }
    }

    public static int getRandomPort(int defaultPort) {
        try (ServerSocket ss = new ServerSocket()) {
            ss.bind(null);
            return ss.getLocalPort();
        } catch (IOException ignore) {
            return defaultPort;
        }
    }
}
