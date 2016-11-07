package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.Type;
import com.web.example.TypeExample;
import com.web.mappers.TypeMapper;
import com.web.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 分类管理 逻辑接口 实现类
 *
 * @author 杜延雷
 * @date 2016-11-07
 */
@Service
@Transactional
public class TypeServiceImpl implements TypeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TypeServiceImpl.class) ;

    @Autowired
    TypeMapper typeMapper;

    @Override
    public int save(Type type) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save Type: {}", JSON.toJSONString(type));
        }

        if (null == type) {
            LOGGER.warn("the type object is null.");
            return 0 ;
        }

        // 插入记录
        int result = typeMapper.insertSelective(type) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save Type object result: {}", result);
        }

        return result;
    }

    @Override
    public int updateById(Type type) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update Type: {}", JSON.toJSONString(type));
        }

        if (null == type) {
            LOGGER.warn("the type object is null.");
            return 0 ;
        }

        // 更新记录
        int result = typeMapper.updateByPrimaryKeySelective(type) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update type object result: {}", result);
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
        int result = typeMapper.deleteByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete type object by id result: {}", result);
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Type getById(String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find type by id: {}", key);
        }

        if (StringUtils.isEmpty(key)) {
            LOGGER.warn("the type id object is null.");
            return null ;
        }

        // 查找实体对象
        Type type = typeMapper.selectByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find muen object by id result: {}", JSON.toJSONString(type));
        }

        return type;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Type> getAll() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get all type");
        }

        //查询所有分类
        TypeExample example = new TypeExample();
        //排序
        List<Type> typeList = typeMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get type all object count: {}", typeList.size());
        }

        return typeList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Type> getByParentId(String key){
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find type by parentID: {}", key);
        }

        //查询所有菜单
        TypeExample example = new TypeExample();
        TypeExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isEmpty(key)){
            criteria.andParentIdIsNull();
        }else {
            criteria.andParentIdEqualTo(key);
        }

        List<Type> typeList = typeMapper.selectByExample(example);

        return typeList;
    }

    @Override
    public int countByExample(TypeExample example) {
        if (null == example){
            return 0;
        }
        return typeMapper.countByExample(example);
    }

    @Override
    public List<Type> getByExample(TypeExample example) {
        if (null == example){
            return null;
        }

        List<Type> list = typeMapper.selectByExample(example);

        return list;
    }

    /**
     * 查询返回树形菜单
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Type> getTree(TypeExample example) {
        if(null == example){
            return null;
        }
        // 查询数据
        List<Type> types = typeMapper.selectByTree(example);
        return types;
    }

    /**
     * 分页处理
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Page<Type> getScrollData(int pageNum, int pageSize, TypeExample example) {

        // 分页
        PageHelper.startPage(pageNum, pageSize) ;

        // 查询数据
        List<Type> types = typeMapper.selectByExamplePage(example);
        Page<Type> page = new Page<>(types) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get types scroll object count: {}", page.getCount());
        }

        return page;
    }

    @Override
    public int deleteCascadeById(String id) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete id by id: {}", id);
        }

        if (StringUtils.isEmpty(id)) {
            LOGGER.warn("the key id object is null.");
            return 0 ;
        }

        // 删除记录数
        int result = typeMapper.deleteByPrimaryKey(id) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete type object by id result: {}", result);
        }

        return result;
    }
}
