package com.web.mappers;

import com.web.entity.RoomUserRel;
import com.web.example.RoomUserRelExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 机房用户关联关系mapper
 *
 * @author 田军兴
 * @date 2016-10-30
 */
public interface RoomUserRelMapper {

	int countByExample(RoomUserRelExample example);

	int deleteByExample(RoomUserRelExample example);

	@Delete({ "delete from t_b_room_user_rel", "where service_room_id = #{serviceRoomId,jdbcType=VARCHAR}" })
	int deleteByServiceRoomId(String serviceRoomId);

	@Insert({ "insert into t_b_room_user_rel (service_room_id, user_id)",
			"values (#{serviceRoomId,jdbcType=VARCHAR}, #{userId,jdbcType=VARCHAR})" })
	int insert(RoomUserRel record);

	int insertSelective(RoomUserRel record);

	List<RoomUserRel> selectByExample(RoomUserRelExample example);

	@Select({ "select", "service_room_id, user_id", "from t_b_room_user_rel",
			"where service_room_id = #{serviceRoomId,jdbcType=VARCHAR}" })
	@ResultMap("BaseResultMap")
	List<RoomUserRel> selectByServiceRoomId(String serviceRoomId);

	int updateByExampleSelective(@Param("record") RoomUserRel record, @Param("example") RoomUserRelExample example);

	int updateByExample(@Param("record") RoomUserRel record, @Param("example") RoomUserRelExample example);

	int updateByPrimaryKeySelective(RoomUserRel record);

	@Update({ "update t_b_room_user_rel", "set user_id = #{userId,jdbcType=VARCHAR}",
			"where service_room_id = #{serviceRoomId,jdbcType=VARCHAR}" })
	int updateByServiceRoomId(RoomUserRel record);
}
