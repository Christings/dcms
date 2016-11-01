package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.Role;
import com.web.example.MenuRoleExample;
import com.web.example.RoleExample;
import com.web.mappers.MenuRoleMapper;
import com.web.mappers.RoleMapper;
import com.web.service.RoleSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 角色管理业务逻辑接口实现
 *
 * @author 杜延雷
 * @date 2016-08-10
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleSerivce {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private MenuRoleMapper menuRoleMapper;

	@Override
	public int save(Role entity) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("save Role: {}", JSON.toJSONString(entity));
		}

		if (null == entity) {
			LOGGER.warn("the role object is null.");
			return 0 ;
		}

		// 插入记录
		int result = roleMapper.insertSelective(entity);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("save Role object result: {}", result);
		}

		return result;
	}

	@Override
	public int updateById(Role entity) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("update Role: {}", JSON.toJSONString(entity));
		}

		if (null == entity) {
			LOGGER.warn("the role object is null.");
			return 0 ;
		}

		// 更新记录
		int result = roleMapper.updateByPrimaryKeySelective(entity) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("update role object result: {}", result);
		}

		return result;
	}

	@Override
	public int deleteById(String key) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("delete key by id: {}", key);
		}

		if (StringUtils.isEmpty(key)) {
			LOGGER.warn("the key id object is null.");
			return 0 ;
		}

		//删除菜单和角色关系
		MenuRoleExample example = new MenuRoleExample();
		MenuRoleExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdEqualTo(key);
		menuRoleMapper.deleteByExample(example);

		//删除记录数
		int result = roleMapper.deleteByPrimaryKey(key);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("delete role object by id result: {}", result);
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public Role getById(String key) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("find role by id: {}", key);
		}

		if (StringUtils.isEmpty(key)) {
			LOGGER.warn("the role id object is null.");
			return null ;
		}

		// 查找实体对象
		Role role = roleMapper.selectByPrimaryKey(key);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("find role object by id result: {}", JSON.toJSONString(role));
		}

		return role;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public List<Role> getAll() {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get all role");
		}

		//查询所有角色
		RoleExample example = new RoleExample();
		List<Role> roles = roleMapper.selectByExample(example);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get role all object count: {}", roles.size());
		}

		return roles;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public int count(RoleExample example) {
		return roleMapper.countByExample(example);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public Role getByExample(RoleExample example) {
		List<Role> roles = roleMapper.selectByExample(example);
		if(roles.size()==0){
			return null;
		}
		return roles.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public Page<Role> getScrollData(int pageNum, int pageSize, RoleExample example) {
		// 分页
		PageHelper.startPage(pageNum, pageSize);
//		PageHelper.orderBy("create_date desc,id");

		List<Role> roles = roleMapper.selectByExample(example);
		Page<Role> page = new Page<>(roles);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get role scroll object count: {}", page.getCount());
		}

		return page;
	}

	@Override
	public List<Role> getByUserId(Map<String, String> params) {
		List<Role> roles = roleMapper.selectByUserId(params);
		return roles;
	}
}
