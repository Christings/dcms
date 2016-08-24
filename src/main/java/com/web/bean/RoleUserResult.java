package com.web.bean;

import com.web.entity.Role;
import com.web.entity.User;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 根据角色ID 查询角色下用户相关信息
 *
 * @author 杜延雷
 * @date 2016/8/24.
 */
public class RoleUserResult {
    /**
     * 角色对象信息
     */
    private Role role;
    /**
     * 用户集合(和角色建立关系的用户)
     */
    private Set<User> users = new LinkedHashSet<>();

    private Set<UserResult>  records = new LinkedHashSet<>();

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void add(User user){
        this.users.add(user);
    }

    public Set<UserResult> getRecords() {
        return records;
    }

    public void setRecords(Set<UserResult> records) {
        this.records = records;
    }

    public void add(UserResult userResult){
        this.records.add(userResult);
    }
}
