package com.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.ServiceRoomIcngph;
import com.web.example.ServiceRoomIcngphExample;
import com.web.mappers.ServiceRoomIcngphMapper;
import com.web.service.ServiceRoomIcngphService;

/**
 * 机房平面图管理业务逻辑接口实现
 *
 * @author 田军兴
 * @date 2016-010-22
 */
@Service
@Transactional
public class ServiceRoomIcngphServiceImpl implements ServiceRoomIcngphService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRoomIcngphServiceImpl.class);

    @Autowired
    private ServiceRoomIcngphMapper mapper;

    @Override
    public int save(ServiceRoomIcngph entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int updateById(ServiceRoomIcngph entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int deleteById(String key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public ServiceRoomIcngph getById(String key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public List<ServiceRoomIcngph> getAll() {
        return mapper.selectByExample(new ServiceRoomIcngphExample());
    }

    @Override
    public Page<ServiceRoomIcngph> getScrollData(int pageNum, int pageSize, ServiceRoomIcngphExample example) {
        PageHelper.startPage(pageNum,pageSize);
        List<ServiceRoomIcngph> icngphs = mapper.selectByExample(example);
        Page<ServiceRoomIcngph> page = new Page<>(icngphs);
        return page;
    }

    @Override
    public List<ServiceRoomIcngph> getByExample(ServiceRoomIcngphExample example) {
        return mapper.selectByExample(example);
    }
}
