package com.tianhan.cloud.gateway.auth;

import com.tianhan.cloud.common.core.SystemConstant;
import com.tianhan.cloud.configuration.SecurityProperties;
import com.tianhan.cloud.gateway.handle.ResponseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @Author NieAnTai
 * @Date 2021/3/27 4:20 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description SpringSecurity WebFlux 安全配置
 **/
@EnableWebFluxSecurity
public class SecurityFilterConfig {
    @Resource
    private AccessAuthorizationManager accessAuthorizationManager;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private AuthenticationSuccessHandler successHandler;
    @Resource
    private SecurityContextRepository contextRepository;
    @Resource
    private AuthenticationConverter authenticationConverter;
    @Resource
    private SecurityProperties securityProperties;

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        // 设置白名单
        http.authorizeExchange().pathMatchers(securityProperties.getWhitelist()).permitAll()
                .anyExchange().access(accessAuthorizationManager);
        // 设置登录接口
        http.formLogin().loginPage(SystemConstant.LOGIN_URL)
                .authenticationManager(authenticationManager)
                .authenticationSuccessHandler(successHandler)
                .authenticationFailureHandler((webFilterExchange, e) -> ResponseHandler.doResponse(webFilterExchange.getExchange(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        // token 认证
        http.securityContextRepository(contextRepository);
        // 异常处理
        http.exceptionHandling()
                // 鉴权失败异常处理
                .authenticationEntryPoint(((exchange, e) -> ResponseHandler.doResponse(exchange, HttpStatus.UNAUTHORIZED, SystemConstant.LOGIN_REQUIRE_MSG)))
                .accessDeniedHandler((exchange,e) -> ResponseHandler.doResponse(exchange, HttpStatus.FORBIDDEN, SystemConstant.PERMISSION_ERROR_MSG));
        // 关闭不必要的功能
        SecurityWebFilterChain chain = http.httpBasic().disable()
                .logout().disable()
                .anonymous().disable()
                .csrf().disable().build();
        Flux<WebFilter> filters = chain.getWebFilters();
        filters.subscribe(filter -> {
            if (filter instanceof AuthenticationWebFilter) {
                // 全局传递参数
                ((AuthenticationWebFilter) filter).setServerAuthenticationConverter(authenticationConverter);
            }
        });
        return chain;
    }
}
