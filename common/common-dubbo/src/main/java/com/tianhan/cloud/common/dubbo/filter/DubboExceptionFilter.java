package com.tianhan.cloud.common.dubbo.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.filter.ExceptionFilter;

/**
 * @Author NieAnTai
 * @Date 2021/3/15 3:11 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description 自定义Dubbo异常拦截
 **/
@Activate(group = CommonConstants.PROVIDER)
public class DubboExceptionFilter extends ExceptionFilter {
}
