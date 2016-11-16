package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.ProductCategory;
import com.web.example.ProductCategoryExample;
import com.web.mappers.ProductCategoryMapper;
import com.web.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 设备分类管理 逻辑接口 实现类
 *
 * @author 杜延雷
 * @date 2016-11-14
 */
@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServiceImpl.class) ;

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Override
    public int save(ProductCategory productCategory) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save ProductCategory: {}", JSON.toJSONString(productCategory));
        }

        if (null == productCategory) {
            LOGGER.warn("the productCategory object is null.");
            return 0 ;
        }

        // 插入记录
        int result = productCategoryMapper.insertSelective(productCategory) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save ProductCategory object result: {}", result);
        }

        return result;
    }

    @Override
    public int updateById(ProductCategory productCategory) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update ProductCategory: {}", JSON.toJSONString(productCategory));
        }

        if (null == productCategory) {
            LOGGER.warn("the productCategory object is null.");
            return 0 ;
        }

        // 更新记录
        int result = productCategoryMapper.updateByPrimaryKeySelective(productCategory) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update productCategory object result: {}", result);
        }

        return result;
    }

    @Override
    public int deleteById(String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete key by id: {}", key);
        }

        if (StringUtils.isEmpty(key)) {
            LOGGER.warn("the key id object is null.");
            return 0 ;
        }

        // 删除记录数
        int result = productCategoryMapper.deleteByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete productCategory object by id result: {}", result);
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public ProductCategory getById(String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find productCategory by id: {}", key);
        }

        if (StringUtils.isEmpty(key)) {
            LOGGER.warn("the productCategory id object is null.");
            return null ;
        }

        // 查找实体对象
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find muen object by id result: {}", JSON.toJSONString(productCategory));
        }

        return productCategory;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<ProductCategory> getAll() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get all productCategory");
        }

        //查询所有分类
        ProductCategoryExample example = new ProductCategoryExample();
        //排序
        List<ProductCategory> productCategoryList = productCategoryMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get productCategory all object count: {}", productCategoryList.size());
        }

        return productCategoryList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<ProductCategory> getByParentId(String parentId){
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find productCategory by parentID: {}", parentId);
        }

        //查询所有
        ProductCategoryExample example = new ProductCategoryExample();
        ProductCategoryExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isEmpty(parentId)){
            criteria.andParentIdIsNull();
        }else {
            criteria.andParentIdEqualTo(parentId);
        }

        List<ProductCategory> productCategoryList = productCategoryMapper.selectByExample(example);

        return productCategoryList;
    }

    @Override
    public int countByExample(ProductCategoryExample example) {
        if (null == example){
            return 0;
        }
        return productCategoryMapper.countByExample(example);
    }

    @Override
    public List<ProductCategory> getByExample(ProductCategoryExample example) {
        if (null == example){
            return null;
        }

        List<ProductCategory> list = productCategoryMapper.selectByExample(example);

        return list;
    }

    /**
     * 查询返回树形
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<ProductCategory> getTree(ProductCategoryExample example) {
        if(null == example){
            return null;
        }
        // 查询数据
        List<ProductCategory> productCategorys = productCategoryMapper.selectByTree(example);
        return productCategorys;
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
    public Page<ProductCategory> getScrollData(int pageNum, int pageSize, ProductCategoryExample example) {

        // 分页
        PageHelper.startPage(pageNum, pageSize) ;

        // 查询数据
        List<ProductCategory> productCategorys = productCategoryMapper.selectByExamplePage(example);
        Page<ProductCategory> page = new Page<>(productCategorys) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get productCategorys scroll object count: {}", page.getCount());
        }

        return page;
    }

    @Override
    public int deleteCascadeById(String id) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete id by id: {}", id);
        }

        if (StringUtils.isEmpty(id)) {
            LOGGER.warn("the key id object is null.");
            return 0 ;
        }

        // 删除记录数
        int result = productCategoryMapper.deleteByPrimaryKey(id) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete productCategory object by id result: {}", result);
        }

        return result;
    }
}
