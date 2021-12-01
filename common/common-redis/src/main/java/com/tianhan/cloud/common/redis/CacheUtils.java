package com.tianhan.cloud.common.redis;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author NieAnTai
 * @Date 2021/12/1 4:55 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Component
public class CacheUtils {
    public static RedisTemplate<String, Object> template;

    public CacheUtils(RedisTemplate<String, Object> temp) {
        template = temp;
    }

    public static RedisTemplate<String, Object> handle() {
        return template;
    }

    public static void exec(Consumer<RedisTemplate<String, Object>> fun) {
        fun.accept(template);
    }

    public static Boolean predicate(Predicate<RedisTemplate<String, Object>> fun) {
        return fun.test(template);
    }

    public static <V> V getVal(Function<RedisTemplate<String, Object>, Object> fun, Class<V> vClass) {
        Object obj = fun.apply(template);
        String json = obj.toString();
        return StringUtils.isNotBlank(json) ? JSON.parseObject(json, vClass) : null;
    }

    public static <V> List<V> getList(Function<RedisTemplate<String, Object>, Object> fun, Class<V> vClass) {
        Object obj = fun.apply(template);
        String json = obj.toString();
        return StringUtils.isNotBlank(json) ? JSON.parseArray(json, vClass) : null;
    }
}
