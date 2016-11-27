package com.web.bean.result;

import java.util.Date;

import com.web.entity.Product;

/**
 * 设备信息结果bean
 *
 * @author 田军兴
 * 2016-11-25
 */
public class EquipmentResult {
    // 资源名称
    private String name;
    // 资源编码，必须满足唯一性
    private String serial;
    // 射频码，接入射频管理时使用
    private String rfid;
    // 业务名称
    private String biz;
    // 供应商id
    private String supplierId;
    // 设备型号id
    private String productId;
    // 所属机房ID
    private String roomId;
    // 设备类型
    private Integer type;
    // 投运时间
    private Date workTime;
    // 维保合同到期时间
    private Date warrantyTime;
    // 扩展字段，键值对方式存储扩展属性
    private String extra;
    // 可以存扩展属性
    private String remark;
    //
    private Integer flags;
    // 状态
    private Integer status;

    private Product product;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public Date getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(Date warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
