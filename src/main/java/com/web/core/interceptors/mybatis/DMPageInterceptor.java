package com.web.core.interceptors.mybatis;

import com.web.core.interceptors.MybatisPageInterceptor;
import com.web.core.util.page.PageBounds;

/**
 * Created by tians on 2016/6/18.
 * 达梦数据库SQL处理
 */
public class DMPageInterceptor extends MybatisPageInterceptor{
    protected String getSelectTotalSql(String targetSql) {
        String sql = targetSql.toLowerCase();
        StringBuilder sqlBuilder = new StringBuilder(sql);
        int orderByPos = 0;
        if((orderByPos = sqlBuilder.lastIndexOf(ORDER_BY)) != -1){
            sqlBuilder.delete(orderByPos, sqlBuilder.length());
        }
        int fromPos = sqlBuilder.indexOf(FROM);
        if(fromPos != -1){
            sqlBuilder.delete(0,fromPos);
            sqlBuilder.insert(0, "select count(1) as _count ");
        }
        return sqlBuilder.toString();
    }

    protected String getSelectPagingSql(String targetSql, PageBounds pageBounds) {
        String sql = targetSql.toLowerCase();
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.insert(0, "select top " + pageBounds.getSelectCount() + "," + pageBounds.getOffset() + 1 + " *  from (");
        sqlBuilder.append(") ");
        sqlBuilder.append("table_alias");
        return sqlBuilder.toString();
    }
}
