package com.tianhan.cloud.common.core;

import lombok.Data;

/**
 * @Author NieAnTai
 * @Date 2021/3/26 10:06 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description 请求响应体
 **/
@Data
public class ResponseResult {
    private Integer code;
    private String message;
    private Object data;

    public ResponseResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseResult buildNormalResponse(Object data) {
        return new ResponseResult(200, "", data);
    }

    public static ResponseResult buildAbnormalResponse(Integer code, String message) {
        return new ResponseResult(code, message, "");
    }
}
