package com.tianhan.cloud.common.web.config;

import com.tianhan.cloud.common.web.dao.IBaseDao;
import com.tianhan.cloud.common.web.dao.impl.BaseDaoImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * @Author NieAnTai
 * @Date 2021/4/16 11:18 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description 简易SqlDao类配置, 由EnableAutoConfiguration管理加载
 **/
@ConditionalOnClass({DataSource.class, JdbcTemplate.class})
@ConditionalOnBean(NamedParameterJdbcTemplate.class)
@AutoConfigureAfter(JdbcTemplateAutoConfiguration.class)
public class SimpleDataConfiguration {
    @Bean
    public IBaseDao baseDao() {
        return new BaseDaoImpl();
    }
}
