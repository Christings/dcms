package com.web.bean.form;

/**
 * 接收设备型号访问数据
 *
 * @author 杜延雷
 * @date 2016/11/15.
 */
public class ProductForm extends CommonForm {
    /**
     * 设备类型ID
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
        this.extra = extra;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
