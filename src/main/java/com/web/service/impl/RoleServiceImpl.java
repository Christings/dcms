package com.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.Role;
import com.web.example.RoleExample;
import com.web.mappers.RoleMapper;
import com.web.service.RoleSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
	public int countByExample(RoleExample example) {
		return roleMapper.countByExample(example);
	}

	@Override
	public Page<Role> getScrollData(int pageNum, int pageSize, RoleExample example) {

		// 分页
		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy("create_date");

		List<Role> roles=roleMapper.selectByExample(example);
		Page<Role> page = new Page<>(roles) ;



		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get menu scroll object count: {}", page.getCount());
		}

		return page;
	}


}
