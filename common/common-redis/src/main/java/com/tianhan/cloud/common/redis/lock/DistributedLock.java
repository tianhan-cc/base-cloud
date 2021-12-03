package com.tianhan.cloud.common.redis.lock;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author NieAnTai
 * @Date 2021/12/1 2:59 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description redis 分布式锁
 **/
@Component
public class DistributedLock {
    private static RedisTemplate<String, Object> handle;

    public DistributedLock(RedisTemplate<String, Object> template) {
        handle = template;
    }

    public static Rlock obtainLock(String key) {
        return new Rlock(key, 60).setHandle(handle);
    }

    public static Rlock obtainLock(String key, int second) {
        return new Rlock(key, second).setHandle(handle);
    }
}
