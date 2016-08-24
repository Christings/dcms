package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.User;
import com.web.example.UserExample;
import com.web.mappers.UserMapper;
import com.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户 逻辑接口 实现
 *
 * @author 杜延雷
 * @date 2016-08-09
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserMapper userMapper;

	@Override
	public int save(User entity) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("save user: {}", JSON.toJSONString(entity));
		}

		if (null == entity) {
			LOGGER.warn("the user object is null.");
			return 0 ;
		}

		// 插入记录
		int result = userMapper.insertSelective(entity) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("save user object result: {}", result);
		}

		return result;
	}

	@Override
	public int updateById(User entity) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("update User: {}", JSON.toJSONString(entity));
		}

		if (null == entity) {
			LOGGER.warn("the user object is null.");
			return 0 ;
		}

		// 更新记录
		int result = userMapper.updateByPrimaryKeySelective(entity) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("update user object result: {}", result);
		}

		return result;
	}

	@Override
	public int deleteById(String key) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("delete key by id: {}", key);
		}

		if (StringUtils.isEmpty(key)) {
			LOGGER.warn("the key id object is null.");
			return 0 ;
		}

		// 删除记录数
		int result = userMapper.deleteByPrimaryKey(key) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("delete user object by id result: {}", result);
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public User getById(String key) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("find user by id: {}", key);
		}

		if (StringUtils.isEmpty(key)) {
			LOGGER.warn("the user id object is null.");
			return null ;
		}

		// 查找实体对象
		User user = userMapper.selectByPrimaryKey(key) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("find muen object by id result: {}", JSON.toJSONString(user));
		}

		return user;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public List<User> getAll() {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get all user");
		}

		//查询所有用户
		UserExample example = new UserExample();
		//排序
		example.setOrderByClause("create_date desc,id asc");
		List<User> users = userMapper.selectByExample(example);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get user all object count: {}", users.size());
		}

		return users;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public int count(UserExample example) {
		return userMapper.countByExample(example);
	}

	/**
	 * 根据登录名（查找用户）
	 *
	 * @param username
	 * @return
     */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public User getUserByName(String username){
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> userList = userMapper.selectByExample(example);
		if (userList.size()==0){
			return null;
		}
		return userList.get(0);
	}

	/**
	 * 分页处理
	 * @param pageNum
	 * @param pageSize
	 * @param example
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public Page<User> getScrollData(int pageNum, int pageSize, UserExample example) {
		// 分页
		PageHelper.startPage(pageNum, pageSize) ;
		PageHelper.orderBy("create_date desc,id asc");

		// 查询数据
		List<User> users = userMapper.selectByExample(example);
		Page<User> page = new Page<>(users) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get user scroll object count: {}", page.getCount());
		}

		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public List<User> getExample(UserExample example) {
		// 查询数据
		List<User> users = userMapper.selectByExample(example);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get user scroll object size: {}", users.size());
		}

		return users;
	}
}
