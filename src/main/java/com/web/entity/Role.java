package com.web.entity;

import com.web.core.entity.BaseEntity;
/**
 * 角色实体类
 * @author think
 *
 */
public class Role extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4534815857132405090L;
	private String roleCode;//角色编码
	private String roleName;//角色名称
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
	
	

}
