package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.FixedEquipment;
import com.web.example.FixedEquipmentExample;
import com.web.mappers.FixedEquipmentMapper;
import com.web.service.FixedEquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 机柜设备管理  逻辑接口 实现类
 *
 * @author 杜延雷
 * @date 2016/8/25.
 */
@Service
@Transactional
public class FixedEquipmentServiceImpl implements FixedEquipmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FixedEquipmentServiceImpl.class);

    @Autowired
    FixedEquipmentMapper fixedEquipmentMapper;

    @Override
    public int save(FixedEquipment entity) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save FixedEquipment: {}", JSON.toJSONString(entity));
        }

        if (null == entity) {
            LOGGER.warn("the FixedEquipment object is null.");
            return 0 ;
        }

        int result = fixedEquipmentMapper.insertSelective(entity);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save FixedEquipment object result: {}", result);
        }

        return result;
    }

    @Override
    public int updateById(FixedEquipment entity) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update FixedEquipment: {}", JSON.toJSONString(entity));
        }

        if (null == entity) {
            LOGGER.warn("the FixedEquipment object is null.");
            return 0 ;
        }

        // 更新记录
        int result = fixedEquipmentMapper.updateByPrimaryKeySelective(entity) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update FixedEquipment object result: {}", result);
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
        int result = fixedEquipmentMapper.deleteByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete FixedEquipment object by id result: {}", result);
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public FixedEquipment getById(String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find fixedEquipment by id: {}", key);
        }

        if (StringUtils.isEmpty(key)) {
            LOGGER.warn("the fixedEquipment id object is null.");
            return null ;
        }

        // 查找实体对象
        FixedEquipment fixedEquipment = fixedEquipmentMapper.selectByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find fixedEquipment object by id result: {}", JSON.toJSONString(fixedEquipment));
        }

        return fixedEquipment;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<FixedEquipment> getAll() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get all menu");
        }

        //查询所有菜单
        FixedEquipmentExample example = new FixedEquipmentExample();
        //排序
        example.setOrderByClause("create_date desc,id");
        List<FixedEquipment> fixedEquipments = fixedEquipmentMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get fixedEquipments all object count: {}", fixedEquipments.size());
        }

        return fixedEquipments;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public int count(FixedEquipmentExample example) {
        return fixedEquipmentMapper.countByExample(example);
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
    public Page<FixedEquipment> getScrollData(int pageNum, int pageSize, FixedEquipmentExample example) {
        // 分页
        PageHelper.startPage(pageNum, pageSize) ;
        PageHelper.orderBy("create_date desc,id asc");

        // 查询数据
        List<FixedEquipment> fixedEquipments = fixedEquipmentMapper.selectByExample(example);
        Page<FixedEquipment> page = new Page<>(fixedEquipments) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get fixedEquipment scroll object count: {}", page.getCount());
        }

        return page;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<FixedEquipment> getExample(FixedEquipmentExample example) {
        // 查询数据
        List<FixedEquipment> fixedEquipments = fixedEquipmentMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get fixedEquipment scroll object size: {}", fixedEquipments.size());
        }

        return fixedEquipments;
    }
}
