package com.web.mappers;

import com.web.entity.ProductCategory;
import com.web.example.ProductCategoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryMapper {
    int countByExample(ProductCategoryExample example);

    int deleteByExample(ProductCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    List<ProductCategory> selectByExample(ProductCategoryExample example);

    ProductCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);

    int updateByExample(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    /**
     * 自定义查询返回树形分类
     */
    List<ProductCategory> selectByTree(ProductCategoryExample example);

    /**
     * 自定义分页返回树结构
     */
    List<ProductCategory> selectByExamplePage(ProductCategoryExample example);
}