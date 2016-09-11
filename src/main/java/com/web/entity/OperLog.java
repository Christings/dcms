package com.web.entity;

import java.util.Date;

/**
 * 业务日志实体类
 *
 * @author 田军兴
 * @date 2016-06-29
 */
public class OperLog {

	private String id;

	/**
	 * 设备名称
	 */
	private String deviceName;

	/**
	 * 操作类型 增删改查
	 */
	private String operType;

	/**
	 * 日志级别 成功或失败
	 */
	private Integer logLevel;

	/**
	 * 行为
	 */
	private String actionType;

	/**
	 */
	private String operProp;

	/**
	 * 操作人ID
	 */
	private String operUserId;

	/**
	 * 操作人姓名
	 */
	private String operUserName;

	/**
	 */
	private Date operDate;

	/**
	 * 备注
	 */
	private String comments;

	/**
	 * 日志类型 0:系统日志 1:业务日志
	 */
	private int logType;

	public enum operTypeEnum {
		select, insert, update, delete
	}

	public enum logLevelEnum {
		success, error
	}

	public enum actionSystemEnum {
		user, role, menu, menuOperation, dataDic, login, logout, log, menuRole, userRole, fixedEquipment
	}

	public enum actionBusinessEnum {
		cabinet, device, jumper, cabinetPro, devicePro, serviceRoom
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public Integer getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getOperProp() {
		return operProp;
	}

	public void setOperProp(String operProp) {
		this.operProp = operProp;
	}

	public String getOperUserId() {
		return operUserId;
	}

	public void setOperUserId(String operUserId) {
		this.operUserId = operUserId;
	}

	public String getOperUserName() {
		return operUserName;
	}

	public void setOperUserName(String operUserName) {
		this.operUserName = operUserName;
	}

	public Date getOperDate() {
		return operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}
}