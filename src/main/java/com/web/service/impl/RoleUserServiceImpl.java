package com.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.UserRole;
import com.web.example.RoleUserExample;
import com.web.mappers.UserRoleMapper;
import com.web.service.RoleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tong_yunpeng on 2016/7/13.
 */
@Service("roleUserService")
@Transactional
public class RoleUserServiceImpl implements RoleUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleUserServiceImpl.class);

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public int countByExample(RoleUserExample example) {
        return userRoleMapper.countByExample(example);
    }

    @Override
    public List<UserRole> selectByExample(RoleUserExample example) {
        return null;
    }

    @Override
    public Page<UserRole> getScrollData(int pageNum, int pageSize,  RoleUserExample example) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserRole> list=userRoleMapper.selectByExample(example);
        Page<UserRole> page=new Page<>(list);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get menu scroll object count: {}", page.getCount());
        }
        return page;

    }

    @Override
    public int saveList(List<UserRole> list) {
        deleteByUserId(list.get(0).getUserid());
        return userRoleMapper.saveList(list);
    }

    @Override
    public int deleteByUserId(String userId) {
        return userRoleMapper.deleteByUserId(userId);
    }

    @Override
    public Page<UserRole> getScrollDataByUserId(int pageNum, int pageSize,  String userId) {
        // 分页
        PageHelper.startPage(pageNum, pageSize);
        List<UserRole> list=userRoleMapper.selectByUserId(userId);
        Page<UserRole> page=new Page<>(list);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get menu scroll object count: {}", page.getCount());
        }
        return page;
    }

    @Override
    public Page<UserRole> getScrollDataByRoleId(int pageNum, int pageSize,  String roleId) {
        // 分页
        PageHelper.startPage(pageNum, pageSize);

        List<UserRole> list=userRoleMapper.selectByUserId(roleId);
        Page<UserRole> page=new Page<>(list);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get menu scroll object count: {}", page.getCount());
        }
        return page;
    }

    @Override
    public int save(UserRole entity) {
        return userRoleMapper.save(entity);
    }

    @Override
    public int updateById(UserRole entity) {
        return userRoleMapper.deleteByUserId(entity.getId());
    }

    @Override
    public int deleteById(String key) {
        return userRoleMapper.deleteByUserId(key);
    }

    @Override
    public UserRole getById(String key) {
        return userRoleMapper.selectOneById(key);
    }

    @Override
    public List<UserRole> getAll() {
        return userRoleMapper.getAll();
    }
}
