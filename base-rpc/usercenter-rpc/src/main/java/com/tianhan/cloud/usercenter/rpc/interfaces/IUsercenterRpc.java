package com.tianhan.cloud.usercenter.rpc.interfaces;

import com.tianhan.cloud.common.auth.UserDetail;

/**
 * @Author NieAnTai
 * @Date 2021/3/15 2:56 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
public interface IUsercenterRpc {

    void test();

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return userinfo
     */
    UserDetail obtainUser(String username);

    /**
     * 登录操作记录
     *
     * @param username    用户名
     * @param loginIp     登录IP
     * @param loginSource 登录来源
     */
    void loginRecord(String username, String loginIp, String loginSource);
}
