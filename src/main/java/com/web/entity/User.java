package com.web.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户实体类
* @ClassName: User 
* @Description: TODO
* @author 童云鹏 
* @date 2016年6月29日 上午10:26:23 
*/
public class User  {
	private String id;//主键ID
	private String userName;//用户姓名
	private String account;//登录账号
	private String password;//登录密码
	private String identificationNo;//身份证号
	private String mobile;//手机号
	private String sex;//性别
	private String enabled;//是否启用：0 启用 ，1是不启用
	private String creatorId;// 创建人ID
	private String createTime;//创建时间
	private String remark;//备注
	private String deleted;//是否逻辑删除：0 未删除 ，1 逻辑删除
	private String superAdmin;//是否超级管理员 ：0 不是，1 是
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdentificationNo() {
		return identificationNo;
	}
	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getSuperAdmin() {
		return superAdmin;
	}
	public void setSuperAdmin(String superAdmin) {
		this.superAdmin = superAdmin;
	}

	

}