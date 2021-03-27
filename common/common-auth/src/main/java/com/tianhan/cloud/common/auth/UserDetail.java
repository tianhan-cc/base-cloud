package com.tianhan.cloud.common.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Date;

/**
 * @Author NieAnTai
 * @Date 2021/3/27 11:35 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
public class UserDetail extends UsernamePasswordAuthenticationToken {
    private String id;
    private String username;
    private String password;
    /**
     * 部门ID
     */
    private String deptId;
    /**
     * 昵称
     */
    private String realname;
    /**
     * 手机号
     */
    private String mobile;
    private String token;
    /**
     * 管理员标识 0: 不是 1: 是
     */
    private Integer adminFlag;
    /**
     * 状态标识 0: 禁用 1：正常
     */
    private Integer status;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUserid;

    public UserDetail(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(Integer adminFlag) {
        this.adminFlag = adminFlag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }
}
