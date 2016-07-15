package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.Role;
import com.web.example.RoleExample;

public interface RoleSerivce extends IService<Role, String>{
	/**
     * 分页处理 根据查询条件进行分页
     * @return
     */



	public int countByExample(RoleExample example);
	//分页查询
	public Page<Role> getScrollData(int pageNum, int pageSize,  RoleExample example);
    
}
