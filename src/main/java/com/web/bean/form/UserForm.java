package com.web.bean.form;

/**
 * 接收用户访问数据
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class UserForm extends CommonForm {
    /**
     * 登录名
     */
    private String username;

    /**
     * 真实名
     */
    private String realname;

    /**
     * 密码
     */
    private String password;

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
     * 排序条件（登录名）
     */
    private String usernameSort;

    /**
     * 排序条件（真实名）
     */
    private String realnameSort;

    /**
     * 排序条件（证件号码）
     */
    private String idCardSort;

    /**
     * 排序条件（邮箱）
     */
    private String emailSort;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentificationno() {
        return identificationno;
    }

    public void setIdentificationno(String identificationno) {
        this.identificationno = identificationno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
        this.remark = remark;
    }

    public String getUsernameSort() {
        return usernameSort;
    }

    public void setUsernameSort(String usernameSort) {
        this.usernameSort = usernameSort;
    }

    public String getRealnameSort() {
        return realnameSort;
    }

    public void setRealnameSort(String realnameSort) {
        this.realnameSort = realnameSort;
    }

    public String getIdCardSort() {
        return idCardSort;
    }

    public void setIdCardSort(String idCardSort) {
        this.idCardSort = idCardSort;
    }

    public String getEmailSort() {
        return emailSort;
    }

    public void setEmailSort(String emailSort) {
        this.emailSort = emailSort;
    }
}
