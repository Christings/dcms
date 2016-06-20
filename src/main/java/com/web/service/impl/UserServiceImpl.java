package com.web.service.impl;

import com.web.core.dao.ICommonDao;
import com.web.core.util.page.PageBounds;
import com.web.mappers.UserMapper;
import com.web.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.entity.User;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private  UserMapper userMapper;

	@Resource
	public void setUserMapper(UserMapper userMapper){
		this.userMapper = userMapper;
	}

public User getUserById(int id) {
	User user = userMapper.selectOneById(String.valueOf(id));
	return user;
	}

	public List<User> getAllUsers(){
		return userMapper.getAll();
	}

	public int saveUser(User user){
		 return userMapper.save(user);
	}
	public int updateUser(User user){
		return userMapper.update(user);
	}

	public int deleteUser(int id){
		return userMapper.delete(String.valueOf(id));
	}

	public User getUserByName(String name){
		return userMapper.getUserByName(name);
	}

	public List<User> getUserPage(PageBounds bounds, User user) {
		List<User> users = userMapper.getByPage(bounds,user);
		System.out.println(bounds.getTotal() + bounds.getLimit() + bounds.getOffset());
		return users;
	}
}
