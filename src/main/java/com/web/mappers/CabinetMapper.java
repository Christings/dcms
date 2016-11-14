package com.web.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.web.bean.form.CabinetForm;
import com.web.bean.result.CabinetResult;
import com.web.entity.Cabinet;
import com.web.example.CabinetExample;

/**
 * 机柜管理MAPPER
 *
 * @author 田军兴
 * @date 2016-11-6
 */
public interface CabinetMapper {

	int countByExample(CabinetExample example);

	int deleteByExample(CabinetExample example);

	@Delete({ "delete from t_b_cabinet", "where id = #{id,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String id);

	@Insert({ "insert into t_b_cabinet (id, name, ", "resource_code, room_id, ", "equipment_type_id, area_id, ", "x, y, z, ",
			"height, weight, power, ", "room_status, model_height, ", "model_width, model_color, ", "status, warranty_time, ",
			"work_time, u_order, ", "create_date, create_name, ", "update_date, update_name)",
			"values (#{id,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
			"#{resourceCode,jdbcType=VARCHAR}, #{roomId,jdbcType=VARCHAR}, ",
			"#{equipmentTypeId,jdbcType=VARCHAR}, #{areaId,jdbcType=VARCHAR}, ",
			"#{x,jdbcType=REAL}, #{y,jdbcType=REAL}, #{z,jdbcType=REAL}, ",
			"#{height,jdbcType=INTEGER}, #{weight,jdbcType=REAL}, #{power,jdbcType=REAL}, ",
			"#{roomStatus,jdbcType=INTEGER}, #{modelHeight,jdbcType=INTEGER}, ",
			"#{modelWidth,jdbcType=INTEGER}, #{modelColor,jdbcType=VARCHAR}, ",
			"#{status,jdbcType=INTEGER}, #{warrantyTime,jdbcType=TIMESTAMP}, ",
			"#{workTime,jdbcType=TIMESTAMP}, #{uOrder,jdbcType=INTEGER}, ",
			"#{createDate,jdbcType=TIMESTAMP}, #{createName,jdbcType=VARCHAR}, ",
			"#{updateDate,jdbcType=TIMESTAMP}, #{updateName,jdbcType=VARCHAR})" })
	int insert(Cabinet record);

	int insertSelective(Cabinet record);

	List<Cabinet> selectByExample(CabinetExample example);

	@Select({ "select", "id, name, resource_code, room_id, equipment_type_id, area_id, x, y, ",
			"z, height, weight, power, room_status, model_height, model_width, model_color, ",
			"status, warranty_time, work_time, u_order, create_date, create_name, update_date, ", "update_name", "from t_b_cabinet",
			"where id = #{id,jdbcType=VARCHAR}" })
	@ResultMap("BaseResultMap")
	Cabinet selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Cabinet record, @Param("example") CabinetExample example);

	int updateAreaByExample(@Param("record") Cabinet record, @Param("example") CabinetExample example);

	int updateByExample(@Param("record") Cabinet record, @Param("example") CabinetExample example);

	int updateByPrimaryKeySelective(Cabinet record);

	@Update({ "update t_b_cabinet", "set name = #{name,jdbcType=VARCHAR},", "resource_code = #{resourceCode,jdbcType=VARCHAR},",
			"room_id = #{roomId,jdbcType=VARCHAR},", "equipment_type_id = #{equipmentTypeId,jdbcType=VARCHAR},",
			"area_id = #{areaId,jdbcType=VARCHAR},", "x = #{x,jdbcType=REAL},", "y = #{y,jdbcType=REAL},", "z = #{z,jdbcType=REAL},",
			"height = #{height,jdbcType=INTEGER},", "weight = #{weight,jdbcType=REAL},", "power = #{power,jdbcType=REAL},",
			"room_status = #{roomStatus,jdbcType=INTEGER},", "model_height = #{modelHeight,jdbcType=INTEGER},",
			"model_width = #{modelWidth,jdbcType=INTEGER},", "model_color = #{modelColor,jdbcType=VARCHAR},",
			"status = #{status,jdbcType=INTEGER},", "warranty_time = #{warrantyTime,jdbcType=TIMESTAMP},",
			"work_time = #{workTime,jdbcType=TIMESTAMP},", "u_order = #{uOrder,jdbcType=INTEGER},",
			"create_date = #{createDate,jdbcType=TIMESTAMP},", "create_name = #{createName,jdbcType=VARCHAR},",
			"update_date = #{updateDate,jdbcType=TIMESTAMP},", "update_name = #{updateName,jdbcType=VARCHAR}",
			"where id = #{id,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(Cabinet record);

	List<Cabinet> selectListByExample(CabinetExample example);

	List<Cabinet> selectWithProNames(String roomName, String name, String resourceCode, Integer status, String orderByClause);

	List<CabinetResult> selectGridData(CabinetForm form);
}