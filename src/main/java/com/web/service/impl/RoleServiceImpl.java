package com.web.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.example.RoleExample;
import com.web.example.UserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.core.util.Page;
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


	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class) ;
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
	public List<Role> getByPage(Page<Role> page) throws Exception {
		// TODO Auto-generated method stub
		return roleMapper.getByPage(page);
	}

	@Override
	public int countByExample(RoleExample example) {
		return roleMapper.countByExample(example);
	}

	@Override
	public QueryResult<Role> getScrollData(int pageCurrent, int count, RoleExample example) {
      example.setOrderByClause("create_date");
		// 分页
		PageHelper.startPage(pageCurrent, count) ;

		List<Role> roles=roleMapper.selectByExample(example);
		PageInfo<Role> pageInfo = new PageInfo<>(roles) ;

		QueryResult<Role> queryResult = new QueryResult<>(pageInfo.getList(), pageInfo.getTotal()) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get menu scroll object count: {}", queryResult.getTotalRecord());
		}

		return queryResult;
	}


}
