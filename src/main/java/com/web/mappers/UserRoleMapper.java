package com.web.mappers;

import com.web.core.mapper.BaseMapper;
import com.web.entity.UserRole;
import com.web.example.RoleUserExample;

import java.util.List;

/**
 * 
* @ClassName: UserRoleMapper 
* @Description: 
* @author 童云鹏 
* @date 2016年7月11日 下午2:10:02
 */
public interface UserRoleMapper extends BaseMapper<UserRole>{



	public int countByExample(RoleUserExample example);

	public List<UserRole> selectByExample(RoleUserExample example);

	public int saveList(List<UserRole> list);

	public int deleteByUserId(String userId);

	public List<UserRole> selectByUserId(String userId);

	public List<UserRole> selectByRoleId(String roleId);

}
