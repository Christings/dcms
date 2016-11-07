package com.web.service.impl;

import java.util.List;

import com.web.entity.RoomIcngph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.example.RoomIcngphExample;
import com.web.mappers.RoomIcngphMapper;
import com.web.service.RoomIcngphService;

/**
 * 机房平面图管理业务逻辑接口实现
 *
 * @author 田军兴
 * @date 2016-010-22
 */
@Service
@Transactional
public class RoomIcngphServiceImpl implements RoomIcngphService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomIcngphServiceImpl.class);

    @Autowired
    private RoomIcngphMapper mapper;

    @Override
    public int save(RoomIcngph entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int updateById(RoomIcngph entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int deleteById(String key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public RoomIcngph getById(String key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public List<RoomIcngph> getAll() {
        return mapper.selectByExample(new RoomIcngphExample());
    }

    @Override
    public Page<RoomIcngph> getScrollData(int pageNum, int pageSize, RoomIcngphExample example) {
        PageHelper.startPage(pageNum,pageSize);
        List<RoomIcngph> icngphs = mapper.selectByExample(example);
        Page<RoomIcngph> page = new Page<>(icngphs);
        return page;
    }

    @Override
    public List<RoomIcngph> getByExample(RoomIcngphExample example) {
        return mapper.selectByExample(example);
    }
}
