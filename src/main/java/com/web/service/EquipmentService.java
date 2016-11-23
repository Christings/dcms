package com.web.service;

import java.util.List;

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
}
