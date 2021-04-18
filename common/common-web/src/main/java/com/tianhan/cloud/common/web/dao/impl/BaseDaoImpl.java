package com.tianhan.cloud.common.web.dao.impl;

import com.alibaba.fastjson.JSON;
import com.tianhan.cloud.common.core.utils.CommonUtils;
import com.tianhan.cloud.common.web.dao.IBaseDao;
import com.tianhan.cloud.common.web.pager.Pager;
import com.tianhan.cloud.common.web.utils.SQLBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Slf4j
public class BaseDaoImpl implements IBaseDao {

    private <T> RowMapper<T> getRowMapper(Class<T> clazz) {
        if (CommonUtils.isWrapClass(clazz)) {
            return new SingleColumnRowMapper<T>(clazz);
        }
        return new BeanPropertyRowMapper<T>(clazz);
    }

    @Resource
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public <T> List<T> queryListForSql(String sql, Object param, Class<T> clazz) {
        SqlParameterSource sps = null;
        if (param == null) {
            sps = new EmptySqlParameterSource();
        }
        log.debug(String.format("生成SQL:%s 填充值:%s", sql.toString(), JSON.toJSONString(param)));
        return namedParameterJdbcTemplate.query(sql, sps, getRowMapper(clazz));
    }

    @Override
    public <T> Pager queryPageForSql(String sql, Object param, Pager pager, Class<T> clazz) {
        SqlParameterSource sps = null;
        if (param == null) {
            sps = new EmptySqlParameterSource();
        }
        String pagerSql = SQLBuilder.buildPagerSql(sql, pager);
        pager.setPageData(this.queryListForSql(pagerSql, param, clazz));
        if (!pager.getIgnoreCount()) {
            String countSql = "select count(*) from ( " + sql + " ) sqlbuildtb_";
            pager.setTotalRows(namedParameterJdbcTemplate.queryForObject(countSql, sps, new SingleColumnRowMapper<Long>(Long.class)));
        }
        return pager;
    }

    @Override
    public <T> T querySingleForSql(String sql, Object param, Class<T> clazz) {
        List<T> list = this.queryListForSql(sql, param, clazz);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public <T> Pager queryPageForSql(String sql, Map<String, Object> param, Pager pager, Class<T> clazz) {
        SqlParameterSource sps = (param == null || param.isEmpty()) ? new EmptySqlParameterSource() : new MapSqlParameterSource(param);
        String pagerSql = SQLBuilder.buildPagerSql(sql, pager);
        log.debug(String.format("生成SQL:%s 填充值:%s", pagerSql, JSON.toJSONString(param)));
        pager.setPageData(this.queryListForSql(pagerSql, param, clazz));
        if (!pager.getIgnoreCount()) {
            String countSql = "select count(*) from ( " + sql + " ) sqlbuildtb_";
            pager.setTotalRows(namedParameterJdbcTemplate.queryForObject(countSql, sps, new SingleColumnRowMapper<Long>(Long.class)));
        }
        return pager;
    }

    @Override
    public <T> List<T> queryListForSql(String sql, Map<String, Object> param, Class<T> clazz) {
        SqlParameterSource sps = (param == null || param.isEmpty()) ? new EmptySqlParameterSource() : new MapSqlParameterSource(param);
        return namedParameterJdbcTemplate.query(sql, sps, getRowMapper(clazz));
    }


    @Override
    public <T> T querySingleForSql(String sql, Map<String, Object> param, Class<T> clazz) {
        List<T> list = this.queryListForSql(sql, param, clazz);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
