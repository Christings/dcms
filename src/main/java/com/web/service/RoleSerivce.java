package com.web.service;

import java.util.List;

import com.web.core.util.Page;
import com.web.core.util.page.QueryResult;
import com.web.entity.Role;
import com.web.example.RoleExample;
import com.web.example.UserExample;

public interface RoleSerivce extends IService<Role, String>{
	/**
     * 分页处理 根据查询条件进行分页
     * @return
     */
	public List<Role> getByPage(Page<Role> page)throws Exception;


	public int countByExample(RoleExample example);
	//分页查询
	public QueryResult<Role> getScrollData(int pageCurrent, int count, RoleExample example);
    
}
