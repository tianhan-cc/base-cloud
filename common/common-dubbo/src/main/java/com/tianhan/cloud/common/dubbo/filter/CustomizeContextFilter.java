package com.tianhan.cloud.common.dubbo.filter;

import com.tianhan.cloud.common.auth.AuthContext;
import com.tianhan.cloud.common.auth.SimpleTokenInfo;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @Author NieAnTai
 * @Date 2021/4/16 9:48 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description Dubbo 调用上下文传递用户信息
 **/
@Activate(group = {CommonConstants.CONSUMER, CommonConstants.PROVIDER}, order = -10000)
public class CustomizeContextFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();

        SimpleTokenInfo dubbo = null;
        SimpleTokenInfo local = AuthContext.getJwtAttributesHolder();

        if (local != null) {
            // 传递到下一个Dubbo服务
            context.setAttachment("info", local);
        } else {
            dubbo = (SimpleTokenInfo) context.getObjectAttachment("info");
            if (dubbo != null) {
                // 接受上游服务传递下来的信息，绑定到本地
                AuthContext.setJwtAttributesHolder(local);
            }
        }

        try {
            return invoker.invoke(invocation);
        } finally {
            if (dubbo != null) {
                // 调用结束，清空缓存
                AuthContext.remove();
            }
        }
    }
}
