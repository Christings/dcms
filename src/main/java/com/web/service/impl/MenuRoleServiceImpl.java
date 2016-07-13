package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.web.core.exception.BusinessException;
import com.web.entity.MenuRole;
import com.web.example.MenuRoleExample;
import com.web.mappers.MenuRoleMapper;
import com.web.service.MenuRoleService;
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
 * 菜单角色关系 逻辑接口 实现类
 *
 * @author 杜延雷
 * @date 2016-07-12
 */
@Service
@Transactional
public class MenuRoleServiceImpl implements MenuRoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuRoleServiceImpl.class) ;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    @Override
    public int save(MenuRole menuRole) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save MenuRole: {}", JSON.toJSONString(menuRole));
        }

        if (null == menuRole) {
            LOGGER.warn("the menuRole object is null.");
            return 0 ;
        }

        //设置删除条件 并删除原有关系
        MenuRoleExample example = new MenuRoleExample();
        MenuRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(menuRole.getRoleId());
        criteria.andMenuIdEqualTo(menuRole.getMenuId());
        menuRoleMapper.deleteByExample(example) ;

        // 插入记录
        int result = menuRoleMapper.insertSelective(menuRole) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save menuRole object result: {}", result);
        }

        return result;
    }

    @Override
    public int updateById(MenuRole menuRole) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update menuRole: {}", JSON.toJSONString(menuRole));
        }

        if (null == menuRole) {
            LOGGER.warn("the menuRole object is null.");
            return 0 ;
        }

        // 更新记录
        int result = menuRoleMapper.updateByPrimaryKeySelective(menuRole) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update menuRole object result: {}", result);
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
        int result = menuRoleMapper.deleteByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete MenuRole object by id result: {}", result);
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public MenuRole getById(String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find menuRole by id: {}", key);
        }

        if (StringUtils.isEmpty(key)) {
            LOGGER.warn("the menuRole id object is null.");
            return null ;
        }

        // 查找实体对象
        MenuRole menuRole = menuRoleMapper.selectByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find menuRole object by id result: {}", JSON.toJSONString(menuRole));
        }

        return menuRole;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<MenuRole> getAll() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get all MenuRole");
        }

        //查询条件
        MenuRoleExample example = new MenuRoleExample();
        //排序
        List<MenuRole> menuRoleList = menuRoleMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get MenuRole all object count: {}", menuRoleList.size());
        }

        return menuRoleList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<MenuRole> getRoleMenu(String roleId) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get roleId MenuRole");
        }

        //查询条件
        MenuRoleExample example = new MenuRoleExample();
        MenuRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        //排序
        List<MenuRole> menuRoleList = menuRoleMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get MenuRole all object count: {}", menuRoleList.size());
        }

        return menuRoleList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<MenuRole> getMenuRole(String menuId) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get all MenuRole");
        }

        //查询条件
        MenuRoleExample example = new MenuRoleExample();
        MenuRoleExample.Criteria criteria = example.createCriteria();
        criteria.andMenuIdEqualTo(menuId);
        //排序
        List<MenuRole> menuRoleList = menuRoleMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get MenuRole all object count: {}", menuRoleList.size());
        }

        return menuRoleList;
    }

    @Override
    public void batchRoleMenu(String roleId, String[] menuIds) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("param roleId: {}, menuIds:{}", roleId,menuIds);
        }

        try {
            //设置删除条件 并删除原有关系
            MenuRoleExample example = new MenuRoleExample();
            MenuRoleExample.Criteria criteria = example.createCriteria();
            criteria.andRoleIdEqualTo(roleId);
            menuRoleMapper.deleteByExample(example);

            //批量插入记录
            MenuRole menuRole;
            for(String menuId:menuIds){
                menuRole = new MenuRole();
                menuRole.setId(UUIDGenerator.generatorRandomUUID());
                menuRole.setRoleId(roleId);
                menuRole.setMenuId(menuId);
                menuRoleMapper.insertSelective(menuRole);
            }

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("batch Save menuRole object result: {}");
            }
        }catch (Exception e){
            throw new BusinessException("批量根据角色保存菜单失败",e);
        }
    }

    @Override
    public void batchMenuRole(String menuId, String[] roleIds) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("param menuId: {}, roleIds:{}", menuId,roleIds);
        }

        try {
            //设置删除条件 并删除原有关系
            MenuRoleExample example = new MenuRoleExample();
            MenuRoleExample.Criteria criteria = example.createCriteria();
            criteria.andMenuIdEqualTo(menuId);
            menuRoleMapper.deleteByExample(example);

            //批量插入记录
            MenuRole menuRole;
            for(String roleId:roleIds){
                menuRole = new MenuRole();
                menuRole.setId(UUIDGenerator.generatorRandomUUID());
                menuRole.setRoleId(roleId);
                menuRole.setMenuId(menuId);
                menuRoleMapper.insertSelective(menuRole);
            }

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("batch Save menuRole object result: {}");
            }
        }catch (Exception e){
            throw new BusinessException("批量根据菜单保存角色失败",e);
        }
    }
}
