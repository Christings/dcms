package com.web.core.mapper;

import java.util.List;

/**
 * Created by tians on 2016/6/15.
 * Mapper基础接口，包括基础的增删改，查询。
 * 子类接口对应的sqlmap中的ID必须和BaseMapper中方法名对应
 */
public interface BaseMapper<T> {
    public int save(T entity);
    public int update(T t);
    public int delete(String id);
    public T selectOneById(String id);
    public List<T> getAll();
}
