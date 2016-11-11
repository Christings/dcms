package com.web.mappers;

import java.util.List;

import com.web.entity.Room;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import com.web.example.RoomExample;

/**
 * 机房管理mapper
 *
 * @author 田军兴
 * @date 2016-07-30
 */
public interface RoomMapper {

	int countByExample(RoomExample example);

	int deleteByExample(RoomExample example);

	int deleteByPrimaryKey(String id);

	int insert(Room record);

	int insertSelective(Room record);

	List<Room> selectByExample(RoomExample example);

	@ResultMap("BaseResultMap")
	Room selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Room record, @Param("example") RoomExample example);

	int updateByExample(@Param("record") Room record, @Param("example") RoomExample example);

	int updateByPrimaryKeySelective(Room record);

	int updateByPrimaryKey(Room record);

	List<Room> selectForChoose(RoomExample example);
}
