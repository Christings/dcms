package com.web.core.interceptors;

import com.web.core.util.page.PageBounds;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.omg.CORBA.Bounds;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tians on 2016/6/18.
 * 拦截器分页
 * 为了可以兼容多个数据库，所以写成抽象类。
 * 在spring配置中，根据不同的数据库配置不同的的子类
 */
public abstract class MybatisPageInterceptor implements Interceptor {

    private static final String FIELD_DELEGATE = "delegate";
    private static final String FIELD_ROWBOUNDS = "rowBounds";
    private static final String FIELD_MAPPEDSTATEMENT = "mappedStatement";
    private static final Pattern PATTERN_SQL_BLANK = Pattern.compile("\\s+");
    private static final String BLANK = " ";
    private static final String FIELD_SQL = "sql";
    public static final String ORDER_BY = "order by";
    public static final String UNION = "union";
    public static final  String FROM = "from";
    public static  final  String SELECT = "select";

    public Object intercept(Invocation invocation) throws Throwable {
        Connection connection = (Connection)invocation.getArgs()[0];
        RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation.getTarget();

        StatementHandler handler = (StatementHandler)readFile(statementHandler,FIELD_DELEGATE);
        PageBounds pageBounds = (PageBounds)readFile(handler,FIELD_ROWBOUNDS);
        MappedStatement mappedStatement = (MappedStatement)readFile(handler,FIELD_MAPPEDSTATEMENT);
        BoundSql boundSql = handler.getBoundSql();
        String targetSql = replaceSqlBlank(boundSql.getSql());
        getTotalAndSetInPagingBounds(targetSql,boundSql,pageBounds, mappedStatement,connection);
        String pageSql = getSelectPagingSql(targetSql, pageBounds);
        writeDeclaredField(boundSql, FIELD_SQL, pageSql);
        pageBounds.setMeToDefault();
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        if(target instanceof  RoutingStatementHandler){
            try{
                Field delegate = getField(RoutingStatementHandler.class, FIELD_DELEGATE);
                StatementHandler handler = (StatementHandler)delegate.get(target);
                RowBounds rowBounds = (RowBounds)readFile(handler, FIELD_ROWBOUNDS);
                if(rowBounds != RowBounds.DEFAULT && rowBounds instanceof PageBounds){
                    return Plugin.wrap(target, this);
                }
            }catch (IllegalAccessException e){

            }
        }
        return target;
    }

    public void setProperties(Properties properties) {

    }

    private void getTotalAndSetInPagingBounds(String targetSql, BoundSql boundSql, PageBounds pageBounds, MappedStatement mappedStatement,Connection connection) throws SQLException{
        String totalSql = getSelectTotalSql(targetSql);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        BoundSql totalBoundSql = new BoundSql(mappedStatement.getConfiguration(),totalSql,parameterMappingList,parameterObject);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject,totalBoundSql);

        PreparedStatement pstatement = null;
        ResultSet rs = null;
        try{
            pstatement = connection.prepareStatement(totalSql);
            parameterHandler.setParameters(pstatement);
            rs = pstatement.executeQuery();
            if(rs.next()){
                int totalRecord = rs.getInt(1);
                pageBounds.setTotal(totalRecord);
            }
        }finally {
            if (null != rs){
                rs.close();
            }
            if(null != pstatement){
                pstatement.close();
            }
        }
    }

    /**
     * @param targetSql 拦截查询的所有SQL，处理成select count(1) from ,获取集合总数
     * */
    protected abstract String getSelectTotalSql(String targetSql);

    /**
     * @param targetSql
     *                  拦截查询的所有SQL
     * @param pageBounds
     *                  分页参数载体
     * */
    protected abstract String getSelectPagingSql(String targetSql, PageBounds pageBounds);

    private void writeDeclaredField(Object target, String fieldName, Object value) throws IllegalAccessException {
        if(null ==target){
            throw new IllegalArgumentException("target object must not be null");
        }
        Class<?> cls = target.getClass();
        Field field = getField(cls, fieldName);
        if(null == field){
            throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
        }
        field.set(target,value);
    }

    private Object readFile(Object target, String fieldName) throws IllegalAccessException{
        if(null == target){
            throw new IllegalArgumentException("target object must not be null");
        }
        Class<?> cls = target.getClass();
        Field field = getField(cls, fieldName);
        if(null == field){
            throw new IllegalArgumentException("Cannot locate field " + fieldName + " on " + cls);
        }
        if(!field.isAccessible()){
            field.setAccessible(true);
        }
        return field.get(target);
    }

    private  static  Field getField(final Class<?> clss, String fieldName){
        for (Class<?> aclss = clss; null != aclss;aclss = aclss.getSuperclass()){
            try{
                Field field = aclss.getDeclaredField(fieldName);
                if(!Modifier.isPublic(field.getModifiers())){
                    field.setAccessible(true);
                    return field;
                }
            }catch (NoSuchFieldException ex){

            }
        }
        return null;
    }

    private String replaceSqlBlank(String originalSql) {
        Matcher matcher = PATTERN_SQL_BLANK.matcher(originalSql);
        return matcher.replaceAll(BLANK);
    }
}
