package com.web.entity;

import com.web.core.entity.BaseEntity;

/**
 * 设备型号实体对象
 *
 * @author 杜延雷
 * @date 2016-11-14
 */
public class Product extends BaseEntity{
    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备型号
     */
    private String typeId;

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
     * 包含模型文件说明，设备面板图片说明 扩展字段
     */
    private String extra;

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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}