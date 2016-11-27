package com.web.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.Category;
import com.web.example.CategoryExample;
import com.web.mappers.CategoryMapper;
import com.web.service.CategoryService;

/**
 * 设备分类管理 逻辑接口 实现类
 *
 * @author 杜延雷
 * @date 2016-11-14
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryMapper categoryMapper;

	@Override
	public int save(Category category) {
		if (null == category) {
			LOGGER.warn("the category object is null.");
			return 0;
		}
		// 插入记录
		int result = categoryMapper.insertSelective(category);
		return result;
	}

	@Override
	public int updateById(Category category) {
		if (null == category) {
			LOGGER.warn("the category object is null.");
			return 0;
		}
		// 更新记录
		int result = categoryMapper.updateByPrimaryKeySelective(category);
		return result;
	}

	@Override
	public int deleteById(String key) {
		if (StringUtils.isEmpty(key)) {
			LOGGER.warn("the key id object is null.");
			return 0;
		}
		// 删除记录数
		int result = categoryMapper.deleteByPrimaryKey(key);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Category getById(String key) {
		if (StringUtils.isEmpty(key)) {
			LOGGER.warn("the category id object is null.");
			return null;
		}
		// 查找实体对象
		Category category = categoryMapper.selectByPrimaryKey(key);
		return category;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Category> getAll() {
		// 查询所有分类
		CategoryExample example = new CategoryExample();
		// 排序
		List<Category> categoryList = categoryMapper.selectByExample(example);
		return categoryList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Category> getByParentId(String parentId) {
		// 查询所有
		CategoryExample example = new CategoryExample();
		CategoryExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isEmpty(parentId)) {
			criteria.andParentIdIsNull();
		} else {
			criteria.andParentIdEqualTo(parentId);
		}
		List<Category> categoryList = categoryMapper.selectByExample(example);
		return categoryList;
	}

	@Override
	public int countByExample(CategoryExample example) {
		if (null == example) {
			return 0;
		}
		return categoryMapper.countByExample(example);
	}

	@Override
	public List<Category> getByExample(CategoryExample example) {
		if (null == example) {
			return null;
		}
		List<Category> list = categoryMapper.selectByExample(example);
		return list;
	}

	/**
	 * 查询返回树形
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Category> getTree(CategoryExample example) {
		if (null == example) {
			return null;
		}
		// 查询数据
		List<Category> categories = categoryMapper.selectByTree(example);
		return categories;
	}

	/**
	 * 分页处理
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param example
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<Category> getScrollData(int pageNum, int pageSize, CategoryExample example) {
		// 分页
		PageHelper.startPage(pageNum, pageSize);

		// 查询数据
		List<Category> categories = categoryMapper.selectByExamplePage(example);
		Page<Category> page = new Page<>(categories);
		return page;
	}

	@Override
	public int deleteCascadeById(String id) {
		if (StringUtils.isEmpty(id)) {
			LOGGER.warn("the key id object is null.");
			return 0;
		}
		// 删除记录数
		int result = categoryMapper.deleteByPrimaryKey(id);
		return result;
	}
}
