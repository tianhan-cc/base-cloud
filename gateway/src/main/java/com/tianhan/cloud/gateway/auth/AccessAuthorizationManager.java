package com.tianhan.cloud.gateway.auth;

import com.tianhan.cloud.common.auth.UserDetailsImpl;
import com.tianhan.cloud.configuration.SecurityProperties;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Author NieAnTai
 * @Date 2021/3/28 12:40 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Component
public class AccessAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Resource
    private SecurityProperties securityProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        return authentication.map(var -> check((UserDetailsImpl) var.getPrincipal(), object.getExchange())).defaultIfEmpty(new AuthorizationDecision(false));
    }

    public AuthorizationDecision check(UserDetailsImpl user, ServerWebExchange exchange) {
        boolean granted = false;
        String path = exchange.getRequest().getPath().toString();
        if (user.getAdminFlag() == 1) {
            granted = true;
        }
        boolean permit = Arrays.stream(securityProperties.getPermit()).anyMatch(x -> antPathMatcher.match("/**/" + x, path));
        if (permit) {
            granted = true;
        }
        boolean existAuthorities = user.getAuthorities().stream().anyMatch(authority -> antPathMatcher.match("/**/" + authority.getAuthority(), path));
        if (existAuthorities) {
            granted = true;
        }
        return new AuthorizationDecision(granted);
    }
}
