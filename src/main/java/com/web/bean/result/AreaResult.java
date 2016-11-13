package com.web.bean.result;

/**
 * 区域管理前台显示
 *
 * @author 田军兴
 * @date 2016-11-13
 */
public class AreaResult {

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

	/**
	 * 备注
	 */
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
