package com.web.service.impl;

import com.web.core.dao.ICommonDao;
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

	private ICommonDao dao;
	private  UserMapper userMapper;

	@Resource
	public void setCommonDao(ICommonDao commonDao){
		this.dao = commonDao;
	}

public User getUserById(int id) {
	UserMapper userMapper = (UserMapper)dao.getMapper(UserMapper.class);
	User user = userMapper.getUser(id);
	return user;
	}

	public List<User> getAllUsers(){
		UserMapper userMapper = (UserMapper)dao.getMapper(UserMapper.class);
		return userMapper.getAllUsers();
	}

	public int saveUser(User user){
		UserMapper userMapper = (UserMapper)dao.getMapper(UserMapper.class);
		 return userMapper.saveUser(user);
	}
	public int updateUser(User user){
		UserMapper userMapper = (UserMapper)dao.getMapper(UserMapper.class);
		return userMapper.updateUser(user);
	}

	public int deleteUser(int id){
		UserMapper userMapper = (UserMapper)dao.getMapper(UserMapper.class);
		return userMapper.deleteUser(id);
	}

	public User getUserByName(String name){
		UserMapper userMapper = (UserMapper)dao.getMapper(UserMapper.class);
		return userMapper.getUserByName(name);
	}
}
