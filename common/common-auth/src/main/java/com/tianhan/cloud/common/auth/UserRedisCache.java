package com.tianhan.cloud.common.auth;

import com.tianhan.cloud.common.core.SystemConstant;
import com.tianhan.cloud.common.redis.CacheUtils;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.stereotype.Component;

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
    public UserDetail obtainUserInfo(String userKey, String username) {
        return CacheUtils.generalVal(String.format("%s:%s", userKey, username), UserDetail.class);
    }

    public boolean validateToken(String tokenKey, String username, String accessToken) {
        return CacheUtils.predicate(template -> {
            Double f = template.boundZSetOps(String.format("%s:%s", tokenKey, username)).score(accessToken);
            return f != null;
        });
    }

    public void updateTokenExpire(String tokenKey, String username, String accessToken, Long timeout, TimeUnit unit) {
        CacheUtils.exec(template -> template.expire(String.format("%s:%s:%s", tokenKey, unit, accessToken), timeout, unit));
    }

    public void storage(UserDetail user, String accessToken, String tokenKey, String userKey, Long timeout, TimeUnit unit) {
        // 存储TOKEN信息
        storageToken(user.getUsername(), accessToken, tokenKey, timeout, unit);
        // 存储登录用户信息
        storageUser(user, userKey, timeout, unit);
    }

    public void storageToken(String username, String accessToken, String tokenKey, Long timeout, TimeUnit unit) {
        CacheUtils.exec((template) -> {
            BoundZSetOperations<String, Object> zSet = template.boundZSetOps(String.format("%s:%s", tokenKey, username));
            if (zSet.zCard() >= SystemConstant.ACCOUNT_LOGIN_MAX) {
                // 超出最大登录次数
                zSet.removeRange(0, 0);
            }
            zSet.add(accessToken, (double) (System.currentTimeMillis() << 2));
            // 刷新Token过期时间
            zSet.expire(timeout, unit);
        });
    }

    public void storageUser(UserDetail user, String userKey, Long timeout, TimeUnit unit) {
        String key = String.format("%s:%s", userKey, user.getUsername());
        CacheUtils.handle().boundValueOps(key).set(user, timeout, unit);
    }
}
