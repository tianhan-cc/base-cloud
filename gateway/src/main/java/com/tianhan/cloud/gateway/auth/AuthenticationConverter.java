package com.tianhan.cloud.gateway.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author NieAnTai
 * @Date 2021/3/27 10:28 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Configuration
public class AuthenticationConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        UserLoginParam params = new UserLoginParam(exchange.getRequest().getQueryParams());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(params.getUsername(), params.getPassword());
        authentication.setDetails(params);
        return Mono.just(authentication);
    }
}
