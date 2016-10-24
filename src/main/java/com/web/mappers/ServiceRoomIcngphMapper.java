package com.web.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import com.web.entity.ServiceRoomIcngph;
import com.web.example.ServiceRoomIcngphExample;

/**
 * 机房平面图mapper
 *
 * @author 田军兴
 * @date 2016-10-22
 */
public interface ServiceRoomIcngphMapper {

    int countByExample(ServiceRoomIcngphExample example);

    int deleteByExample(ServiceRoomIcngphExample example);

    int deleteByPrimaryKey(String id);

    int insertSelective(ServiceRoomIcngph record);

    List<ServiceRoomIcngph> selectByExample(ServiceRoomIcngphExample example);

    @ResultMap("BaseResultMap")
    ServiceRoomIcngph selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ServiceRoomIcngph record, @Param("example") ServiceRoomIcngphExample example);

    int updateByExample(@Param("record") ServiceRoomIcngph record, @Param("example") ServiceRoomIcngphExample example);

    int updateByPrimaryKeySelective(ServiceRoomIcngph record);

    int updateByPrimaryKey(ServiceRoomIcngph record);
}
