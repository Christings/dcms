package com.web.entity;

import com.web.core.entity.BaseEntity;

import java.util.Date;

/**
 * 机房平面图管理实体类
 *
 * @author 田军兴
 * @date 2016-010-22
 */
public class RoomIcngph extends BaseEntity {

	/**
	 * 楼层名称
	 * */
	private String floorName;

	/**
	 * 图片名称
	 * */
	private String imageName;

	/**
	 * 图片路径
	 * */
	private String imageRealPath;

	/**
	 * JSON文件名称
	 * */
	private String jsonName;

	/**
	 * JSON文件路径
	 * */
	private String jsonRealPath;

	/**
	 * YML文件名称
	 * */
	private String ymlName;

	/**
	 * YML文件路径
	 * */
	private String ymlRealPath;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageRealPath() {
		return imageRealPath;
	}

	public void setImageRealPath(String imageRealPath) {
		this.imageRealPath = imageRealPath;
	}

	public String getJsonName() {
		return jsonName;
	}

	public void setJsonName(String jsonName) {
		this.jsonName = jsonName;
	}

	public String getJsonRealPath() {
		return jsonRealPath;
	}

	public void setJsonRealPath(String jsonRealPath) {
		this.jsonRealPath = jsonRealPath;
	}

	public String getYmlName() {
		return ymlName;
	}

	public void setYmlName(String ymlName) {
		this.ymlName = ymlName;
	}

	public String getYmlRealPath() {
		return ymlRealPath;
	}

	public void setYmlRealPath(String ymlRealPath) {
		this.ymlRealPath = ymlRealPath;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
}
