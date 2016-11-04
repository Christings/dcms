package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.Menu;
import com.web.example.MenuExample;

import java.util.List;
import java.util.Map;

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

    /**
     * 分页处理 根据查询条件进行分页
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    Page<Menu> getScrollData(int pageNum, int pageSize, MenuExample example);

    /**
     * 根据条件返回 菜单树集合
     * @param params
     * @return
     */
    List<Menu> getTree(Map<String,String> params);

    /**
     * 关联关系删除菜单
     */
    int deleteCascadeById(String menuId);
}
