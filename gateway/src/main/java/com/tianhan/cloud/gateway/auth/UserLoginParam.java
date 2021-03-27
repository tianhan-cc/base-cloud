package com.tianhan.cloud.gateway.auth;

import lombok.Data;
import org.springframework.util.MultiValueMap;

/**
 * @Author NieAnTai
 * @Date 2021/3/27 10:36 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Data
public class UserLoginParam {
    private String username;
    private String password;
    private String captcha;
    private String validate;

    public UserLoginParam(MultiValueMap<String, String> params) {
        username = params.getFirst("username");
        password = params.getFirst("password");
        captcha = params.getFirst("captcha");
        validate = params.getFirst("validate");
    }
}
