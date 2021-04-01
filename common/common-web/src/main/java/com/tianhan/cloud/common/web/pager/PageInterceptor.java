package com.tianhan.cloud.common.web.pager;


import com.tianhan.cloud.common.core.utils.ReflectUtil;
import com.tianhan.cloud.common.web.utils.SQLBuilder;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

/**
 * @Author: NieAnTai
 * @Description: Mybatis 分页查询插件
 * @Date: 10:20 2019/4/17
 */
@Component
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class}),
        @Signature(method = "query", type = StatementHandler.class, args = {Statement.class, ResultHandler.class})
})
public class PageInterceptor implements Interceptor {

    ThreadLocal<Pager> tl = new ThreadLocal<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        if (invocation.getTarget() instanceof StatementHandler && "prepare".equals(invocation.getMethod().getName())) {
            RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
            StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
            BoundSql boundSql = delegate.getBoundSql();
            Object object = boundSql.getParameterObject();

            Pager page = null;

            if (object instanceof Pager) {
                page = (Pager) object;
            } else if (object instanceof HashMap) {
                HashMap<?, ?> map = (HashMap<?, ?>) object;
                Optional<?> optional = map.values().stream().filter(obj -> obj instanceof Pager).findFirst();
                page = (Pager) optional.orElse(null);
            }

            if (page != null) {
                MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
                Connection connection = (Connection) invocation.getArgs()[0];
                String sql = boundSql.getSql();
                if (!page.getIgnoreCount()) this.setTotalRows(page, object, mappedStatement, connection);
                String pageSql = SQLBuilder.buildPagerSql(sql, page);
                ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
                tl.set(page);
            }
        }


        if (invocation.getTarget() instanceof StatementHandler && "query".equals(invocation.getMethod().getName())) {
            Pager page = tl.get();
            if (page != null) {
                tl.remove();
                Object obj = invocation.proceed();
                page.setPageData((Collection<?>) obj);
            }
        }


        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    /**
     * @param page
     * @param statement
     * @param connection
     */
    private void setTotalRows(Pager page, Object parameterObject, MappedStatement statement, Connection connection) {
        BoundSql boundSql = statement.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        String countSql = String.format("select count(1) from (%s) as xsx", sql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(statement.getConfiguration(), countSql, parameterMappings, page);
        ParameterHandler parameterHandler = new DefaultParameterHandler(statement, parameterObject, countBoundSql);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                long currentPage = page.getCurrentPage();
                long totalRows = rs.getLong(1);
                page.setTotalRows(totalRows);
                page.setCurrentPage(currentPage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
