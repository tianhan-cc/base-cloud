package com.tianhan.cloud.common.core.exceptions;

/**
 * @Author NieAnTai
 * @Date 2021/3/28 9:27 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
