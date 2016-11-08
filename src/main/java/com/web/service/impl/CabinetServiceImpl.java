package com.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.web.bean.form.CabinetForm;
import com.web.bean.result.CabinetResult;
import com.web.core.util.page.Page;
import com.web.entity.Cabinet;
import com.web.example.CabinetExample;
import com.web.mappers.CabinetMapper;
import com.web.service.CabinetService;

/**
 * 机柜管理接口实现
 *
 * @author 田军兴
 * @date 2016-11-07
 */
@Service
@Transactional
public class CabinetServiceImpl implements CabinetService{

    @Autowired
    private CabinetMapper mapper;

    @Override
    public Page<Cabinet> getByPage(int pageCurrent, int count, CabinetExample example) {
        PageHelper.startPage(pageCurrent,count);
        List<Cabinet> cabinets = mapper.selectByExample(example);
        Page<Cabinet> page = new Page<>(cabinets);
        return page;
    }

    @Override
    public Page<CabinetResult> getByPage(int pageCurrent, int count, CabinetForm form) {
        PageHelper.startPage(pageCurrent,count);
        List<CabinetResult> grids = mapper.selectGridData(form);
        Page<CabinetResult> page = new Page<>(grids);
        return page;
    }

    @Override
    public List<Cabinet> getByExample(CabinetExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int save(Cabinet entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int updateById(Cabinet entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int deleteById(String key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public Cabinet getById(String key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public List<Cabinet> getAll() {
        return mapper.selectByExample(new CabinetExample());
    }

    @Override
    public int getCount(CabinetExample example) {
        return mapper.countByExample(example);
    }
}
