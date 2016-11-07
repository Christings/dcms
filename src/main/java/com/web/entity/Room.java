package com.web.entity;

import com.web.core.entity.BaseEntity;

import java.util.Date;

/**
 * 机房管理实体类
 *
 * @author 田军兴
 * @date 2016-07-30
 */
public class Room extends BaseEntity {

	/**
	 * 机房名称
	 */
	private String name;

	/**
	 * 位置
	 */
	private String position;

	/**
	 * 外景
	 */
	private String exterior;

	/**
	 * 资源编码
	 */
	private String resourceCode;

	/**
	 * 机房地址
	 */
	private String address;

	/**
	 * 面积
	 */
	private Double area;

	/**
	 * 备注
	 */
	private String comment;

	/**
	 * 机房图片路径
	 */
	private String imageUrl;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getExterior() {
		return exterior;
	}

	public void setExterior(String exterior) {
		this.exterior = exterior;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
