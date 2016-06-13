package com.web.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;

import com.web.core.util.HashList;
import com.web.core.util.page.Page;

/**
 * 
 * 类描述：DAO层泛型基类接口
 * 
 * qgl
 * 
 * @date： 日期：2015-10-10
 * @version 1.0
 */
public interface ICommonDao {

	public SqlSession getSession();

	public Object getMapper(Class entity);
}
