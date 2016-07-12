package com.web.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.core.util.Page;
import com.web.entity.DataDict;
import com.web.example.DataDictExample;

/**
 * 数据词典MAPPER
 *
 * @author 田军兴
 * @date 2016-07-09
 */
public interface DataDictMapper {

	int deleteByExample(DataDictExample example);

	int deleteByPrimaryKey(String id);

	int insertSelective(DataDict record);

	DataDict selectByPrimaryKey(String id);

	List<DataDict> selectByExample(DataDictExample example);

	int updateByExampleSelective(@Param("record") DataDict record, @Param("example") DataDictExample example);

	int updateByExample(@Param("record") DataDict record, @Param("example") DataDictExample example);

	int updateByPrimaryKey(DataDict record);

	List<DataDict> selectAll();

	List<DataDict> getByPage(Page<DataDict> page, DataDictExample example);
}