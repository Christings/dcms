package com.web.core.dao;

import java.util.List;
import java.util.Map;

/**
 * mongoDB DAO层基础类
 *
 * @author 田军兴
 * @date 2016/9/18.
 */
public interface IMongoDao<T> {
	public void save(T obj, String collectionName);

	public void save(T obj);

	public void remove(T obj);

	public long count(Map<String, Object> params, String collectionName);

	public List<T> getPage(int currentPage, int limit, Map<String, Object> params);

}
