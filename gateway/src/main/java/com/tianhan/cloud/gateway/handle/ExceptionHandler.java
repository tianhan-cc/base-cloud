package com.tianhan.cloud.gateway.handle;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author NieAnTai
 * @Date 2021/12/2 10:35 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description 全局异常处理
 **/
@Component
public class ExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return ResponseHandler.doResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
