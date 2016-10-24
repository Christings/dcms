package com.web.mappers;

import com.web.entity.DoMain;
import com.web.example.DoMainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DoMainMapper {
    int countByExample(DoMainExample example);

    int deleteByExample(DoMainExample example);

    int deleteByPrimaryKey(String id);

    int insert(DoMain record);

    int insertSelective(DoMain record);

    List<DoMain> selectByExample(DoMainExample example);

    /**
     * 自定义查询返回树形域
     */
    List<DoMain> selectByTree(Map<String,String> params);

    DoMain selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DoMain record, @Param("example") DoMainExample example);

    int updateByExample(@Param("record") DoMain record, @Param("example") DoMainExample example);

    int updateByPrimaryKeySelective(DoMain record);

    int updateByPrimaryKey(DoMain record);
}