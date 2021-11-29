package com.tianhan.cloud.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tianhan.cloud.common.auth.UserDetailsUpgrade;
import com.tianhan.cloud.common.auth.utils.SecurityUserUtil;
import com.tianhan.cloud.common.core.exceptions.BusinessException;
import com.tianhan.cloud.common.core.utils.IdGen;
import com.tianhan.cloud.usercenter.dao.UserDao;
import com.tianhan.cloud.usercenter.entity.UserEntity;
import com.tianhan.cloud.usercenter.param.LoginParam;
import com.tianhan.cloud.usercenter.param.UserParam;
import com.tianhan.cloud.usercenter.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author NieAnTai
 * @Date 2021/11/26 4:00 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements IUserService {
    private static final BCryptPasswordEncoder BC = new BCryptPasswordEncoder();

    @Override
    public UserDetailsUpgrade login(LoginParam login) {
        UserEntity user = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", login.getUsername()));
        if (BC.matches(login.getPassword(), user.getPassword())) {
            // 记录登录来源IP
            user.setLoginIp(login.getSourceId()).setLoginTime(new Date()).updateById();
            UserDetailsUpgrade vo = new UserDetailsUpgrade(user.getUsername(), "");
            BeanUtils.copyProperties(user, vo);
            log.info("用户 {} 于 {} 登录系统,访问ip {}", user.getUsername(), user.getLoginTime(), user.getLoginIp());
            return vo;
        }
        throw new BusinessException("请输入正确的用户名或密码!");
    }

    @Override
    public void createUser(UserParam user) {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(user, entity);

        entity.setId(IdGen.nextIdStr())
                .setPassword(BC.encode(user.getPassword()))
                .setCreateUser(SecurityUserUtil.obtainUserDetail().getCreateUserid())
                .setCreateTime(new Date()).insert();

        log.info("添加用户 {}", entity.getUsername());
    }
}
