package com.web.service;

import com.web.entity.MenuRole;

import java.util.List;

/**
 * 菜单角色关系 逻辑接口
 *
 * @author 杜延雷
 * @date 2016-07-12
 */
public interface MenuRoleService extends IService<MenuRole, String> {
    /**
     * 根据查询条件返回List列表
     */
    List<MenuRole> getRoleMenuOperation(String roleId,String menuId);
    /**
     * 根据角色ID 查询角色与菜单关系数据
     *
     * @return 返回所有实体对象的集合
     */
    List<MenuRole> getRoleMenu(String roleId) ;

    /**
     * 根据菜单ID 查询菜单与角色关系数据
     *
     * @return 返回所有实体对象的集合
     */
    List<MenuRole> getMenuRole(String menuId) ;

    /**
     * 批量根据角色保存菜单
     *
     * @return
     */
    void batchRoleMenu(String roleId,String[] menuIds);

    /**
     * 批量根据菜单保存角色
     *
     * @return
     */
    void batchMenuRole(String menuId,String[] roleIds);

}
