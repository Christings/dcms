package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.ProductCategory;
import com.web.example.ProductCategoryExample;

import java.util.List;

/**
 * 设备分类管理 逻辑接口
 *
 * @author 杜延雷
 * @date 2016-11-14
 */
public interface ProductCategoryService extends IService<ProductCategory, String> {

    /**
     * 根据父ID查询 所有子集
     * @param parentId
     * @return
     */
    List<ProductCategory> getByParentId(String parentId);

    /**
     * 根据条件查询是否存在相关数据
     * @param example
     * @return
     */
    int countByExample(ProductCategoryExample example);

    /**
     * 根据条件查询所有的分类
     * @param example
     * @return
     */
    List<ProductCategory> getByExample(ProductCategoryExample example);

    /**
     * 根据条件返回 分类树集合
     * @param example
     * @return
     */
    List<ProductCategory> getTree(ProductCategoryExample example);

    /**
     * 分页处理 根据查询条件进行分页
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    Page<ProductCategory> getScrollData(int pageNum, int pageSize, ProductCategoryExample example);

    /**
     * 级联删除子类
     * @param id
     * @return
     */
    int deleteCascadeById(String id);
}
