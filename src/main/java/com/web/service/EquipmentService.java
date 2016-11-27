package com.web.service;

import java.util.List;

import com.web.bean.result.EquipmentResult;
import com.web.core.util.page.Page;
import com.web.entity.Equipment;

/**
 * 设备操作类
 * 
 * @author qgl
 * 
 */
public interface EquipmentService {
	public List<Equipment> findAll();

	public int insert(Equipment equipment);

	public List<EquipmentResult> selectForBackImage(Equipment equipment);

	public Page<EquipmentResult> selectForBackImagePage(int pageCurrent, int count, Equipment equipment);
}
