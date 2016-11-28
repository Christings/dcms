package com.web.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.web.bean.result.EquipmentResult;
import com.web.core.util.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.entity.Equipment;
import com.web.mappers.EquipmentMapper;
import com.web.service.EquipmentService;

/**
 * 设备操作类
 * 
 * @author qgl
 * 
 */
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
	@Autowired
	private EquipmentMapper equipmentMapper;

	@Override
	public List<Equipment> findAll() {
		return equipmentMapper.findAll();
	}

	@Override
	public int insert(Equipment equipment) {
		return equipmentMapper.insert(equipment);
	}

	@Override
	public List<EquipmentResult> selectForBackImage(Equipment equipment){
		return equipmentMapper.selectForBackImage(equipment);
	}

	@Override
	public Page<EquipmentResult> selectForBackImagePage(int pageCurrent, int count, Equipment equipment) {
		PageHelper.startPage(pageCurrent, count);
		List<EquipmentResult> equipmentResults = equipmentMapper.selectForBackImage(equipment);
		Page<EquipmentResult> page = new Page<>(equipmentResults);
		return page;
	}
}
