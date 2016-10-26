package com.web.bean.result;

import com.web.entity.DoMain;
import com.web.entity.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
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
    private Set<RoleResult> roles = new HashSet<>();

    /**
     * 组织结构ID
     */
    private Set<DoMainResult> domains = new HashSet<>();

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


    public Set<RoleResult> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleResult> roles) {
        this.roles = roles;
    }

    public Set<DoMainResult> getDomains() {
        return domains;
    }

    public void setDomains(Set<DoMainResult> domains) {
        this.domains = domains;
    }

    public void addRole(Role role){
        if(null != role){
            RoleResult rr = new RoleResult();
            BeanUtils.copyProperties(role, rr);
            this.roles.add(rr);
        }
    }

    public void addRoles(Collection<Role> collection){
        if (!CollectionUtils.isEmpty(collection)) {
            for (Role role : collection) {
                this.addRole(role);
            }
        }
    }

    public void addDomain(DoMain doMain){
        if(null != doMain){
            DoMainResult dmr = new DoMainResult();
            BeanUtils.copyProperties(doMain, dmr);
            this.domains.add(dmr);
        }
    }

    public void addDomains(Collection<DoMain> collection){
        if (!CollectionUtils.isEmpty(collection)) {
            for (DoMain doMain : collection) {
                this.addDomain(doMain);
            }
        }
    }
}