package com.tianhan.cloud.usercenter.controller;

import com.tianhan.cloud.common.auth.UserDetail;
import com.tianhan.cloud.common.auth.utils.SecurityUserUtil;
import com.tianhan.cloud.common.core.ResponseResult;
import com.tianhan.cloud.usercenter.param.UserParam;
import com.tianhan.cloud.usercenter.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

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
public class UserController {
    @Resource
    private IUserService userService;
    static final Random R = new Random(100);

    @PostMapping("/info/{username}")
    public ResponseResult info(@PathVariable String username) {
        int rr = R.nextInt(10);
        if (rr <= 3) {
            throw new RuntimeException("随机异常!");
        }

        UserDetail user = SecurityUserUtil.obtainUserDetail();
        log.info("用户 {} 与 {} 获取目标用户 {} 信息", user.getUsername(), new Date(), username);
        return new ResponseResult(200, "请求成功!", userService.info(username));
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody UserParam user) {
        userService.createUser(user);
        return new ResponseResult(200, "请求成功!");
    }
}
