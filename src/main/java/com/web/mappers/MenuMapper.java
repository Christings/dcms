package com.web.mappers;

import com.web.entity.Menu;
import com.web.example.MenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
    int countByExample(MenuExample example);

    int deleteByExample(MenuExample example);

    int deleteByPrimaryKey(String id);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuExample example);

    /**
     * 自定义分页返回树结构
     */
    List<Menu> selectByExamplePage(MenuExample example);

    /**
     * 自定义查询返回树形菜单
     */
    List<Menu> selectByTree(Map<String,String> params);

    Menu selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}