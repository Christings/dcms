package com.web.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.core.util.Page;
import com.web.entity.DataDict;
import com.web.example.DataDictExample;

public interface DataDictMapper {

	int countByExample(DataDictExample example);

	int deleteByExample(DataDictExample example);

	int deleteByPrimaryKey(String id);

	int insertSelective(DataDict record);

	List<DataDict> selectByExample(DataDictExample example);

	DataDict selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") DataDict record, @Param("example") DataDictExample example);

	int updateByExample(@Param("record") DataDict record, @Param("example") DataDictExample example);

	int updateByPrimaryKey(DataDict record);

	List<DataDict> selectAll();

	List<DataDict> getByPage(Page<DataDict> page) throws Exception;
}