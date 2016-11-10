package com.web.entity;

import com.web.core.entity.BaseEntity;

public class BoxEquipment extends BaseEntity {

	private String equipmentId;

	private String cabinetId;

	private Byte pos;

	private Boolean direction;

	private Integer confirmed;

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public Byte getPos() {
		return pos;
	}

	public void setPos(Byte pos) {
		this.pos = pos;
	}

	public Boolean getDirection() {
		return direction;
	}

	public void setDirection(Boolean direction) {
		this.direction = direction;
	}

	public Integer getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}
}