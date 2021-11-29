package com.tianhan.cloud.usercenter.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianhan.cloud.usercenter.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author NieAnTai
 * @Date 2021/11/26 4:46 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
}
