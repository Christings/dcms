package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.web.core.exception.BusinessException;
import com.web.entity.UserRole;
import com.web.example.UserRoleExample;
import com.web.mappers.UserRoleMapper;
import com.web.service.UserRoleService;
import com.web.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户角色关系  逻辑接口实现
 *
 * @author 杜延雷
 * @date 2016-08-11
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public int save(UserRole entity) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save UserRole: {}", JSON.toJSONString(entity));
        }

        if (null == entity) {
            LOGGER.warn("the UserRole object is null.");
            return 0 ;
        }

        //设置删除条件 并删除原有关系
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(entity.getRoleId());
        criteria.andUserIdEqualTo(entity.getUserId());
        userRoleMapper.deleteByExample(example) ;

        // 插入记录
        int result = userRoleMapper.insertSelective(entity) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save userRole object result: {}", result);
        }

        return result;
    }

    @Override
    public int updateById(UserRole entity) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update userRole: {}", JSON.toJSONString(entity));
        }

        if (null == entity) {
            LOGGER.warn("the userRole object is null.");
            return 0 ;
        }

        // 更新记录
        int result = userRoleMapper.updateByPrimaryKeySelective(entity) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update userRole object result: {}", result);
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

        // 删除记录数
        int result = userRoleMapper.deleteByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete userRole object by id result: {}", result);
        }

        return result;
    }

    @Override
    public int delete( UserRoleExample example) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete condition example by id: {}", example);
        }

        // 删除记录数
        int result = userRoleMapper.deleteByExample(example) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete condition object by id result: {}", result);
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public UserRole getById(String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find userRole by id: {}", key);
        }

        if (StringUtils.isEmpty(key)) {
            LOGGER.warn("the userRole id object is null.");
            return null ;
        }

        // 查找实体对象
        UserRole userRole = userRoleMapper.selectByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find userRole object by id result: {}", JSON.toJSONString(userRole));
        }

        return userRole;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<UserRole> getAll() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get all UserRole");
        }

        //查询条件
        UserRoleExample example = new UserRoleExample();
        //排序
        List<UserRole> userRoleList = userRoleMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get UserRole all object count: {}", userRoleList.size());
        }

        return userRoleList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<UserRole> getRoleUser(String roleId) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get roleId UserRole");
        }

        //查询条件
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        //排序
        List<UserRole> userRoleList = userRoleMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get roleId userRole object count: {}", userRoleList.size());
        }

        return userRoleList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<UserRole> getUserRole(String userId) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get userId UserRole");
        }

        //查询条件
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        //排序
        List<UserRole> userRoleList = userRoleMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get userId UserRole object count: {}", userRoleList.size());
        }

        return userRoleList;
    }

    @Override
    public void batchRoleUser(String roleId, String[] userIds) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("param roleId: {}, userIds:{}", roleId,userIds);
        }

        try {
            //设置删除条件 并删除原有关系
            UserRoleExample example = new UserRoleExample();
            UserRoleExample.Criteria criteria = example.createCriteria();
            criteria.andRoleIdEqualTo(roleId);
            userRoleMapper.deleteByExample(example);

            //批量插入记录
            UserRole userRole;
            for(String userId : userIds){
                userRole = new UserRole();
                userRole.setId(UUIDGenerator.generatorRandomUUID());
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRoleMapper.insertSelective(userRole);
            }

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("batch Save userRole object result: {}");
            }
        }catch (Exception e){
            throw new BusinessException("批量根据角色保存用户失败",e);
        }
    }

    @Override
    public void batchUserRole(String userId, String[] roleIds) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("param userId: {}, roleIds:{}", userId,roleIds);
        }

        try {
            //设置删除条件 并删除原有关系
            UserRoleExample example = new UserRoleExample();
            UserRoleExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(userId);
            userRoleMapper.deleteByExample(example);

            //批量插入记录
            UserRole userRole;
            for(String roleId:roleIds){
                userRole = new UserRole();
                userRole.setId(UUIDGenerator.generatorRandomUUID());
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRoleMapper.insertSelective(userRole);
            }

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("batch Save UserRole object result: {}");
            }
        }catch (Exception e){
            throw new BusinessException("批量根据用户保存角色失败",e);
        }
    }
}
