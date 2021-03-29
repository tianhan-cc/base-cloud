package com.tianhan.cloud.common.auth;

import com.tianhan.cloud.common.core.SystemConstant;
import com.tianhan.cloud.common.core.utils.ConvertMap;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author NieAnTai
 * @Date 2021/3/28 11:24 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description 登录用户信息存储
 **/
@Component
public class UserRedisCache {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public UserDetailsImpl obtainUserInfo(String userKey, String username) {
        return (UserDetailsImpl) redisTemplate.boundValueOps(String.format("%s:%s", userKey, username)).get();
    }

    public boolean validateToken(String tokenKey, String username, String accessToken) {
        Boolean f = redisTemplate.hasKey(String.format("%s:%s:%s", tokenKey, username, accessToken));
        return f != null ? f : false;
    }

    public void updateTokenExpire(String tokenKey, String username, String accessToken, Long timeout, TimeUnit unit) {
        redisTemplate.expire(String.format("%s:%s:%s", tokenKey, unit, accessToken), timeout, unit);
    }

    public void storage(UserDetailsImpl user, String accessToken, String tokenKey, String userKey, Long timeout, TimeUnit unit) {
        // 存储TOKEN信息
        storageToken(user.getUsername(), accessToken, tokenKey, timeout, unit);
        // 存储登录用户信息
        storageUser(user, userKey);
    }

    public void storageToken(String username, String accessToken, String tokenKey, Long timeout, TimeUnit unit) {
        Set<String> tokenKeys = redisTemplate.keys(String.format("%s:%s:*", tokenKey, username));
        int size = tokenKeys != null ? tokenKeys.size() : 0;
        if (size >= SystemConstant.ACCOUNT_LOGIN_MAX) {
            // 超出最大次数，挤掉快超时token
            ConvertMap tmp = new ConvertMap(size);
            tokenKeys.forEach(key -> tmp.put(key, redisTemplate.getExpire(key)));
            String outKey = tmp.keySet().stream()
                    .sorted((k1, k2) -> {
                        long result = tmp.getLong(k1) - tmp.getLong(k2);
                        return result == 0 ? 0 : result > 0 ? 1 : -1;
                    }).findFirst().get();
            redisTemplate.delete(outKey);
        }
        redisTemplate.boundValueOps(String.format("%s:%s:%s", tokenKey, username, accessToken)).set(accessToken, timeout, unit);
    }

    public void storageUser(UserDetailsImpl user, String userKey) {
        String key = String.format("%s:%s", userKey, user.getUsername());
        redisTemplate.boundValueOps(key).set(user);
    }
}
