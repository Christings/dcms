package com.web.entity;

import com.web.core.entity.BaseEntity;

/**
 * 区域管理
 *
 * @author 田军兴
 * @date 2016-11-13
 */
public class Area extends BaseEntity {

	/**
	 * 区域名称
	 */
	private String name;

	/**
	 * 机房ID
	 */
	private String roomId;

	/**
	 * 区域描述
	 */
	private String remark;

	/**
	 * 机柜资源编码
	 */
	private String cabinetResourceCodes;

	/**
	 * 状态
	 */
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCabinetResourceCodes() {
		return cabinetResourceCodes;
	}

	public void setCabinetResourceCodes(String cabinetResourceCodes) {
		this.cabinetResourceCodes = cabinetResourceCodes;
	}
}