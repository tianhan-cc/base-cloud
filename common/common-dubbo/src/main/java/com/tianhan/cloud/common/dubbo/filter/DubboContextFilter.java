package com.tianhan.cloud.common.dubbo.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @Author NieAnTai
 * @Date 2021/4/16 9:48 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Activate(group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER})
public class DubboContextFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }
}
