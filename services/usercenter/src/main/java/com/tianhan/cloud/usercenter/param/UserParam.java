package com.tianhan.cloud.usercenter.param;

import lombok.Data;

/**
 * @Author NieAnTai
 * @Date 2021/11/26 5:20 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Data
public class UserParam {
    private String username;
    /**
     * 前端MD5加密
     */
    private String password;
    private Integer adminFlag;
}
