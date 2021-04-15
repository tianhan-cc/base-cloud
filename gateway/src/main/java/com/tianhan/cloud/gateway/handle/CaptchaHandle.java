package com.tianhan.cloud.gateway.handle;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.RandomUtil;
import com.tianhan.cloud.common.core.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author NieAnTai
 * @Date 2021/3/22 11:10 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Component
public class CaptchaHandle {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    public static final String CAPTCHA = "login:captcha:";

    public Mono<ServerResponse> createCaptcha(ServerRequest request) {
        CircleCaptcha captcha = new CircleCaptcha(200, 100, 5, 20);
        String code = captcha.getCode();
        String image = captcha.getImageBase64();
        String validate = RandomUtil.randomString(8);

        redisTemplate.boundValueOps(obtainRedisKey(validate)).set(code, 2, TimeUnit.MINUTES);

        Map<String, Object> data = new HashMap<>();
        data.put("validate", validate);
        data.put("image", image);
        return ServerResponse.ok().bodyValue(ResponseResult.buildNormalResponse(data));
    }

    public boolean validatorCaptcha(String validate, String code) {
        String cache = redisTemplate.boundValueOps(obtainRedisKey(validate)).get();
        return StringUtils.isNotBlank(cache) && cache.equals(code);
    }

    public static String obtainRedisKey(String validate) {
        return String.format("%s%s", CAPTCHA, validate);
    }
}
