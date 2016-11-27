package com.web.core.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.web.core.dao.IMongoDao;

/**
 * mongoDB DAO层基础类
 *
 * @author 田军兴
 * @date 2016/9/18.
 */
@Repository("mongoDao")
@Transactional
public class MongoDao<T> implements IMongoDao<T> {

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
	 * 保存或更新对象
	 */
	public void saveOrUpdate(T obj, Map params) {
		this.mongoTemplate.remove(this.getQuery(params),obj.getClass());
		this.save(obj);
	}

	public T findTest(Class<T> t,String collectionName){
		Criteria criteria = Criteria.where("cabinets").elemMatch(Criteria.where("resourceCode").is("test233"));
		Query query = new Query();
		query.addCriteria(criteria);
		T entity = this.mongoTemplate.findOne(query,t,collectionName);
		return entity;
	}

	/***
	 * 根据实体查询集合
	 * */
	public T findOne(Class<T> tClass,Map params){
		return mongoTemplate.findOne(this.getQuery(params),tClass);
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

	public void batchSave(List<T> objs, String collectionName){
		for(T obj : objs){
			this.mongoTemplate.remove(obj);
			this.mongoTemplate.save(obj,collectionName);
		}
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

	public T findOne(Map params, Class<T> tClass, String collectionName){
		return this.mongoTemplate.findOne(this.getQuery(params),tClass,collectionName);
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
