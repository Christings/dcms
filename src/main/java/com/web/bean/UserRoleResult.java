package com.web.bean;

import com.web.entity.Role;
import com.web.entity.User;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 根据用户ID  查询用户所有相关角色信息
 *
 * @author 杜延雷
 * @date 2016/8/24.
 */
public class UserRoleResult {
    /**
     * 用户对象
     */
    private User user;

    /**
     * 角色集合
     */
    private Set<Role> roles = new LinkedHashSet();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void add(Role role){
        this.roles.add(role);
    }
}
