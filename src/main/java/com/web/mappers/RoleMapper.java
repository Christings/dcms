package com.web.mappers;

import com.web.core.mapper.BaseMapper;
import com.web.entity.Role;
import com.web.example.RoleExample;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role>{


	public int countByExample(RoleExample example);
	//分页查询
	public List<Role> selectByExample(RoleExample example);
}
