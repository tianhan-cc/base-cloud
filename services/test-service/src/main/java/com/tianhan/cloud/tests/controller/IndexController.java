package com.tianhan.cloud.tests.controller;

import com.tianhan.cloud.common.auth.UserDetail;
import com.tianhan.cloud.common.auth.utils.SecurityUserUtil;
import com.tianhan.cloud.common.core.ResponseResult;
import com.tianhan.cloud.common.web.controller.BaseController;
import com.tianhan.cloud.usercenter.rpc.interfaces.IUsercenterRpc;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author NieAnTai
 * @Date 2021/4/15 3:55 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Slf4j
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {
    @DubboReference
    private IUsercenterRpc rpc;

    @GetMapping("/")
    public ResponseResult index() {
        UserDetail user = SecurityUserUtil.obtainUserDetail();
        return doJsonOut(user);
    }
}
