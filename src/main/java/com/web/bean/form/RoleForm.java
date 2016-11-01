package com.web.bean.form;

/**
 * 接收角色访问数据
 *
 * @author 杜延雷
 * @date 2016/11/1.
 */
public class RoleForm extends CommonForm {
    /**
     * 角色编码
     */
    private String rolecode;

    /**
     * 角色名
     */
    private String rolename;

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
