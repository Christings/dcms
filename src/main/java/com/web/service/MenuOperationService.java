package com.web.service;

import com.web.entity.MenuOperation;

import java.util.List;

/**
 * 菜单-->操作 逻辑接口
 * @author 杜延雷
 * @date 2016-09-11
 */
public interface MenuOperationService extends IService<MenuOperation, String> {

    /**
     * 根据菜单ID 查询菜单下的所有操作
     * @param menuId
     * @return
     */
    List<MenuOperation> getByMenuId(String menuId);
}
