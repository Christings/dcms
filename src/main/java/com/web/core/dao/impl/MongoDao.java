package com.web.core.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.web.core.dao.IMongoDao;

/**
 * mongoDB DAO层基础类
 *
 * @author 田军兴
 * @date 2016/9/18.
 */
public class MongoDao<T, E> implements IMongoDao {

	/**
	 * 初始化Log4j的一个实例
	 */
	private static final Logger logger = Logger.getLogger(MongoDao.class);
	/**
	 * 注入mongoTemplate
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 保存对象 默认取对象的类名称作为集合名称保存，切对象名称将转换为小写
	 */
	public void save(T obj) {
		this.mongoTemplate.save(obj);
	}

	/**
	 * 保存对象
	 * 
	 * @param collectionName
	 *            集合名称
	 */
	public void save(T obj, String collectionName) {
		this.mongoTemplate.save(obj, collectionName);
	}

	/**
	 * 删除对象
	 */
	public void remove(T obj) {
		this.mongoTemplate.remove(obj);
	}

	/**
	 * 查询条数
	 */
	public long count(Map<String, Object> params, String collectionName) {

		return this.mongoTemplate.count(getQuery(params), collectionName);
	}

	/**
	 * 分页查询（待完善）
	 */
	public List<T> getPage(int currentPage, int limit, Map<String, Object> params) {
		Query query = new Query();
		query.skip(currentPage);
		query.limit(limit);
		return (List) this.mongoTemplate.find(getQuery(params), this.getClass());
	}

	private Query getQuery(Map<String, Object> params) {
		Query query = new Query();
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.addCriteria(new Criteria(key).is(params.get(key)));
			}
		}
		return query;
	}
}
