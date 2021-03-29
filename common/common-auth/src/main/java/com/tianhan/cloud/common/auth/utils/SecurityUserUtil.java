package com.tianhan.cloud.common.auth.utils;

import com.tianhan.cloud.common.auth.UserDetailsImpl;
import com.tianhan.cloud.common.auth.UserRedisCache;
import com.tianhan.cloud.common.core.utils.SpringBeanUtils;

/**
 * @Author NieAnTai
 * @Date 2021/3/27 11:43 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
public class SecurityUserUtil {
    /**
     * 获取当前用户信息
     *
     * @return userinfo
     */
    public static UserDetailsImpl obtainUserDetail() {
        return null;
    }

    public static UserDetailsImpl obtainUserDetail(String token) {
        JWTUtil.Infos jwt = JWTUtil.decodedConvertInfos(token);
        return obtainCache().obtainUserInfo(jwt.getUserKey(), jwt.getUsername());
    }

    private static UserRedisCache obtainCache() {
        return SpringBeanUtils.getBean(UserRedisCache.class);
    }
}
