package com.web.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.DataDict;
import com.web.example.DataDictExample;
import com.web.mappers.DataDictMapper;
import com.web.service.DataDictService;
import com.web.util.StringUtil;
import com.web.util.UUIDGenerator;

/**
 * 数据词典接口
 *
 * @author 田军兴
 * @date 2016-07-09
 */
@Service
@Transactional
public class DataDictServiceImpl implements DataDictService {

	@Autowired
	private DataDictMapper dataDictMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(DataDictServiceImpl.class);

	@Override
	public Page<DataDict> getByPage(int pageCurrent, int count, DataDictExample example) {
		// 分页
		PageHelper.startPage(pageCurrent, count);

		// 查询数据
		List<DataDict> DataDicts = dataDictMapper.selectByExample(example);
		Page<DataDict> pageInfo = new Page<>(DataDicts);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get data dict object count: {}", pageInfo.getCount());
		}

		return pageInfo;
	}

	@Override
	public int save(DataDict entity) {
		entity.setId(UUIDGenerator.generatorRandomUUID());
		// 如果分组的ID是空的，那么重新生成一个新的ID
		if (StringUtil.isEmpty(entity.getGroupId())) {
			entity.setGroupId(UUIDGenerator.generatorRandomUUID());
			entity.setSort(1);
		} else {
			Integer maxSort = 0;
			maxSort = dataDictMapper.selectMaxSort(entity.getGroupId());
			maxSort = maxSort == null ? 0 : maxSort;
			entity.setSort(maxSort + 1);
		}
		return dataDictMapper.insertSelective(entity);
	}

	@Override
	public int updateById(DataDict entity) {
		return dataDictMapper.updateByPrimaryKey(entity);
	}

	@Override
	public int deleteById(String key) {
		return dataDictMapper.deleteByPrimaryKey(key);
	}

	@Override
	public DataDict getById(String key) {
		return dataDictMapper.selectByPrimaryKey(key);
	}

	@Override
	public List<DataDict> getAll() {
		return dataDictMapper.selectAll();
	}

	@Override
	public List<DataDict> getByGroupId(DataDict dataDict) {
		DataDictExample example = new DataDictExample();
		DataDictExample.Criteria criteria = example.createCriteria();
		criteria.andGroupIdEqualTo(dataDict.getGroupId());
		return dataDictMapper.selectByExample(example);
	}

}
