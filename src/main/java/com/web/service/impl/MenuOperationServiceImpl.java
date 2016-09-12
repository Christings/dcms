package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.web.entity.MenuOperation;
import com.web.example.MenuOperationExample;
import com.web.mappers.MenuOperationMapper;
import com.web.service.MenuOperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 菜单-->操作 逻辑接口 实现类
 * @author 杜延雷
 * @date 2016-09-11
 */
@Service
@Transactional
public class MenuOperationServiceImpl implements MenuOperationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuOperationServiceImpl.class) ;

    @Autowired
    MenuOperationMapper menuOperationMapper;

    @Override
    public int save(MenuOperation menuOperation) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save MenuOperation: {}", JSON.toJSONString(menuOperation));
        }

        if (null == menuOperation) {
            LOGGER.warn("the MenuOperation object is null.");
            return 0 ;
        }

        // 插入记录
        int result = menuOperationMapper.insertSelective(menuOperation) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save MenuOperation object result: {}", result);
        }

        return result;
    }

    @Override
    public int updateById(MenuOperation menuOperation) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update MenuOperation: {}", JSON.toJSONString(menuOperation));
        }

        if (null == menuOperation) {
            LOGGER.warn("the menuOperation object is null.");
            return 0 ;
        }

        // 更新记录
        int result = menuOperationMapper.updateByPrimaryKeySelective(menuOperation) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update menuOperation object result: {}", result);
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
        int result = menuOperationMapper.deleteByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete menuOperation object by id result: {}", result);
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public MenuOperation getById(String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find menuOperation by id: {}", key);
        }

        if (StringUtils.isEmpty(key)) {
            LOGGER.warn("the menu id object is null.");
            return null ;
        }

        // 查找实体对象
        MenuOperation menuOperation = menuOperationMapper.selectByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find menuOperation object by id result: {}", JSON.toJSONString(menuOperation));
        }

        return menuOperation;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<MenuOperation> getAll() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get all MenuOperation");
        }

        //查询所有操作
        MenuOperationExample example = new MenuOperationExample();

        List<MenuOperation> menuOperations = menuOperationMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get MenuOperation all object count: {}", menuOperations.size());
        }

        return menuOperations;
    }

    @Override
    public List<MenuOperation> getByMenuId(String menuId) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get menuId all MenuOperation");
        }

        //查询菜单下的所有操作
        MenuOperationExample example = new MenuOperationExample();
        MenuOperationExample.Criteria criteria = example.createCriteria();
        criteria.andMenuIdEqualTo(menuId);

        List<MenuOperation> menuOperations = menuOperationMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get MenuOperation all object count: {}", menuOperations.size());
        }

        return menuOperations;
    }
}
