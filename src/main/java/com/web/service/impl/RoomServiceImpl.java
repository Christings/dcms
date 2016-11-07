package com.web.service.impl;

import java.util.List;

import com.web.entity.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.example.RoomExample;
import com.web.mappers.RoomMapper;
import com.web.service.RoomService;

/**
 * 机房管理接口
 *
 * @author 田军兴
 * @date 2016-07-30
 */
@Service("serviceRoomService")
@Transactional
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomMapper roomMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(DataDictServiceImpl.class);

	public Page<Room> getByPage(int pageCurrent, int count, RoomExample example) {
		// 分页
		PageHelper.startPage(pageCurrent, count);

		// 查询数据
		List<Room> rooms = roomMapper.selectByExample(example);
		Page<Room> pageInfo = new Page<>(rooms);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get data serviceRoom object count: {}", pageInfo.getCount());
		}

		return pageInfo;
	}

	@Override
	public List<Room> getByExample(RoomExample example) {
		return roomMapper.selectByExample(example);
	}

	@Override
	public int save(Room entity) {
		return roomMapper.insert(entity);
	}

	@Override
	public int updateById(Room entity) {
		return roomMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int deleteById(String key) {
		return roomMapper.deleteByPrimaryKey(key);
	}

	@Override
	public Room getById(String key) {
		return roomMapper.selectByPrimaryKey(key);
	}

	@Override
	public List<Room> getAll() {
		return roomMapper.selectByExample(new RoomExample());
	}
}
