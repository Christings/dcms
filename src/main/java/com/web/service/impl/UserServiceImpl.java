package com.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.core.util.page.Page;
import com.web.entity.User;
import com.web.example.UserExample;
import com.web.mappers.UserMapper;
import com.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户Service
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	/**
	 *
	 * @param id
	 * @return
     */
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
	public int updateUserPassword(User user) {
		return userMapper.updateUserPassword(user);
	}

	@Override
	public Page<User> getScrollData(int pageNum, int pageSize, UserExample example) {

		//example.setOrderByClause("CREATE_DATE");

		// 分页
		PageHelper.startPage(pageNum, pageSize);
		// 排序
		PageHelper.orderBy("CREATE_DATE");

		// 查询数据
		List<User> users = userMapper.selectByExample(example);
		Page<User> page = new Page<>(users);



		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get menu scroll object count: {}", page.getCount());
		}

		return page;
	}

	@Override
	public int countByExample(UserExample example) {
		return userMapper.countByExample(example);
	}

}
