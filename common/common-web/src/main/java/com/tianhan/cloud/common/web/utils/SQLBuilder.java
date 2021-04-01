package com.tianhan.cloud.common.web.utils;

import com.tianhan.cloud.common.core.exceptions.BusinessException;
import com.tianhan.cloud.common.core.utils.CommonUtils;
import com.tianhan.cloud.common.core.utils.SpringBeanUtils;
import com.tianhan.cloud.common.web.pager.Pager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
@DependsOn("springBeanUtils")
@ConditionalOnBean(type = "javax.sql.DataSource")
public class SQLBuilder {
	
	protected  Logger logger = LoggerFactory.getLogger(getClass());
	
	static int type = 1;
	
	public static String db_schema;
	
	@PostConstruct
	public void init(){
		String typeName = null;
		String version = null;
		try{
			DataSource ds = SpringBeanUtils.getBean(DataSource.class);
			typeName = CnvSmallChr(ds.getConnection().getMetaData().getDatabaseProductName());
			version = CnvSmallChr(ds.getConnection().getMetaData().getDatabaseProductVersion());
			db_schema = ds.getConnection().getCatalog();
			if ("mysql".equals(typeName)) {
				type = 1;
		    }else if ("oracle".equals(typeName)) {
		    	type = 2;
		    }else if (("sqlserver".equals(typeName)) || (typeName.contains("microsoft"))) {
		    	type = 3;
		    }else{
		    	throw new BusinessException("不支持数据库类型" + typeName);
		    }
		}catch(Exception e){
			logger.error("获取数据库类型异常",e);
		}
		logger.info("数据库类型: {}  版本信息:{}",typeName,version);
	}
	
	private static boolean isBigChr(char chr)
	  {
	    return ('@' < chr) && (chr < '[');
	  }
	
	private static String CnvSmallChr(String str)
	  {
	    char[] chrArry = str.toCharArray();
	    for (int i = 0; i < chrArry.length; i++) {
	      if (isBigChr(chrArry[i]))
	      {
	        int tmp24_23 = i; char[] tmp24_22 = chrArry;tmp24_22[tmp24_23] = ((char)(tmp24_22[tmp24_23] + ' '));
	      }
	    }
	    return new String(chrArry);
	  }
	
	public static String buildPagerSql(String sql, Pager pager){
		if(type==1){
			return  buildMysqlPagerSql(sql, pager);
		}
		if(type==2){
			return  buildOraclePagerSql(sql, pager);
		}
		if(type==3){
			return  buildSqlServerPagerSql(sql, pager);
		}
		//默认
		return buildMysqlPagerSql(sql, pager);
	}
	
	
	/**
	 * sqlserver
	 * @param sql
	 * @param pager
	 * @return
	 */
	private static String buildSqlServerPagerSql(String sql,Pager pager){
		StringBuilder pagingSelect = new StringBuilder(300);
	    sql = sql.replaceFirst("^\\s*[sS][eE][lL][eE][cC][tT]\\s+", "select top " + (pager.getStartRow() + pager.getPageSize()) + " ");
	    pagingSelect.append(" select * from ( select row_number()over(order by __tc__)tempRowNumber,* from (select    __tc__=0, *  from ( ");
	    pagingSelect.append(" select top 100 percent * from ( ");
	    pagingSelect.append(sql);
	    pagingSelect.append(" )  as _sqlservertb_  ");
	    String sortColumn = pager.getSort();
	    if(StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(pager.getOrder())){
	    	pagingSelect.append(" order by " + CommonUtils.camelCaseToUnderscore(sortColumn)  +" " + pager.getOrder());
	    }
	    pagingSelect.append(" ) t )tt )ttt where tempRowNumber > ").append(pager.getStartRow()).append(" and tempRowNumber <= ").append(pager.getStartRow() + pager.getPageSize());
	    return pagingSelect.toString();
	}
	
	/**
	 * mysql
	 * @param sql
	 * @param pager
	 * @return
	 */
	private static String buildMysqlPagerSql(String sql,Pager pager){
		StringBuilder pagingSelect = new StringBuilder(300);
		pagingSelect.append(" select * from ( ");
	    pagingSelect.append(sql);
	    pagingSelect.append(" ) as _mysqltb_ ");
	    String sortColumn = pager.getSort();
	    if(StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(pager.getOrder())){
	    	pagingSelect.append(" order by " + CommonUtils.camelCaseToUnderscore(sortColumn) +" " + pager.getOrder());
	    }
	    pagingSelect.append(" limit ").append(pager.getStartRow()).append(",").append(pager.getPageSize());
	    return pagingSelect.toString();
	}
	
	/**
	 * oracle
	 * @param sql
	 * @param pager
	 * @return
	 */
	private static String buildOraclePagerSql(String sql,Pager pager){
		StringBuilder pagingSelect = new StringBuilder(300);
	    pagingSelect.append("select * from ( select row_.*, rownum rownum_userforpage from ( ");
	    pagingSelect.append(" select * from ( ");
	    pagingSelect.append(sql);
	    pagingSelect.append(" )  as _oracletb_  ");
	    String sortColumn = pager.getSort();
	    if(StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(pager.getOrder())){
	    	pagingSelect.append(" order by " + CommonUtils.camelCaseToUnderscore(sortColumn)  +" " + pager.getOrder());
	    }
	    pagingSelect.append(" ) row_ where rownum <= ")
	    .append(pager.getStartRow() + pager.getPageSize())
	    .append(") where rownum_userforpage > ")
	    .append(pager.getStartRow());
	    return pagingSelect.toString();
	}

}
