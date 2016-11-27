package com.web.entity;

import com.web.core.entity.BaseEntity;

/**
 * 设备型号实体对象
 *
 * @author 杜延雷
 * @date 2016-11-14
 */
public class Product extends BaseEntity {
	/**
	 * 型号名称
	 */
	private String name;
	/**
	 * 设备分类
	 */
	private Category category;
	/**
	 * 生产厂商
	 */
	private String brand;
	/**
	 * 高度U（模型中包括）
	 */
	private Integer height;
	/**
	 * 重量（模型中包括）
	 */
	private Float weight;
	/**
	 * 额定功率（模型中包括）
	 */
	private Float power;
	/**
	 * 前视图名称
	 */
	private String frontImgName;
	/**
	 * 前视图宽度
	 */
	private Integer frontImgWidth;
	/**
	 * 前视图高度
	 */
	private Integer frontImgHeight;
	/**
	 * 后视图名称
	 */
	private String backImgName;
	/**
	 * 后视图宽度
	 */
	private Integer backImgWidth;
	/**
	 * 后视图高度
	 */
	private Integer backImgHeight;
	/**
	 * 状态（取值与业务含义未知）
	 */
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand == null ? null : brand.trim();
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getPower() {
		return power;
	}

	public void setPower(Float power) {
		this.power = power;
	}

	public String getFrontImgName() {
		return frontImgName;
	}

	public void setFrontImgName(String frontImgName) {
		this.frontImgName = frontImgName;
	}

	public Integer getFrontImgWidth() {
		return frontImgWidth;
	}

	public void setFrontImgWidth(Integer frontImgWidth) {
		this.frontImgWidth = frontImgWidth;
	}

	public Integer getFrontImgHeight() {
		return frontImgHeight;
	}

	public void setFrontImgHeight(Integer frontImgHeight) {
		this.frontImgHeight = frontImgHeight;
	}

	public String getBackImgName() {
		return backImgName;
	}

	public void setBackImgName(String backImgName) {
		this.backImgName = backImgName;
	}

	public Integer getBackImgWidth() {
		return backImgWidth;
	}

	public void setBackImgWidth(Integer backImgWidth) {
		this.backImgWidth = backImgWidth;
	}

	public Integer getBackImgHeight() {
		return backImgHeight;
	}

	public void setBackImgHeight(Integer backImgHeight) {
		this.backImgHeight = backImgHeight;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}