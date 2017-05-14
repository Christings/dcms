package com.web.service;

import com.web.entity.UserRole;
import com.web.example.UserRoleExample;

import java.util.List;

/**
 * 用户角色关系  逻辑接口
 *
 * @author 杜延雷
 * @date 2016-08-11
 */
public interface UserRoleService extends IService<UserRole,String> {
    /**
     * 根据条件查询数量
     */
    int getCount(UserRoleExample example);

    /**
     * 根据条件删除
     * @param example
     * @return
     */
    int delete(UserRoleExample example);
    /**
     * 根据角色ID 查询角色与用户关系数据
     *
     * @return 返回所有实体对象的集合
     */
    List<UserRole> getRoleUser(String roleId) ;

    /**
     * 根据用户ID 查询用户与角色关系数据
     *
     * @return 返回所有实体对象的集合
     */
    List<UserRole> getUserRole(String userId) ;

    /**
     * 批量根据角色保存用户
     *
     * @return
     */
    void batchRoleUser(String roleId, String[] userIds);

    /**
     * 批量根据用户保存角色
     *
     * @return
     */
    void batchUserRole(String userId, String[] roleIds);
}
