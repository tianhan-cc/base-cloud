package com.tianhan.cloud.gateway.auth;

import com.tianhan.cloud.common.auth.SimpleTokenInfo;
import com.tianhan.cloud.common.auth.UserDetail;
import com.tianhan.cloud.common.auth.UserRedisCache;
import com.tianhan.cloud.common.core.SystemConstant;
import com.tianhan.cloud.common.core.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @Author NieAnTai
 * @Date 2021/3/28 12:42 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description Token校验
 **/
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {
    @Resource
    private UserRedisCache cache;

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        // 获取token
        String token = request.getHeaders().getFirst(SystemConstant.TOKEN_KEY);
        token = StringUtils.isNotBlank(token) ? token : request.getQueryParams().getFirst(SystemConstant.TOKEN_KEY);

        if (StringUtils.isNotBlank(token)) {
            SimpleTokenInfo info = new SimpleTokenInfo(token);
            if (cache.validateToken(info.getTokenKey(), info.getUsername(), token)) {
                UserDetail user = cache.obtainUserInfo(info.getUserKey(), info.getUsername());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword());
                return Mono.just(new SecurityContextImpl(authentication));
            } else {
                throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), SystemConstant.LOGIN_TIMEOUT_MSG);
            }
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }
}
