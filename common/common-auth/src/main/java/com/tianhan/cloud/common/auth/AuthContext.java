package com.tianhan.cloud.common.auth;

/**
 * @Author NieAnTai
 * @Date 2021/4/18 9:28 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description AuthContext 上下文存储 用户信息
 **/
public abstract class AuthContext {
    private static final ThreadLocal<SimpleTokenInfo> LOCAL_SIMPLE_USER_INFO = new ThreadLocal<>();

    public static void setJwtAttributesHolder(SimpleTokenInfo user) {
        LOCAL_SIMPLE_USER_INFO.set(user);
    }

    public static void setJwtAttributesHolder(String token) {
        LOCAL_SIMPLE_USER_INFO.set(new SimpleTokenInfo(token));
    }

    public static SimpleTokenInfo getJwtAttributesHolder() {
        return LOCAL_SIMPLE_USER_INFO.get();
    }

    public static void remove() {
        LOCAL_SIMPLE_USER_INFO.remove();
    }
}
