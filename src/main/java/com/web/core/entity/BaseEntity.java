package com.web.core.entity;

import java.util.Date;

public class BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 用户DI **/
	private String id;
	/** 修改人名 **/
	private String updateName;
	/** 修改时间 **/
	private String updateCreate;
	/** 创建人名 **/
	private String createName;
	/** 创建时间 **/
	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateCreate() {
		return updateCreate;
	}

	public void setUpdateCreate(String updateCreate) {
		this.updateCreate = updateCreate;
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
