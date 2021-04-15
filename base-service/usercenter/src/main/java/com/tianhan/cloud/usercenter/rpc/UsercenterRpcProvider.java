package com.tianhan.cloud.usercenter.rpc;

import com.tianhan.cloud.common.auth.UserDetailsImpl;
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
    public UserDetailsImpl obtainUser(String username) {
        return null;
    }

    @Override
    public void loginRecord(String username, String loginIp, String loginSource) {

    }
}
