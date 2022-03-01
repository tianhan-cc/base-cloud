package com.tianhan.cloud.usercenter.param;

import lombok.Data;

/**
 * @Author NieAnTai
 * @Date 2021/11/26 4:49 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Data
public class LoginParam {
    private String username;
    private String password;
    private String sourceId;
}
