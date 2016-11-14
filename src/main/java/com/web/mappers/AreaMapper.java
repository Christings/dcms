package com.web.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.web.bean.form.AreaForm;
import com.web.bean.result.AreaResult;
import com.web.entity.Area;
import com.web.example.AreaExample;

public interface AreaMapper {

	int countByExample(AreaExample example);

	int deleteByExample(AreaExample example);

	@Delete({ "delete from t_b_area", "where id = #{id,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String id);

	@Insert({ "insert into t_b_area (id, name, ", "room_id, remark, ", "status, create_date, ", "create_name, update_date, ",
			"update_name)", "values (#{id,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
			"#{roomId,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, ",
			"#{status,jdbcType=INTEGER}, #{createDate,jdbcType=TIMESTAMP}, ",
			"#{createName,jdbcType=VARCHAR}, #{updateDate,jdbcType=TIMESTAMP}, ", "#{updateName,jdbcType=VARCHAR})" })
	int insert(Area record);

	int insertSelective(Area record);

	List<Area> selectByExample(AreaExample example);

	@Select({ "select", "id, name, room_id, remark, status, create_date, create_name, update_date, update_name", "from t_b_area",
			"where id = #{id,jdbcType=VARCHAR}" })
	@ResultMap("BaseResultMap")
	Area selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Area record, @Param("example") AreaExample example);

	int updateByExample(@Param("record") Area record, @Param("example") AreaExample example);

	int updateByPrimaryKeySelective(Area record);

	@Update({ "update t_b_area", "set name = #{name,jdbcType=VARCHAR},", "room_id = #{roomId,jdbcType=VARCHAR},",
			"remark = #{remark,jdbcType=VARCHAR},", "status = #{status,jdbcType=INTEGER},",
			"create_date = #{createDate,jdbcType=TIMESTAMP},", "create_name = #{createName,jdbcType=VARCHAR},",
			"update_date = #{updateDate,jdbcType=TIMESTAMP},", "update_name = #{updateName,jdbcType=VARCHAR}",
			"where id = #{id,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(Area record);

	List<AreaResult> selectGridData(AreaForm form);
}