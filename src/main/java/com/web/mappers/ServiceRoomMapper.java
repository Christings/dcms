package com.web.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import com.web.entity.ServiceRoom;
import com.web.example.ServiceRoomExample;

/**
 * 机房管理mapper
 *
 * @author 田军兴
 * @date 2016-07-30
 */
public interface ServiceRoomMapper {

	int countByExample(ServiceRoomExample example);

	int deleteByExample(ServiceRoomExample example);

	int deleteByPrimaryKey(String id);

	int insert(ServiceRoom record);

	int insertSelective(ServiceRoom record);

	List<ServiceRoom> selectByExample(ServiceRoomExample example);

	@ResultMap("BaseResultMap")
	ServiceRoom selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") ServiceRoom record, @Param("example") ServiceRoomExample example);

	int updateByExample(@Param("record") ServiceRoom record, @Param("example") ServiceRoomExample example);

	int updateByPrimaryKeySelective(ServiceRoom record);

	int updateByPrimaryKey(ServiceRoom record);
}
