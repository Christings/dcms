package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.Menu;
import com.web.example.MenuExample;

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

    /**
     * 分页处理 根据查询条件进行分页
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    Page<Menu> getScrollData(int pageNum, int pageSize, MenuExample example);
}
