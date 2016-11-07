package com.web.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import com.web.entity.RoomIcngph;
import com.web.example.RoomIcngphExample;

/**
 * 机房平面图mapper
 *
 * @author 田军兴
 * @date 2016-10-22
 */
public interface RoomIcngphMapper {

    int countByExample(RoomIcngphExample example);

    int deleteByExample(RoomIcngphExample example);

    int deleteByPrimaryKey(String id);

    int insertSelective(RoomIcngph record);

    List<RoomIcngph> selectByExample(RoomIcngphExample example);

    @ResultMap("BaseResultMap")
    RoomIcngph selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RoomIcngph record, @Param("example") RoomIcngphExample example);

    int updateByExample(@Param("record") RoomIcngph record, @Param("example") RoomIcngphExample example);

    int updateByPrimaryKeySelective(RoomIcngph record);
}
