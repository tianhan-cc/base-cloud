package com.tianhan.cloud.common.auth.utils;

import com.tianhan.cloud.common.auth.AuthContext;
import com.tianhan.cloud.common.auth.SimpleTokenInfo;
import com.tianhan.cloud.common.auth.UserDetail;
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
    public static UserDetail obtainUserDetail() {
        SimpleTokenInfo info = AuthContext.getJwtAttributesHolder();
        return obtainCache().obtainUserInfo(info.getUserKey(), info.getUsername());
    }

    public static UserDetail obtainUserDetail(String token) {
        SimpleTokenInfo info = new SimpleTokenInfo(token);
        return obtainCache().obtainUserInfo(info.getUserKey(), info.getUsername());
    }

    private static UserRedisCache obtainCache() {
        return SpringBeanUtils.getBean(UserRedisCache.class);
    }
}
