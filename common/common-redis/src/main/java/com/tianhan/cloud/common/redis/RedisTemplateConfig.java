package com.tianhan.cloud.common.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author NieAnTai
 * @Date 2021/3/15 10:28 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisTemplateConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        log.info(String.format("=====启用redis,工厂实例:%s=====", factory.getClass().getSimpleName()));
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer<Object> jackson = new FastJsonRedisSerializer<>(Object.class);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setDefaultSerializer(jackson);
        redisTemplate.setValueSerializer(jackson);
        redisTemplate.setHashValueSerializer(jackson);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
