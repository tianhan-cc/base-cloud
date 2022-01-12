package com.tianhan.cloud.usercenter.rpc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianhan.cloud.common.auth.UserDetail;
import com.tianhan.cloud.common.auth.utils.SecurityUserUtil;
import com.tianhan.cloud.usercenter.dao.UserDao;
import com.tianhan.cloud.usercenter.entity.UserEntity;
import com.tianhan.cloud.usercenter.rpc.interfaces.IUsercenterRpc;
import com.tianhan.cloud.usercenter.vo.UserDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author NieAnTai
 * @Date 2021/3/15 3:00 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@DubboService
@Slf4j
public class UserRpcProvider implements IUsercenterRpc {
    @Resource
    private UserDao userDao;

    @Override
    public UserDetail obtainUser(String username) {
        UserDetailVO user = userDao.userinfo(username);
        UserDetail result = new UserDetail(user.getUsername(), user.getPassword());
        BeanUtils.copyProperties(user, result);
        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void loginRecord(String username, String loginIp, String loginSource) {
        UserEntity user = userDao.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
        if (user == null) {
            return;
        }
        user.setLoginTime(new Date()).setLoginIp(loginIp).updateById();
    }
}
