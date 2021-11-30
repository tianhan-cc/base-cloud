package com.tianhan.cloud.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tianhan.cloud.common.auth.utils.SecurityUserUtil;
import com.tianhan.cloud.common.core.utils.IdGen;
import com.tianhan.cloud.usercenter.dao.UserDao;
import com.tianhan.cloud.usercenter.entity.UserEntity;
import com.tianhan.cloud.usercenter.param.UserParam;
import com.tianhan.cloud.usercenter.service.IUserService;
import com.tianhan.cloud.usercenter.vo.UserDetailVO;
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
    public UserDetailVO info(String username) {
        return baseMapper.userinfo(username);
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
