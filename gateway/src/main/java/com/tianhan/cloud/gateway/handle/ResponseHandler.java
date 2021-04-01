package com.tianhan.cloud.gateway.handle;

import com.alibaba.fastjson.JSON;
import com.tianhan.cloud.common.core.ResponseResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

/**
 * 消息返回处理类
 */
@Component
public class ResponseHandler {

    public static Mono<Void> doResponse(ServerWebExchange exchange, ResponseResult result) {
        return Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");
            // 跨域请求
//        headers.set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        headers.set(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.setStatusCode(HttpStatus.OK);
            byte[] dataBytes = JSON.toJSONBytes(result);
            DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
            response.writeAndFlushWith(Flux.just(ByteBufFlux.just(bodyDataBuffer)));
        });
    }

    public static Mono<Void> doResponse(ServerWebExchange exchange, HttpStatus status, String msg) {
        return doResponse(exchange, new ResponseResult(status.value(), msg));
    }

}
