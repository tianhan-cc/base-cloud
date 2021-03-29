package com.tianhan.cloud.common.core;

/**
 * @Author NieAnTai
 * @Date 2021/3/28 9:42 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description 项目全局常量
 **/
public final class SystemConstant {
    public static String LOGIN_ERROR_MSG = "帐号或密码错误";
    public static String LOGIN_OK_MSG = "登录成功";
    public static String LOGIN_REQUIRE_MSG = "未授权,请先登录";
    public static String LOGIN_TIMEOUT_MSG = "会话已过期,请重新登录";
    public static String PERMISSION_ERROR_MSG = "您没有权限，请联系管理员授权";
    public static String REQUEST_ERROR_MSG = "请求参数格式错误";
    public static String DUPLICATE_KEY_ERROR = "系统已经存在该记录";
    /**
     * 登录来源
     */
    public static String TOKEN_PLATFORM = "PLATFORM";
    public static String LOGIN_SOURCE = "PC";
    public static String APP_LOGIN_SOURCE = "APP";
    public static String TOKEN_KEY = "access-token";
    public static String APP_TOKEN_KEY = "app-access-token";
    /**
     * redis token 存储 KEY 前缀
     */
    public static String STORAGE_TOKEN_KEY = "login:admin:token";
    public static String APP_STORAGE_TOKEN_KEY = "login:app:token";

    /**
     * redis token 用户信息存储
     */
    public static String USER_KEY = "login:admin:user";
    public static String APP_USER_KEY = "login:app:user";

    public static String PASSWORDENCODERTYPE = "{bcrypt}";

    /**
     * 账号最大同时登录数,默认为1
     */
    public static Integer ACCOUNT_LOGIN_MAX = 1;

    public static String SALT = "?base4cloud~";// 默认盐值

    public static String DEFAULT_PWD = "Gmcc@10086";
}
