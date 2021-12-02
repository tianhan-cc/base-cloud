package com.tianhan.cloud.common.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tianhan.cloud.common.auth.utils.JWTUtil;
import com.tianhan.cloud.common.core.SystemConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author NieAnTai
 * @Date 2021/4/19 10:38 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description 简易登录用户信息
 **/
@Data
public final class SimpleTokenInfo implements Serializable {
    private String userid;
    private String username;
    private String platform;
    private String userKey;
    private String tokenKey;

    public SimpleTokenInfo() {
    }

    public SimpleTokenInfo(String accessToken) {
        DecodedJWT decoded = JWTUtil.decodedJwt(accessToken);
        userid = decoded.getClaim("USERID").asString();
        username = decoded.getSubject();
        platform = decoded.getClaim(SystemConstant.TOKEN_PLATFORM).asString();
        userKey = platform.equals(SystemConstant.LOGIN_SOURCE) ? SystemConstant.USER_KEY : SystemConstant.APP_USER_KEY;
        tokenKey = platform.equals(SystemConstant.LOGIN_SOURCE) ? SystemConstant.STORAGE_TOKEN_KEY : SystemConstant.APP_STORAGE_TOKEN_KEY;
    }
}
