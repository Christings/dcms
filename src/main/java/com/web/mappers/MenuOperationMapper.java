package com.web.mappers;

import com.web.entity.MenuOperation;
import com.web.example.MenuOperationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuOperationMapper {
    int countByExample(MenuOperationExample example);

    int deleteByExample(MenuOperationExample example);

    int deleteByPrimaryKey(String id);

    int insert(MenuOperation record);

    int insertSelective(MenuOperation record);

    List<MenuOperation> selectByExample(MenuOperationExample example);

    MenuOperation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MenuOperation record, @Param("example") MenuOperationExample example);

    int updateByExample(@Param("record") MenuOperation record, @Param("example") MenuOperationExample example);

    int updateByPrimaryKeySelective(MenuOperation record);

    int updateByPrimaryKey(MenuOperation record);
}