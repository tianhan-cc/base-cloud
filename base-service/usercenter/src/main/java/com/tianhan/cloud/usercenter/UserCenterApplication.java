package com.tianhan.cloud.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author NieAnTai
 * @Date 2021/2/23 9:33 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.tianhan"})
@SpringBootApplication
public class UserCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class,args);
    }
}
