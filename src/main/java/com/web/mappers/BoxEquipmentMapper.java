package com.web.mappers;

import java.util.List;

import com.web.entity.BoxEquipment;
import com.web.example.BoxEquipmentExample;
import org.apache.ibatis.annotations.*;

public interface BoxEquipmentMapper {

    int countByExample(BoxEquipmentExample example);

    int deleteByExample(BoxEquipmentExample example);

    @Delete({
        "delete from t_b_box_equipment",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    @Insert({
        "insert into t_b_box_equipment (id, equipment_id, ",
        "cabinet_id, pos, ",
        "direction, confirmed, ",
        "create_date, create_name, ",
        "update_date, update_name)",
        "values (#{id,jdbcType=VARCHAR}, #{equipmentId,jdbcType=VARCHAR}, ",
        "#{cabinetId,jdbcType=VARCHAR}, #{pos,jdbcType=TINYINT}, ",
        "#{direction,jdbcType=BIT}, #{confirmed,jdbcType=INTEGER}, ",
        "#{createDate,jdbcType=TIMESTAMP}, #{createName,jdbcType=VARCHAR}, ",
        "#{updateDate,jdbcType=TIMESTAMP}, #{updateName,jdbcType=VARCHAR})"
    })
    int insert(BoxEquipment record);

    int insertSelective(BoxEquipment record);

    List<BoxEquipment> selectByExample(BoxEquipmentExample example);

    @Select({
        "select",
        "id, equipment_id, cabinet_id, pos, direction, confirmed, create_date, create_name, ",
        "update_date, update_name",
        "from t_b_box_equipment",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    @ResultMap("BaseResultMap")
    BoxEquipment selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BoxEquipment record, @Param("example") BoxEquipmentExample example);

    int updateByExample(@Param("record") BoxEquipment record, @Param("example") BoxEquipmentExample example);

    int updateByPrimaryKeySelective(BoxEquipment record);
    
    @Update({
        "update t_b_box_equipment",
        "set equipment_id = #{equipmentId,jdbcType=VARCHAR},",
          "cabinet_id = #{cabinetId,jdbcType=VARCHAR},",
          "pos = #{pos,jdbcType=TINYINT},",
          "direction = #{direction,jdbcType=BIT},",
          "confirmed = #{confirmed,jdbcType=INTEGER},",
          "create_date = #{createDate,jdbcType=TIMESTAMP},",
          "create_name = #{createName,jdbcType=VARCHAR},",
          "update_date = #{updateDate,jdbcType=TIMESTAMP},",
          "update_name = #{updateName,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(BoxEquipment record);
}