package com.tianhan.cloud.usercenter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author NieAnTai
 * @Date 2021/3/15 2:50 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
