package com.web.core.interceptors.mybatis;

import com.web.core.interceptors.MybatisPageInterceptor;
import com.web.core.util.page.PageBounds;

/**
 * Created by tians on 2016/6/18.
 */
public class OraclePageInterceptor extends MybatisPageInterceptor {
    protected String getSelectTotalSql(String targetSql) {

        String sql = targetSql.toLowerCase();
        StringBuilder sqlBuilder = new StringBuilder(sql);
        int orderByPos = 0;
        if((orderByPos = sqlBuilder.lastIndexOf(ORDER_BY)) != -1) {
            sqlBuilder.delete(orderByPos, sqlBuilder.length());
        }
        sqlBuilder.insert(0, "select count(1) as _count from ( ").append(" )");

        return sqlBuilder.toString();
    }

    protected String getSelectPagingSql(String targetSql, PageBounds pageBounds) {
        String sql = targetSql.toLowerCase();
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.insert(0, "select * from ( select table_alias.*, rownum mybatis_rowNo from (");
        sqlBuilder.append(") ");
        sqlBuilder.append("table_alias where rownum <= " + pageBounds.getSelectCount()).append(")");
        sqlBuilder.append("where mybatis_rowNo >= " + (pageBounds.getOffset() + 1));
        return sqlBuilder.toString();
    }
}
