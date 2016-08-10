package com.web.entity;

import com.web.core.entity.BaseEntity;

/**
 * 角色实体对象
 *
 * @author 杜延雷
 * @date 2016-08-10
 */
public class Role extends BaseEntity{
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
        this.rolecode = rolecode == null ? null : rolecode.trim();
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }
}