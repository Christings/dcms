package com.web.mappers;

import java.util.List;

import com.web.core.mapper.BaseMapper;
import com.web.core.util.Page;
import com.web.entity.Role;
import com.web.example.RoleExample;

public interface RoleMapper extends BaseMapper<Role>{
	//分页查询角色
	public List<Role> getByPage(Page<Role> page)throws Exception;

	public int countByExample(RoleExample example);
	//分页查询
	public List<Role> selectByExample(RoleExample example);
}
