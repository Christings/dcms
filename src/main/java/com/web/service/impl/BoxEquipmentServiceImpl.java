package com.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.BoxEquipment;
import com.web.example.BoxEquipmentExample;
import com.web.mappers.BoxEquipmentMapper;
import com.web.service.BoxEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机柜和设备关联接口实现
 *
 * @author 田军兴
 * @date 2016-11-8
 */
@Service
@Transactional
public class BoxEquipmentServiceImpl implements BoxEquipmentService{

    @Autowired
    private BoxEquipmentMapper mapper;
    @Override
    public Page<BoxEquipment> getByPage(int pageCurrent, int count, BoxEquipmentExample example) {
        PageHelper.startPage(pageCurrent,count);
        List<BoxEquipment> cabinets = mapper.selectByExample(example);
        Page<BoxEquipment> page = new Page<>(cabinets);
        return page;
    }

    @Override
    public int getCount(BoxEquipmentExample example) {
        return mapper.countByExample(example);
    }

    @Override
    public int save(BoxEquipment entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int updateById(BoxEquipment entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int deleteById(String key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public BoxEquipment getById(String key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public List<BoxEquipment> getAll() {
        return mapper.selectByExample(new BoxEquipmentExample());
    }
}
