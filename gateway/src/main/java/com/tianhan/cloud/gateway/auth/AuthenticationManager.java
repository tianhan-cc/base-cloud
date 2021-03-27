package com.tianhan.cloud.gateway.auth;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @Author NieAnTai
 * @Date 2021/3/28 12:33 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Component
public class AuthenticationManager implements  ReactiveAuthenticationManager{
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return null;
    }
}
