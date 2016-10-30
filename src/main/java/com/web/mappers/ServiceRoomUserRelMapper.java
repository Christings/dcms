package com.web.mappers;

import com.web.entity.ServiceRoomUserRel;
import com.web.example.ServiceRoomUserRelExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 机房用户关联关系mapper
 *
 * @author 田军兴
 * @date 2016-10-30
 */
public interface ServiceRoomUserRelMapper {

	int countByExample(ServiceRoomUserRelExample example);

	int deleteByExample(ServiceRoomUserRelExample example);

	@Delete({ "delete from t_b_service_room_user_rel", "where service_room_id = #{serviceRoomId,jdbcType=VARCHAR}" })
	int deleteByServiceRoomId(String serviceRoomId);

	@Insert({ "insert into t_b_service_room_user_rel (service_room_id, user_id)",
			"values (#{serviceRoomId,jdbcType=VARCHAR}, #{userId,jdbcType=VARCHAR})" })
	int insert(ServiceRoomUserRel record);

	int insertSelective(ServiceRoomUserRel record);

	List<ServiceRoomUserRel> selectByExample(ServiceRoomUserRelExample example);

	@Select({ "select", "service_room_id, user_id", "from t_b_service_room_user_rel",
			"where service_room_id = #{serviceRoomId,jdbcType=VARCHAR}" })
	@ResultMap("BaseResultMap")
	List<ServiceRoomUserRel> selectByServiceRoomId(String serviceRoomId);

	int updateByExampleSelective(@Param("record") ServiceRoomUserRel record, @Param("example") ServiceRoomUserRelExample example);

	int updateByExample(@Param("record") ServiceRoomUserRel record, @Param("example") ServiceRoomUserRelExample example);

	int updateByPrimaryKeySelective(ServiceRoomUserRel record);

	@Update({ "update t_b_service_room_user_rel", "set user_id = #{userId,jdbcType=VARCHAR}",
			"where service_room_id = #{serviceRoomId,jdbcType=VARCHAR}" })
	int updateByServiceRoomId(ServiceRoomUserRel record);
}
