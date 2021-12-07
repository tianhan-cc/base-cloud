package com.tianhan.cloud.common.redis.lock;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @Author NieAnTai
 * @Date 2021/12/2 11:02 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
public class Rlock {
    static final String LOCK_SCRIPT =
            "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then \n" +
                    "    return redis.call('expire',KEYS[1],ARGV[2]) \n" +
                    "end \n" +
                    "    return 0";
    static final String RELEASE_SCRIPT =
            "if redis.call('GET',KEYS[1]) == ARGV[1] then\n" +
                    "    redis.call('EXPIRE',KEYS[1],0)\n" +
                    "end";


    private final List<String> key;
    private final String uuid;
    private final int second;
    private long delay = 500L;
    private int retry = 40;
    private RedisTemplate<String, Object> handle;

    protected Rlock(String key, int second) {
        this.key = Collections.singletonList(key);
        this.uuid = UUID.randomUUID().toString();
        this.second = second;
    }

    public boolean lock() {
        return lock(false);
    }

    public boolean lock(boolean endless) {
        Long result = 0L;
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(LOCK_SCRIPT, Long.class);
        int var = retry;
        try {
            for (; var > 0; ) {
                if (!endless) {
                    var--;
                }
                result = handle.execute(script, key, uuid, second);
                assert result != null;
                if (result == 1L) {
                    break;
                }
                Thread.sleep(delay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == 1L;
    }

    public void release() {
        DefaultRedisScript<Integer> script = new DefaultRedisScript<>(RELEASE_SCRIPT, Integer.class);
        handle.execute(script, key, uuid);
    }

    public Rlock setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public Rlock setRetry(int retry) {
        this.retry = retry;
        return this;
    }

    protected Rlock setHandle(RedisTemplate<String, Object> handle) {
        this.handle = handle;
        return this;
    }
}
