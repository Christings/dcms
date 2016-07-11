package com.web.mappers;

import java.util.List;

import com.web.core.mapper.BaseMapper;
import com.web.core.util.Page;
import com.web.entity.UserRole;
/**
 * 
* @ClassName: UserRoleMapper 
* @Description: 
* @author 童云鹏 
* @date 2016年7月11日 下午2:10:02
 */
public interface UserRoleMapper extends BaseMapper<UserRole>{

	public List<UserRole> getUserRoleByPage(Page<UserRole> page)throws Exception;
}
