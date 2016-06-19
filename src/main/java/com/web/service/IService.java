package com.web.service;

import java.util.List;

/**
 * 业务逻辑抽象接口  业务接口继承统一服务接口
 * @param <T> 实体类类型
 * @param <E> 实体类主键类型
 * @author 杜延雷
 * @date 2016/6/20.
 */
public interface IService<T, E> {
    /**
     * 保存实体对象
     * @param entity 要保存的实体对象
     * @return 影响记录条数
     */
    int save(T entity) ;

    /**
     * 根据实体的id更新实体信息
     * @param entity 实体对象
     * @return 影响记录条数
     */
    int updateById(T entity) ;

    /**
     * 根据主键删除对应的实体对象
     * @param key 主键值
     * @return 影响记录条数
     */
    int deleteById(E key) ;

    /**
     * 根据组件查找实体
     * @param key 主键值
     * @return 找到的实体对象，否者返回null
     */
    T getById(E key) ;

    /**
     * 获取所有实体对象
     * @return 返回所有实体对象的集合
     */
    List<T> getAll() ;
}
