package com.web.core.interceptors;

import com.web.core.util.ContextHolderUtils;
import com.web.core.util.PluginUtil;
import com.web.util.WebUtils;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Insert和Update拦截器(设置公共值)
 *
 * @author 杜延雷
 * @date 2016-07-14
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class AutoColumnInterceptor implements Interceptor {
	
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("-yyyy-MM-dd HH:mm:ss.SSS-");
    
	/** 更新人 表字段名*/
	public static String updateName;
	/** 更新时间 表字段名*/
	public static String updateDate;
	/** 创建人 表字段名*/
	public static String createName;
	/** 创建时间  表字段名 */
	public static String createDate;
	/** 要忽略拦截表名称集合 */
	public static String[] ignoreTables;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		String interceptMethod = invocation.getMethod().getName();
		if(!"prepare".equals(interceptMethod)) {
			return invocation.proceed();
		}
		
		StatementHandler handler = (StatementHandler) PluginUtil.processTarget(invocation.getTarget());
		MetaObject metaObject = SystemMetaObject.forObject(handler);
		MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		SqlCommandType sqlCmdType = ms.getSqlCommandType();
		if(sqlCmdType != SqlCommandType.UPDATE && sqlCmdType != SqlCommandType.INSERT) {
			return invocation.proceed();
		}

		//获取原始sql
		String originalSql = (String) metaObject.getValue("delegate.boundSql.sql");

		//获取配置参数
		if(isConfig()) {
			return invocation.proceed();
		}

		//追加上日期
		String newSql = "";
		Statement stmt = CCJSqlParserUtil.parse(originalSql);

		if(sqlCmdType == SqlCommandType.UPDATE){
			if(!(stmt instanceof Update)){
				return originalSql;
			}
			Update update = (Update)stmt;
			if(!ingoreTab(update.getTables())){
				newSql = addUpdateStringToSql(stmt,originalSql, updateName, WebUtils.getUser(ContextHolderUtils.getRequest()).getUsername());
				newSql = addUpdateDateToSql(stmt,newSql, updateDate);
			}
		}else if(sqlCmdType == SqlCommandType.INSERT){
			if(!(stmt instanceof Insert)){
				return originalSql;
			}
			Insert insert = (Insert)stmt;
			if(!ingoreTab(insert.getTable().getName())){
				newSql = addCreateStringToSql(stmt,originalSql, createName, WebUtils.getUser(ContextHolderUtils.getRequest()).getUsername());
				newSql = addCreateDateToSql(stmt,newSql, createDate);
			}
		}
		//设置新sql
		if(newSql.length() > 0){
			metaObject.setValue("delegate.boundSql.sql", newSql);
		}
	    return invocation.proceed();
	}

	private String addCreateStringToSql(Statement stmt,String originalSql, String columnName,String columnValue){
		try{
			Insert insert = (Insert)stmt;
			List<Column> columns = insert.getColumns();
			ItemsList itemList = insert.getItemsList();

			int index = contains(columns, columnName);
			if(-1 == index){
				Column versionColumn = new Column();
				versionColumn.setColumnName(columnName);
				columns.add(versionColumn);
			}

			if(itemList instanceof ExpressionList){//单个
				ExpressionList expressionList = (ExpressionList)itemList;
				List<Expression> expressions = expressionList.getExpressions();
				if(-1 == index) {
					StringValue val = new StringValue("-"+columnValue+"-");
					expressions.add(val);
				}else{
					expressions.set(index,new StringValue("-"+columnValue+"-"));
				}
			}else if(itemList instanceof MultiExpressionList){//批量
				MultiExpressionList multiExpressionList = (MultiExpressionList)itemList;
				List<ExpressionList> expressionLists = multiExpressionList.getExprList();
				for(ExpressionList expressionList : expressionLists){
					List<Expression> expressions = expressionList.getExpressions();
					if(-1 != index){
						StringValue val = new StringValue("-"+columnValue+"-");
						expressions.add(val);
					}else{
						expressions.set(index,new StringValue("-"+columnValue+"-"));
					}
				}
			}else{//insert select
				columns.remove(columns.size() - 1);
			}

			return stmt.toString();
		}catch(Exception e){
			e.printStackTrace();
			return originalSql;
		}
	}

	private String addCreateDateToSql(Statement stmt,String originalSql, String columnName){
		try{
			Insert insert = (Insert)stmt;
			List<Column> columns = insert.getColumns();
			ItemsList itemList = insert.getItemsList();

			int index = contains(columns, columnName);
			if(-1 == index){
				Column versionColumn = new Column();
				versionColumn.setColumnName(columnName);
				columns.add(versionColumn);
			}

			if(itemList instanceof ExpressionList){//单个
				ExpressionList expressionList = (ExpressionList)itemList;
				List<Expression> expressions = expressionList.getExpressions();
				if(-1 == index) {
					TimestampValue val = new TimestampValue(TIMESTAMP_FORMAT.format(new Date()));
					expressions.add(val);
				}else{
					expressions.set(index,new TimestampValue(TIMESTAMP_FORMAT.format(new Date())));
				}
			}else if(itemList instanceof MultiExpressionList){//批量
				MultiExpressionList multiExpressionList = (MultiExpressionList)itemList;
				List<ExpressionList> expressionLists = multiExpressionList.getExprList();
				for(ExpressionList expressionList : expressionLists){
					List<Expression> expressions = expressionList.getExpressions();
					if(-1 != index){
						TimestampValue val = new TimestampValue(TIMESTAMP_FORMAT.format(new Date()));
						expressions.add(val);
					}else{
						expressions.set(index,new TimestampValue(TIMESTAMP_FORMAT.format(new Date())));
					}
				}
			}else{//insert select
				columns.remove(columns.size() - 1);
			}

			return stmt.toString();
		}catch(Exception e){
			e.printStackTrace();
			return originalSql;
		}
	}

	private String addUpdateStringToSql(Statement stmt,String originalSql, String columnName,String columnValue){
		StringBuilder sb = new StringBuilder();
		String sqlList[] = originalSql.split(";");
		for(int i=0; i<sqlList.length; i++){
			if(i > 0 ){
				sb.append(";");
			}

			String sql = _addUpdateStringToSql(stmt,sqlList[i], columnName, columnValue);
			sb.append(sql);
		}
		return sb.toString();
	}
	private String _addUpdateStringToSql(Statement stmt,String originalSql, String columnName,String columnValue){
		try{
			Update update = (Update)stmt;
			List<Column> columns = update.getColumns();
			List<Expression> expressions = update.getExpressions();
			int index = contains(columns, columnName);
			if(-1 == index){
				Column versionColumn = new Column();
				versionColumn.setColumnName(columnName);
				columns.add(versionColumn);
				StringValue val = new StringValue("-"+columnValue+"-");
				expressions.add(val);
			}else{
				expressions.set(index,new StringValue("-"+columnValue+"-"));
			}
			return stmt.toString();
		}catch(Exception e){
			e.printStackTrace();
			return originalSql;
		}
	}

	private String addUpdateDateToSql(Statement stmt,String originalSql, String columnName){
		StringBuilder sb = new StringBuilder();
		String sqlList[] = originalSql.split(";");
		for(int i=0; i<sqlList.length; i++){
			if(i > 0 ){
				sb.append(";");
			}

			String sql = _addUpdateDateToSql(stmt,sqlList[i], columnName);
			sb.append(sql);
		}
		return sb.toString();
	}
	private String _addUpdateDateToSql(Statement stmt,String originalSql, String columnName){
		try{
			Update update = (Update)stmt;
			List<Column> columns = update.getColumns();
			List<Expression> expressions = update.getExpressions();
			int index = contains(columns, columnName);
			if(-1 == index){
				Column versionColumn = new Column();
				versionColumn.setColumnName(columnName);
				columns.add(versionColumn);
				TimestampValue val = new TimestampValue(TIMESTAMP_FORMAT.format(new Date()));
				expressions.add(val);
			}else{
				expressions.set(index,new TimestampValue(TIMESTAMP_FORMAT.format(new Date())));
			}
			return stmt.toString();
		}catch(Exception e){
			e.printStackTrace();
			return originalSql;
		}
	}
	
	private int contains(List<Column> columns, String columnName){
		if(columns == null || columns.size() <= 0){
			return -1;
		}
		if(columnName == null || columnName.length() <= 0){
			return -1;
		}
		for(int i=0;i<columns.size();i++){
			if(columns.get(i).getColumnName().equalsIgnoreCase(columnName)){
				return i;
			}
		}
		return -1;
	}

	private boolean isConfig(){
		if(StringUtils.isEmpty(updateName) || StringUtils.isEmpty(updateDate)
				|| StringUtils.isEmpty(createName) || StringUtils.isEmpty(createDate)){
			return true;
		}
		return false;
	}

	private boolean ingoreTab(String name){
		if(null != ignoreTables && ignoreTables.length>0) {
			for (String tableName : ignoreTables) {
				if (name.equals(tableName)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean ingoreTab(List<Table> tables){
		if(null != ignoreTables && ignoreTables.length>0){
			for(Table table:tables){
				for(String tableName:ignoreTables){
					if(table.getName().equals(tableName)){
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
	}

	@Override
	public void setProperties(Properties properties) {
//		if(null != properties && !properties.isEmpty()) props = properties;

		if(null != properties && !properties.isEmpty()) {
			updateName = properties.getProperty("updateName", "");
			updateDate = properties.getProperty("updateDate", "");
			createName = properties.getProperty("createName", "");
			createDate = properties.getProperty("createDate", "");
			ignoreTables = properties.getProperty("ignoreTables", "").split(",");
		}
	}
}
