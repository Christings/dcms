package com.web.bean.form;

import java.util.Date;

/**
 * 接收操作日志访问数据
 *
 * @author 田军兴
 * @date 2016/10/14
 */
public class OperLogForm extends CommonForm {

	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 数据操作类型
	 */
	private String operType;
	/**
	 * 操作类型（0：失败 1：成功）
	 */
	private Integer logLevel;
	/**
	 * 操作内容
	 */
	private String actionType;
	/**
	 * 操作人
	 */
	private String operUserName;
	/**
	 * 操作开始日期
	 */
	private Date beginOperDate;
	/**
	 * 操作开始日期
	 */
	private Date endOperDate;
	/**
	 * 日类类型（0：系统日志 1：业务日志）
	 */
	private Integer logType;

	/**
	 * 备注
	 */
	private String comments;


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

	public String getOperUserName() {
		return operUserName;
	}

	public void setOperUserName(String operUserName) {
		this.operUserName = operUserName;
	}

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getEndOperDate() {
		return endOperDate;
	}

	public void setEndOperDate(Date endOperDate) {
		this.endOperDate = endOperDate;
	}

	public Date getBeginOperDate() {
		return beginOperDate;
	}

	public void setBeginOperDate(Date beginOperDate) {
		this.beginOperDate = beginOperDate;
	}
}
