package com.web.service;


import com.web.core.util.page.Page;
import com.web.entity.User;
import com.web.example.UserExample;

public interface UserService extends IService<User,String> {

	/**
	 * 根据查询条件获取查询数量
     */
	int count(UserExample example);

	/**
	 * 根据用户登录名称查询用户
	 *
	 * @param username
	 * @return
     */
	User getUserByName(String username);

	/**
	 * 分页处理 根据查询条件进行分页
	 * @param pageNum
	 * @param pageSize
	 * @param example
	 * @return
	 */
	Page<User> getScrollData(int pageNum, int pageSize, UserExample example);
}
