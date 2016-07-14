package com.web.entity;

import com.web.core.entity.BaseEntity;
/**
 * 用户与角色关联实体类
* @ClassName: UserRole 
* @Description: TODO
* @author 童云鹏 
* @date 2016年7月11日 下午1:59:04
 */
public class UserRole extends BaseEntity{

	private String userid;//用户ID
	private String roleid;//角色ID
	private String UserName;
	private String realName;
	private String roleName;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
