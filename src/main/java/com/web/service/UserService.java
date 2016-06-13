package com.web.service;

import com.web.entity.User;

import java.util.List;

public interface UserService {
	/*根据ID查询User*/
	public User getUserById(int id);

	/*根据name查询User*/
	public User getUserByName(String name);

	/*获取全部用户*/
	public List<User> getAllUsers();

	/**
	 * @param user
	 * 保存用户
	 * */
	public int saveUser(User user);

	public int updateUser(User user);

	public int deleteUser(int id);
}
