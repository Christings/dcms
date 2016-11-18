package com.web.mappers;

import com.web.entity.Category;
import com.web.example.CategoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    int countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    /**
     * 自定义查询返回树形分类
     */
    List<Category> selectByTree(CategoryExample example);

    /**
     * 自定义分页返回树结构
     */
    List<Category> selectByExamplePage(CategoryExample example);
}