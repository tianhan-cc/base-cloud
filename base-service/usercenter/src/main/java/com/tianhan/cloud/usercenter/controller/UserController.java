package com.tianhan.cloud.usercenter.controller;

import com.tianhan.cloud.common.core.ResponseResult;
import com.tianhan.cloud.common.web.controller.BaseController;
import com.tianhan.cloud.usercenter.param.UserParam;
import com.tianhan.cloud.usercenter.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author NieAnTai
 * @Date 2021/3/15 2:50 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private IUserService userService;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody UserParam user) {
        userService.createUser(user);
        return doDefaultMsg();
    }
}
