package com.web.core.dao.impl;

import com.web.core.dao.ICommonDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 *
 * 类描述： DAO层泛型基类
 *
 * qgl
 *
 * @date： 日期：2014-06-01 时间：上午10:16:48
 * 
 * @param <T>
 * @param <PK>
 * @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unused", "hiding", "unchecked" })
@Repository("commonDao")
@Transactional
public class CommonDao<T, PK extends Serializable> implements ICommonDao {
	/**
	 * 初始化Log4j的一个实例
	 */
	private static final Logger logger = Logger.getLogger(CommonDao.class);

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 注入一个sessionFactory属性,并注入到父类
	 **/
	@Autowired
	@Qualifier("sessionFactory")
	private SqlSessionFactory sessionFactory;

	public SqlSession getSession() {
		return sessionFactory.openSession();
	}

	public Object getMapper(Class entity) {
		Object mapper = getSession().getMapper(entity);
		return mapper;
	}
}
