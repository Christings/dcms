package com.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.web.bean.form.AreaForm;
import com.web.bean.result.AreaResult;
import com.web.core.util.page.Page;
import com.web.entity.Area;
import com.web.example.AreaExample;
import com.web.mappers.AreaMapper;
import com.web.service.AreaService;

/**
 * 区域管理接口实现类
 *
 * @author 田军兴
 * @date 2016-11-13
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaMapper mapper;

	@Override
	public Page<AreaResult> getByPage(int pageCurrent, int count, AreaForm form) {
		PageHelper.startPage(pageCurrent, count);
		List<AreaResult> areaResults = mapper.selectGridData(form);
		Page<AreaResult> page = new Page<>(areaResults);
		return page;
	}

	@Override
	public int getCount(AreaExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int save(Area entity) {
		return mapper.insertSelective(entity);
	}

	@Override
	public int updateById(Area entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int deleteById(String key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public Area getById(String key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<Area> getAll() {
		return mapper.selectByExample(new AreaExample());
	}

	@Override
	public AreaResult getAreaResultById(AreaForm form) {
		List<AreaResult> list = mapper.selectGridData(form);
		if (list.size() > 0) {
			return list.get(0);
		}
		return new AreaResult();
	}
}
