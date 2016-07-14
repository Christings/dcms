package com.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.core.util.Page;
import com.web.core.util.page.QueryResult;
import com.web.entity.UserRole;
import com.web.example.RoleUserExample;
import com.web.mappers.UserRoleMapper;
import com.web.service.RoleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tong_yunpeng on 2016/7/13.
 */
@Service
public class RoleUserServiceImpl implements RoleUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleUserServiceImpl.class);

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
        PageHelper.startPage(pageCurrent,count);
        example.setOrderByClause("create_date");
        List<UserRole> list=userRoleMapper.selectByExample(example);
        PageInfo<UserRole> pageInfo=new PageInfo<>(list);
        QueryResult<UserRole> queryResult=new QueryResult<>(pageInfo.getList(),pageInfo.getTotal());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get menu scroll object count: {}", queryResult.getTotalRecord());
        }
        return queryResult;

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
    public QueryResult<UserRole> getScrollDataByUserId(int pageCurrent, int count, String userId) {
        // 分页
        PageHelper.startPage(pageCurrent, count);
        List<UserRole> list=userRoleMapper.selectByUserId(userId);
        PageInfo<UserRole> pageInfo=new PageInfo<>(list);
        QueryResult<UserRole> queryResult=new QueryResult<>(pageInfo.getList(),pageInfo.getTotal());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get menu scroll object count: {}", queryResult.getTotalRecord());
        }
        return queryResult;
    }

    @Override
    public QueryResult<UserRole> getScrollDataByRoleId(int pageCurrent, int count, String roleId) {
        // 分页
        PageHelper.startPage(pageCurrent, count);

        List<UserRole> list=userRoleMapper.selectByUserId(roleId);
        PageInfo<UserRole> pageInfo=new PageInfo<>(list);
        QueryResult<UserRole> queryResult=new QueryResult<>(pageInfo.getList(),pageInfo.getTotal());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get menu scroll object count: {}", queryResult.getTotalRecord());
        }
        return queryResult;
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
