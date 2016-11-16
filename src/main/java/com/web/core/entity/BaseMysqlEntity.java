package com.web.core.entity;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseMysqlEntity<ID extends Serializable> implements Serializable {

	/** 主键ID **/
	private ID id;
	/** 修改人名 **/
	private String updateName;
	/** 修改时间 **/
	private Date updateDate;
	/** 创建人名 **/
	private String createName;
	/** 创建时间 **/
	private Date createDate;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
