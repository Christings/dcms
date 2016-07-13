package com.web.service;

import java.util.List;

import com.web.core.util.Page;
import com.web.entity.Role;

public interface RoleSerivce extends IService<Role, String>{
	/**
     * 分页处理 根据查询条件进行分页
     * @param pagination
     * @param maxResult
     * @param example
     * @return
     */
	public List<Role> getByPage(Page<Role> page)throws Exception;
    
}
