package com.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.entity.User;
import com.web.mappers.UserMapper;
import com.web.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private  UserMapper userMapper;

	

public User getUserById(String id) {
	User user = userMapper.selectOneById(id);
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

	public int deleteUser(String id){
		return userMapper.delete(id);
	}

	public User getUserByName(String name)throws Exception{
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
	public  int updateUserPassword(User user){
		return userMapper.updateUserPassword(user);
	}

}
