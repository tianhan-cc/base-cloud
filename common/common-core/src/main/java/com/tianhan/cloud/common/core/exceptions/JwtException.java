package com.tianhan.cloud.common.core.exceptions;

/**
 * @Author NieAnTai
 * @Date 2021/3/28 9:34 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
public class JwtException extends BusinessException {
    public static final String CREATE_ERROR = "凭证创建失败!";
    public static final String VALIDATE_ERROR = "凭证验证失败!";

    public JwtException(Integer code, String message) {
        super(code, message);
    }

    public static JwtException buildCreateError() {
        return new JwtException(493, CREATE_ERROR);
    }

    public static JwtException buildValidateError() {
        return new JwtException(493, VALIDATE_ERROR);
    }
}
