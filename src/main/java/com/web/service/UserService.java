package com.web.service;

import com.web.core.util.Page;
import com.web.core.util.page.QueryResult;
import com.web.entity.Menu;
import com.web.entity.User;

import java.util.List;

import com.web.example.MenuExample;
import com.web.example.UserExample;
import org.apache.ibatis.annotations.Param;

public interface UserService {
	/*根据ID查询User*/
	public User getUserById(String id);

	/*根据name查询User*/
	public User getUserByName(String name)throws Exception;

	/*获取全部用户*/
	public List<User> getAllUsers();
	/*是否启用用户*/
    public void updateUserEnabled(Integer enabled,String id)throws Exception;
    /*是否逻辑删除*/
    public void updateUserDelete(Integer deleted,String id)throws Exception;

	/**
	 * @param user
	 * 保存用户
	 * */
	public int saveUser(User user);

	public int updateUser(User user);

	public int deleteUser(String id);

	public List<User> getUserPage(Page<User> page)throws Exception;

	public  int updateUserPassword(User user);



	public QueryResult<User> getScrollData(int pageCurrent, int count, UserExample example);

	public int countByExample(UserExample example);

}
