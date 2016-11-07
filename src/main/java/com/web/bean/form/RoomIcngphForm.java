package com.web.bean.form;

/**
 * 接收机房平面图管理访问数据
 *
 * @author 田军兴
 * @date 2016/10/23.
 */
public class RoomIcngphForm extends CommonForm {

	/**
	 * 楼层名称
	 */
	private String floorName;
	/**
	 * 附件名称
	 */
	private String fileName;

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
