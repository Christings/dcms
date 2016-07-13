package com.web.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.core.util.Page;
import com.web.core.util.page.QueryResult;
import com.web.entity.Menu;
import com.web.example.MenuExample;
import com.web.example.UserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.entity.User;
import com.web.mappers.UserMapper;
import com.web.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class) ;

	@Autowired
	private UserMapper userMapper;

	public User getUserById(String id) {
		User user = userMapper.selectOneById(id);
		return user;
	}

	public List<User> getAllUsers() {
		return userMapper.getAll();
	}

	public int saveUser(User user) {
		return userMapper.save(user);
	}

	public int updateUser(User user) {
		return userMapper.update(user);
	}

	public int deleteUser(String id) {
		return userMapper.delete(id);
	}

	public User getUserByName(String name) throws Exception {
		return userMapper.getUserByName(name);
	}

	@Override
	public void updateUserEnabled(Integer enabled, String id) throws Exception {
		// TODO Auto-generated method stub
		userMapper.updateUserEnabled(enabled, id);
	}

	@Override
	public void updateUserDelete(Integer deleted, String id) throws Exception {
		// TODO Auto-generated method stub
		userMapper.updateUserDelete(deleted, id);
	}

	@Override
	public List<User> getUserPage(Page<User> page)throws Exception {
		/*List<User> users = userMapper.getByPage(page);
		System.out.println(bounds.getTotal() + bounds.getLimit() + bounds.getOffset());*/
		return userMapper.getByPage(page);
	}

	@Override
	public int updateUserPassword(User user) {
		return userMapper.updateUserPassword(user);
	}

	@Override
	public QueryResult<User> getScrollData(int pageCurrent, int count, UserExample example) {
		//排序
		example.setOrderByClause("CREATE_DATE");

		// 分页
		PageHelper.startPage(pageCurrent, count) ;

		// 查询数据
		List<User> users = userMapper.selectByExample(example);
		PageInfo<User> pageInfo = new PageInfo<>(users) ;

		QueryResult<User> queryResult = new QueryResult<>(pageInfo.getList(), pageInfo.getTotal()) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get menu scroll object count: {}", queryResult.getTotalRecord());
		}

		return queryResult;
	}


	@Override
	public int countByExample(UserExample example) {
		return userMapper.countByExample(example);
	}

}
