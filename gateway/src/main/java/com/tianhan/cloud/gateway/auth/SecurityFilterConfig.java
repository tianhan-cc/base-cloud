package com.tianhan.cloud.gateway.auth;

import com.tianhan.cloud.configuration.SecurityProperties;
import org.springframework.context.annotation.Bean;
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
    private AuthenticationFailureHandler failureHandler;
    @Resource
    private SecurityContextRepository contextRepository;
    @Resource
    private AuthenticationEntryPoint entryPoint;
    @Resource
    private AccessDeniedHandler accessDeniedHandler;
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
        http.formLogin().loginPage("/auth/login")
                .authenticationManager(authenticationManager)
                .authenticationSuccessHandler(successHandler)
                .authenticationFailureHandler(failureHandler);
        // token 认证
        http.securityContextRepository(contextRepository);
        // 异常处理
        http.exceptionHandling().authenticationEntryPoint(entryPoint).accessDeniedHandler(accessDeniedHandler);
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
