package com.tianhan.cloud.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author NieAnTai
 * @Date 2021/2/23 9:33 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.tianhan"})
@MapperScan(basePackages = {"com.tianhan.**.dao"})
public class UserCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }
}
