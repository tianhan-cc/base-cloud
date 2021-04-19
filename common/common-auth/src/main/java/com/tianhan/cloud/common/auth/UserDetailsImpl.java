package com.tianhan.cloud.common.auth;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author NieAnTai
 * @Date 2021/3/27 11:35 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
public class UserDetailsImpl implements UserDetails {
    private String id;
    private String username;
    @JSONField(serialize = false)
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
    /**
     * 管理员标识 0: 不是 1: 是
     */
    private Integer adminFlag;
    /**
     * 是否删除 0: 已删除 1: 未删除
     */
    private Integer delFlag;
    /**
     * 状态标识 0: 禁用 1：正常
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUserid;

    private List<String> permissions;

    /**
     * 登录来源[PC|APP]
     */
    private String loginSource;

    public UserDetailsImpl(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    @JSONField(serialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return status == 1;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return delFlag == 1;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return status == 1;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return delFlag == 1;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(String loginSource) {
        this.loginSource = loginSource;
    }
}
