package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.Type;
import com.web.example.TypeExample;

import java.util.List;

/**
 * 分类管理 逻辑接口
 *
 * @author 杜延雷
 * @date 2016-11-07
 */
public interface TypeService extends IService<Type, String> {

    /**
     * 根据父ID查询 所有子集
     * @param key
     * @return
     */
    List<Type> getByParentId(String key);

    /**
     * 根据条件查询是否存在相关数据
     * @param example
     * @return
     */
    int countByExample(TypeExample example);

    /**
     * 根据条件查询所有的分类
     * @param example
     * @return
     */
    List<Type> getByExample(TypeExample example);

    /**
     * 根据条件返回 分类树集合
     * @param example
     * @return
     */
    List<Type> getTree(TypeExample example);

    /**
     * 分页处理 根据查询条件进行分页
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    Page<Type> getScrollData(int pageNum, int pageSize, TypeExample example);

    /**
     * 级联删除子类
     * @param id
     * @return
     */
    int deleteCascadeById(String id);
}
