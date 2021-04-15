package com.tianhan.cloud.gateway.config;

import com.tianhan.cloud.common.core.ResponseResult;
import com.tianhan.cloud.gateway.handle.CaptchaHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.Resource;

/**
 * @Author NieAnTai
 * @Date 2021/4/15 4:51 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description 路由映射
 **/
@Configuration
public class GlobeRouteConfig {
    @Resource
    private CaptchaHandle captchaHandle;

    @Bean
    public RouterFunction<ServerResponse> router() {
        return RouterFunctions.route()
                .GET("/captcha", captchaHandle::createCaptcha)
                .GET("/server_error", (request) -> ServerResponse.ok().bodyValue(ResponseResult.buildAbnormalResponse(500, "Hello,地球人!")))
                .build();
    }
}
