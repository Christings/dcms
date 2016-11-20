package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.Category;
import com.web.example.CategoryExample;

import java.util.List;

/**
 * 设备分类管理 逻辑接口
 *
 * @author 杜延雷
 * @date 2016-11-14
 */
public interface CategoryService extends IService<Category, String> {

    /**
     * 根据父ID查询 所有子集
     * @param parentId
     * @return
     */
    List<Category> getByParentId(String parentId);

    /**
     * 根据条件查询是否存在相关数据
     * @param example
     * @return
     */
    int countByExample(CategoryExample example);

    /**
     * 根据条件查询所有的分类
     * @param example
     * @return
     */
    List<Category> getByExample(CategoryExample example);

    /**
     * 根据条件返回 分类树集合
     * @param example
     * @return
     */
    List<Category> getTree(CategoryExample example);

    /**
     * 分页处理 根据查询条件进行分页
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    Page<Category> getScrollData(int pageNum, int pageSize, CategoryExample example);

    /**
     * 级联删除子类
     * @param id
     * @return
     */
    int deleteCascadeById(String id);
}
