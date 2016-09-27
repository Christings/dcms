package com.web.bean.result;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户(用来返回前端的类)
 *
 * @author 杜延雷
 * @date 2016-08-24
 */
public class UserResult {

    private String id;

    /**
     * 登录名
     */
    private String username;

    /**
     * 真实名
     */
    private String realname;

    /**
     * 证件号码（身份证）
     */
    private String identificationno;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 座机号
     */
    private String mobile;

    /**
     * 性别
     */
    private Short sex;

    /**
     * 状态
     */
    private Short status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否选中
     */
    private int checked;

    /**
     * 角色ID集合
     */
    private Set<String> roleIds = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getIdentificationno() {
        return identificationno;
    }

    public void setIdentificationno(String identificationno) {
        this.identificationno = identificationno == null ? null : identificationno.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public Set<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<String> roleIds) {
        this.roleIds = roleIds;
    }

    public void addRoleIds(String roleId){
        if(StringUtils.isNotEmpty(roleId)){
            this.roleIds.add(roleId);
        }
    }
}