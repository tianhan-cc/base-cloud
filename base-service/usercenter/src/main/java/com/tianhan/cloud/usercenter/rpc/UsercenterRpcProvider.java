package com.tianhan.cloud.usercenter.rpc;

import com.tianhan.cloud.common.auth.UserDetailsUpgrade;
import com.tianhan.cloud.usercenter.rpc.interfaces.IUsercenterRpc;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author NieAnTai
 * @Date 2021/3/15 3:00 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@DubboService
public class UsercenterRpcProvider implements IUsercenterRpc {

    @Override
    public UserDetailsUpgrade obtainUser(String username) {
        return new UserDetailsUpgrade(username, username);
    }

    @Override
    public void loginRecord(String username, String loginIp, String loginSource) {

    }
}
