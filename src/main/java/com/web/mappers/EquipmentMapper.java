package com.web.mappers;

import java.util.List;

import com.web.entity.Equipment;

public interface EquipmentMapper {

	public List<Equipment> findAll();

	public int insert(Equipment equipment);

}