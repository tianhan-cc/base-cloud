package com.tianhan.cloud.usercenter.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author NieAnTai
 * @Date 2021/11/26 4:47 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Data
public class UserDetailVO {
    private String username;
    @JSONField(serialize = false)
    private String password;
    private String deptId;
    private String deptName;
    private String status;
    private String adminFlag;
    private String delFlag;
    private Date loginTime;
    private String loginIp;
    private List<String> permissions;
}
