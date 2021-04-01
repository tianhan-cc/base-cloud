package com.tianhan.cloud.common.web.dao;


import com.tianhan.cloud.common.web.pager.Pager;

import java.util.List;
import java.util.Map;


public interface IBaseDao {
	
	<T> Pager queryPageForSql(String sql, Object param, Pager pager, Class<T> clazz);

	<T> List<T> queryListForSql(String sql, Object param, Class<T> clazz);

	<T> T querySingleForSql(String sql, Object param, Class<T> clazz);

	<T> Pager queryPageForSql(String sql, Map<String, Object> param, Pager pager, Class<T> clazz);

	<T> List<T> queryListForSql(String sql, Map<String, Object> param, Class<T> clazz);

	<T> T querySingleForSql(String sql, Map<String, Object> param, Class<T> clazz);
}
