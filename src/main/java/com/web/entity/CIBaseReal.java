package com.web.entity;

import java.util.Date;

/**
 * Created by tians on 2016/10/8.
 */
public class CIBaseReal {

    /**
     *CI类别名称
     */
    private String category;

    /**
     * 分类ID
     * */
    private String catId;

    /**
     * 配置项名称
     * */
    private String ciName;

    /**
     * 配置项中文名称描述
     * */
    private String ciNameCN;

    /**
     * 简要描述
     * */
    private String ciDesc;

    /**
     * 状态
     * */
    private String ciStatus;

    /**
     * 责任人姓名
     * */
    private  String owner;

    /**
     * 维护状态
     * */
    private String maintenance;

    /**
     * 购买日期
     * */
    private Date orderedTime;

    /**
     * 入库日期
     * */
    private Date stockTime;

    /**
     * 准备日期
     * */
    private Date preTime;

    /**
     * 上线正式运行日期
     * */
    private Date operatedTime;

    /**
     * 维修日期
     * */
    private Date maintenancedTime;

    /**
     * 报废日期
     * */
    private Date disposalTime;

    /**
     * 丢失日期
     * */
    private Date lostTime;

    /**
     * 活动标志
     * */
    private String lifeFlag;

    /**
     * 服务开始日期
     * */
    private  Date srvStart;

    /**
     * 服务结束日期
     * */
    private  Date srvEnd;

    /**
     * 服务级别
     * */
    private  String srvLevel;

    /**
     * 审核状态
     * */
    private  String auditStatus;

    /**
     * 审核日期
     * */
    private  Date auditTime;

    /**
     * 审核结果
     * */
    private  String auditCode;

    private String selfProp;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCiName() {
        return ciName;
    }

    public void setCiName(String ciName) {
        this.ciName = ciName;
    }

    public String getCiNameCN() {
        return ciNameCN;
    }

    public void setCiNameCN(String ciNameCN) {
        this.ciNameCN = ciNameCN;
    }

    public String getCiDesc() {
        return ciDesc;
    }

    public void setCiDesc(String ciDesc) {
        this.ciDesc = ciDesc;
    }

    public String getCiStatus() {
        return ciStatus;
    }

    public void setCiStatus(String ciStatus) {
        this.ciStatus = ciStatus;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public Date getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(Date orderedTime) {
        this.orderedTime = orderedTime;
    }

    public Date getStockTime() {
        return stockTime;
    }

    public void setStockTime(Date stockTime) {
        this.stockTime = stockTime;
    }

    public Date getPreTime() {
        return preTime;
    }

    public void setPreTime(Date preTime) {
        this.preTime = preTime;
    }

    public Date getOperatedTime() {
        return operatedTime;
    }

    public void setOperatedTime(Date operatedTime) {
        this.operatedTime = operatedTime;
    }

    public Date getMaintenancedTime() {
        return maintenancedTime;
    }

    public void setMaintenancedTime(Date maintenancedTime) {
        this.maintenancedTime = maintenancedTime;
    }

    public Date getDisposalTime() {
        return disposalTime;
    }

    public void setDisposalTime(Date disposalTime) {
        this.disposalTime = disposalTime;
    }

    public Date getLostTime() {
        return lostTime;
    }

    public void setLostTime(Date lostTime) {
        this.lostTime = lostTime;
    }

    public String getLifeFlag() {
        return lifeFlag;
    }

    public void setLifeFlag(String lifeFlag) {
        this.lifeFlag = lifeFlag;
    }

    public Date getSrvStart() {
        return srvStart;
    }

    public void setSrvStart(Date srvStart) {
        this.srvStart = srvStart;
    }

    public Date getSrvEnd() {
        return srvEnd;
    }

    public void setSrvEnd(Date srvEnd) {
        this.srvEnd = srvEnd;
    }

    public String getSrvLevel() {
        return srvLevel;
    }

    public void setSrvLevel(String srvLevel) {
        this.srvLevel = srvLevel;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditCode() {
        return auditCode;
    }

    public void setAuditCode(String auditCode) {
        this.auditCode = auditCode;
    }

    public String getSelfProp() {
        return selfProp;
    }

    public void setSelfProp(String selfProp) {
        this.selfProp = selfProp;
    }
}
