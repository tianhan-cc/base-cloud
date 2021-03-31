package com.tianhan.cloud.gateway.auth;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
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

    public void validate() {
        if (StringUtils.isBlank(username)) {
            throw new BadCredentialsException("用户名不能为空!");
        }
        if (StringUtils.isBlank(password)) {
            throw new BadCredentialsException("密码不能为空!");
        }
        if (StringUtils.isBlank(captcha)) {
            throw new BadCredentialsException("验证码不能为空!");
        }
        if (StringUtils.isBlank(validate)) {
            throw new BadCredentialsException("validateKey不能为空!");
        }
    }
}
