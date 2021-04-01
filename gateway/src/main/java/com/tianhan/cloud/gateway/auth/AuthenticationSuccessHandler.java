package com.tianhan.cloud.gateway.auth;

import com.tianhan.cloud.common.auth.UserDetailsImpl;
import com.tianhan.cloud.common.auth.UserRedisCache;
import com.tianhan.cloud.common.auth.utils.JWTUtil;
import com.tianhan.cloud.common.core.ResponseResult;
import com.tianhan.cloud.common.core.SystemConstant;
import com.tianhan.cloud.gateway.handle.ResponseHandler;
import com.tianhan.cloud.gateway.utils.ObtainRemoteIp;
import com.tianhan.cloud.usercenter.rpc.interfaces.IUsercenterRpc;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author NieAnTai
 * @Date 2021/3/28 12:39 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Component
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
    @Resource
    private UserRedisCache cache;
    @Resource
    private IUsercenterRpc userRpc;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerHttpRequest request = webFilterExchange.getExchange().getRequest();
        UsernamePasswordAuthenticationToken tmp = (UsernamePasswordAuthenticationToken) authentication;
        UserDetailsImpl user = (UserDetailsImpl) tmp.getPrincipal();
        String token = JWTUtil.getAccessToken(user.getUsername(), SystemConstant.LOGIN_SOURCE);
        user.setLoginSource(SystemConstant.LOGIN_SOURCE);
        userRpc.loginRecord(user.getUsername(),
                ObtainRemoteIp.create().headers(request.getHeaders()).inetSocket(request.getRemoteAddress()).build().obtainIp(),
                user.getLoginSource());
        // 存储用户信息
        cache.storage(user, token, SystemConstant.TOKEN_KEY, SystemConstant.USER_KEY, 3L, TimeUnit.HOURS);
        return ResponseHandler.doResponse(webFilterExchange.getExchange(),
                new ResponseResult(HttpStatus.OK.value(), SystemConstant.LOGIN_OK_MSG, token));
    }
}
