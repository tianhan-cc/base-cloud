package com.tianhan.cloud.usercenter.rpc;

import com.tianhan.cloud.usercenter.rpc.interfaces.IUserCenterRpc;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author NieAnTai
 * @Date 2021/3/15 3:00 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@DubboService
public class UserCenterRpcProvider implements IUserCenterRpc {
    @Override
    public String invokeRpc() {
        return "userCenterRpc";
    }
}
