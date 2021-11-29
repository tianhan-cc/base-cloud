package com.tianhan.cloud.usercenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author NieAnTai
 * @Date 2021/3/31 2:23 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@EqualsAndHashCode(callSuper = true)
@TableName("user")
@Data
@Accessors(chain = true)
public class UserEntity extends Model<UserEntity> {
    @TableId(type = IdType.INPUT)
    private String id;
    private String deptId;
    private String username;
    private String password;
    private Integer status;
    private Integer adminFlag;
    private Integer delFlag;
    private Date loginTime;
    private String loginIp;
    private Date createTime;
    private String createUser;
}
