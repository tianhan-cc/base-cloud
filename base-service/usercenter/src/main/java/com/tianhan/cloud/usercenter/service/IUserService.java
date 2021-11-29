package com.tianhan.cloud.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tianhan.cloud.common.auth.UserDetailsUpgrade;
import com.tianhan.cloud.usercenter.entity.UserEntity;
import com.tianhan.cloud.usercenter.param.LoginParam;
import com.tianhan.cloud.usercenter.param.UserParam;

/**
 * @Author NieAnTai
 * @Date 2021/11/26 3:59 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
public interface IUserService extends IService<UserEntity> {
    /**
     * 登录接口
     *
     * @param login 登录信息
     * @return 用户信息
     */
    UserDetailsUpgrade login(LoginParam login);

    /**
     * 添加新用户
     * @param user 添加用户信息
     */
    void createUser(UserParam user);
}
