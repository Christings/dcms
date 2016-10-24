package com.web.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.ServiceRoom;
import com.web.example.ServiceRoomExample;
import com.web.mappers.ServiceRoomMapper;
import com.web.service.ServiceRoomService;

/**
 * 机房管理接口
 *
 * @author 田军兴
 * @date 2016-07-30
 */
@Service("serviceRoomService")
@Transactional
public class ServiceRoomServiceImpl implements ServiceRoomService {

	@Autowired
	private ServiceRoomMapper serviceRoomMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(DataDictServiceImpl.class);

	public Page<ServiceRoom> getByPage(int pageCurrent, int count, ServiceRoomExample example) {
		// 分页
		PageHelper.startPage(pageCurrent, count);

		// 查询数据
		List<ServiceRoom> serviceRooms = serviceRoomMapper.selectByExample(example);
		Page<ServiceRoom> pageInfo = new Page<>(serviceRooms);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get data serviceRoom object count: {}", pageInfo.getCount());
		}

		return pageInfo;
	}

	@Override
	public int save(ServiceRoom entity) {
		return serviceRoomMapper.insert(entity);
	}

	@Override
	public int updateById(ServiceRoom entity) {
		return serviceRoomMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int deleteById(String key) {
		return serviceRoomMapper.deleteByPrimaryKey(key);
	}

	@Override
	public ServiceRoom getById(String key) {
		return serviceRoomMapper.selectByPrimaryKey(key);
	}

	@Override
	public List<ServiceRoom> getAll() {
		return null;
	}
}
