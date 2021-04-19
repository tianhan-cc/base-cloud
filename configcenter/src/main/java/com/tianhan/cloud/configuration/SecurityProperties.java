package com.tianhan.cloud.configuration;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * @Author NieAnTai
 * @Date 2021/3/27 10:52 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Configuration
@Data
public class SecurityProperties {
    /**
     * 白名单
     */
    private String[] whitelist = new String[]{"/captcha", "/usercenter/user/hello","/other/index/"};
    /**
     * 无须鉴权
     */
    private String[] permit = new String[]{"/captcha"};

}
