package com.web.service.impl;

import com.web.core.util.Page;
import com.web.core.util.page.QueryResult;
import com.web.entity.UserRole;
import com.web.example.RoleUserExample;
import com.web.mappers.UserRoleMapper;
import com.web.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tong_yunpeng on 2016/7/13.
 */
@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public List<UserRole> getUserRoleByPage(Page<UserRole> page) throws Exception {
        return userRoleMapper.getUserRoleByPage(page);
    }

    @Override
    public int countByExample(RoleUserExample example) {
        return userRoleMapper.countByExample(example);
    }

    @Override
    public List<UserRole> selectByExample(RoleUserExample example) {
        return null;
    }

    @Override
    public QueryResult<UserRole> getScrollData(int pageCurrent, int count, RoleUserExample example) {
        return null;
    }

    @Override
    public int saveList(List<UserRole> list) {
        return userRoleMapper.saveList(list);
    }

    @Override
    public int deleteByUserId(String userId) {
        return userRoleMapper.deleteByUserId(userId);
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
