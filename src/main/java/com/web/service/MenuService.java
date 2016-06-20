package com.web.service;

import com.web.entity.Menu;

import java.util.List;

/**
 * 菜单 逻辑接口
 * @author 杜延雷
 * @date 2016-06-20
 */
public interface MenuService extends IService<Menu, String> {

    /**
     * 根据父ID查询 菜单 key 为空时 查询的是一级菜单
     * @param key
     * @return
     */
    List<Menu> getByParentId(String key);
}
