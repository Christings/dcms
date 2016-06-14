package com.web.core.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.web.core.dao.ICommonDao;
import com.web.entity.User;
import com.web.mappers.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.web.core.exception.BusinessException;
import com.web.core.util.HashList;
import com.web.core.util.page.Page;

/**
 *
 * 类描述： DAO层泛型基类
 *
 * qgl
 *
 * @date： 日期：2014-06-01 时间：上午10:16:48
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
     * 注入一个sessionFactory属性,并注入到父类(HibernateDaoSupport)
     * **/
    @Autowired
    @Qualifier("sessionFactory")
    private SqlSessionFactory sessionFactory;

    public SqlSession getSession() {
        return sessionFactory.openSession();
    }

    public Object getMapper(Class entity){
        Object mapper = getSession().getMapper(entity);
        return mapper;
    }
}
