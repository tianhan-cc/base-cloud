package com.tianhan.cloud.common.dubbo.filter;


import com.netflix.hystrix.*;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @Author NieAnTai
 * @Date 2021/4/19 4:37 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description Hystrix 熔断拦截器
 **/
@Activate(group = CommonConstants.CONSUMER, order = 1000)
public class HystrixFilter implements Filter {
    public static final int DEFAULT_THREAD_POOL_SIZE = 30;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return new DubboHystrixCommand(invoker, invocation).run();
    }

    static final class DubboHystrixCommand extends HystrixCommand<Result> {
        private final Invoker<?> invoker;
        private final Invocation invocation;

        public DubboHystrixCommand(Invoker<?> invoker, Invocation invocation) {
            super(
                    Setter
                            .withGroupKey(HystrixCommandGroupKey.Factory.asKey(invocation.getServiceName()))
                            .andCommandKey(HystrixCommandKey.Factory.asKey(invocation.getMethodName()))
                            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                    // 错误20次计算错误率
                                    .withCircuitBreakerRequestVolumeThreshold(20)
                                    // 错误率到50%开启熔断
                                    .withCircuitBreakerErrorThresholdPercentage(50)
                                    // 熔断中断5秒后放通部分请求
                                    .withCircuitBreakerSleepWindowInMilliseconds(5000)
                                    // 取消Hystrix默认超时时间
                                    .withExecutionTimeoutEnabled(false))
                            // 设置线程核心数
                            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(DEFAULT_THREAD_POOL_SIZE))
            );
            this.invoker = invoker;
            this.invocation = invocation;
        }

        @Override
        protected Result run() {
            return invoker.invoke(invocation);
        }
    }
}
