package com.web.entity;

import com.web.core.entity.BaseEntity;

import java.util.Date;

/**
 * 业务日志实体类
 *
 * @author 田军兴
 * @date 2016-06-29
 */
public class OperLog extends BaseEntity {
	/**
	 * 主键
	 */
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
	private Integer action;

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

	public OperLog() {

	}

	/**
	 */
	public OperLog(String id, String deviceName, String operType, Integer logLevel, Integer action, String operProp, String operUserId,
			String operUserName, Date operDate, String comments) {
		this.id = id;
		this.deviceName = deviceName;
		this.operType = operType;
		this.logLevel = logLevel;
		this.action = action;
		this.operProp = operProp;
		this.operUserId = operUserId;
		this.operUserName = operUserName;
		this.operDate = operDate;
		this.comments = comments;
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

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
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
}