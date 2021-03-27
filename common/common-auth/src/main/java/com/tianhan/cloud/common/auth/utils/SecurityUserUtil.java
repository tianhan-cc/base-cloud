package com.tianhan.cloud.common.auth.utils;

import com.tianhan.cloud.common.auth.UserDetail;
import com.tianhan.cloud.common.core.utils.SpringBeanUtils;
import org.springframework.data.redis.core.RedisTemplate;

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
        return null;
    }

    /**
     * 存储用户信息
     *
     * @param token jwt
     * @param user  userinfo
     */
    public static void storageUser(String token, UserDetail user) {

    }

    private static RedisTemplate<String, Object> obtainRedisTemplate() {
        return SpringBeanUtils.getBean("redisTemplate");
    }
}
