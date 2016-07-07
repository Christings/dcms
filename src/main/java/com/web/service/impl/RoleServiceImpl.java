package com.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.core.util.page.QueryResult;
import com.web.entity.Menu;
import com.web.entity.Role;
import com.web.example.MenuExample;
import com.web.mappers.RoleMapper;
import com.web.service.RoleSerivce;
/**
 * 
* @ClassName: RoleServiceImpl 
* @Description: 角色管理service
* @author 童云鹏 
* @date 2016年7月7日 下午2:50:37
 */
@Service("roleSerivce")
@Transactional
public class RoleServiceImpl implements RoleSerivce{
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public int save(Role entity) {
		// TODO Auto-generated method stub
		return roleMapper.save(entity);
	}

	@Override
	public int updateById(Role entity) {
		// TODO Auto-generated method stub
		return roleMapper.update(entity);
	}

	@Override
	public int deleteById(String key) {
		// TODO Auto-generated method stub
		return roleMapper.delete(key);
	}

	@Override
	public Role getById(String key) {
		// TODO Auto-generated method stub
		return roleMapper.selectOneById(key);
	}

	@Override
	public List<Role> getAll() {
		// TODO Auto-generated method stub
		return roleMapper.getAll();
	}

	@Override
	public QueryResult<Menu> getScrollData(int pagination, int maxResult,
			MenuExample example) {
		// TODO Auto-generated method stub
		return null;
	}

}
