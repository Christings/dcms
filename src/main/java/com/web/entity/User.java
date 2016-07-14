package com.web.entity;

import com.web.core.entity.BaseEntity;


/**
 * 用户实体类
* @ClassName: User 
* @Description: TODO
* @author 童云鹏 
* @date 2016年6月29日 上午10:26:23 
*/
public class User  extends BaseEntity{
	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -5268511121173326164L;
	//private String id;//主键ID
	private String userName;//用户姓名
	private String realName;//登录账号
	private String password;//登录密码
	private String identificationNo;//身份证号
	private String mobile;//手机号
	private Integer sex;//性别
	private Integer enabled;//是否启用：0 启用 ，1是不启用
	//private String creatorId;// 创建人ID
	//private String createTime;//创建时间
	private String remark;//备注
	private Integer deleted;//是否逻辑删除：0 未删除 ，1 逻辑删除
	private Integer superAdmin;//是否超级管理员 ：0 不是，1 是
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Integer getSuperAdmin() {
		return superAdmin;
	}
	public void setSuperAdmin(Integer superAdmin) {
		this.superAdmin = superAdmin;
	}
	

	

}