package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.DoMain;
import com.web.example.DoMainExample;

import java.util.List;
import java.util.Map;

/**
 * 域管理（组织结构） 逻辑接口
 *
 * @author 杜延雷
 * @date 2016-10-22
 */
public interface DoMainService extends IService<DoMain, String> {
    /**
     * 查询数量
     */
    int countByExample(DoMainExample example);

    /**
     * 根据父ID查询 域 key 为空时 查询的是一级域
     * @param key
     * @return
     */
    List<DoMain> getByParentId(String key);

    /**
     * 分页处理 根据查询条件进行分页
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    Page<DoMain> getScrollData(int pageNum, int pageSize, DoMainExample example);

    /**
     * 根据条件返回 域返回树集合
     * @param params
     * @return
     */
    List<DoMain> getTree(Map<String,String> params);
}
