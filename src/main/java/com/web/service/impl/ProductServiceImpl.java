package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.Product;
import com.web.example.ProductExample;
import com.web.mappers.ProductMapper;
import com.web.service.ProductService;
import com.web.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 设备型号 逻辑接口 实现
 *
 * @author 杜延雷
 * @date 2016-11-14
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductMapper productMapper; //Service

	@Override
	public int save(Product product) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("save Product: {}", JSON.toJSONString(product));
		}

		if (null == product) {
			LOGGER.warn("the Product object is null.");
			return 0 ;
		}

		if(null == product.getId() || "".equals(product.getId())){
			product.setId(UUIDGenerator.generatorRandomUUID());
		}
		// 插入记录
		int result = productMapper.insertSelective(product) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("save Product object result: {}", result);
		}

		return result;
	}

	@Override
	public int updateById(Product product) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("update Product: {}", JSON.toJSONString(product));
		}

		if (null == product) {
			LOGGER.warn("the Product object is null.");
			return 0 ;
		}

		// 更新记录
		int result = productMapper.updateByPrimaryKeySelective(product) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("update Product object result: {}", result);
		}

		return result;
	}

	@Override
	public int deleteById(String id) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("delete id by id: {}", id);
		}

		if (StringUtils.isEmpty(id)) {
			LOGGER.warn("the id object is null.");
			return 0 ;
		}

		// 删除记录数
		int result = productMapper.deleteByPrimaryKey(id) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("delete product object by id result: {}", result);
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public Product getById(String id) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("find product by id: {}", id);
		}

		if (StringUtils.isEmpty(id)) {
			LOGGER.warn("the product id object is null.");
			return null ;
		}

		// 查找实体对象
		Product product = productMapper.selectByPrimaryKey(id) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("find product object by id result: {}", JSON.toJSONString(product));
		}

		return product;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public List<Product> getAll() {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get all products");
		}

		//查询所有用户
		ProductExample example = new ProductExample();
		//排序
		example.setOrderByClause("create_date desc,id asc");
		List<Product> products = productMapper.selectByExample(example);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get products all object count: {}", products.size());
		}

		return products;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public int count(ProductExample example) {
		return productMapper.countByExample(example);
	}

	/**
	 * 分页处理
	 * @param pageNum
	 * @param pageSize
	 * @param example
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public Page<Product> getScrollData(int pageNum, int pageSize, ProductExample example) {
		// 分页
		PageHelper.startPage(pageNum, pageSize) ;

		// 查询数据
		List<Product> products = productMapper.selectByExample(example);
		Page<Product> page = new Page<>(products) ;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get products scroll object count: {}", page.getCount());
		}

		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public List<Product> getExample(ProductExample example) {
		// 查询数据
		List<Product> products = productMapper.selectByExample(example);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get products scroll object size: {}", products.size());
		}

		return products;
	}
}
