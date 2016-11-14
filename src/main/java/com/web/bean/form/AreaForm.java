package com.web.bean.form;

/**
 * 区域管理
 *
 * @author 田军兴
 * @date 2016-11-13
 */
public class AreaForm extends CommonForm {
	// 排序设置
	private String orderByClause;

	/**
	 * 区域ID
	 */
	private String id;
	/**
	 * 区域名称
	 */
	private String name;

	/**
	 * 机房ID
	 */
	private String roomId;

	/**
	 * 机房名称
	 */
	private String roomName;

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
}
