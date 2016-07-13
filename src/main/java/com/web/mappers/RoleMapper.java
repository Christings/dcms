package com.web.mappers;

import java.util.List;

import com.web.core.mapper.BaseMapper;
import com.web.core.util.Page;
import com.web.entity.Role;

public interface RoleMapper extends BaseMapper<Role>{
	//分页查询角色
	public List<Role> getByPage(Page<Role> page)throws Exception;
}
