package com.web.mappers;

import com.web.entity.FixedEquipment;
import com.web.example.FixedEquipmentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FixedEquipmentMapper {
    int countByExample(FixedEquipmentExample example);

    int deleteByExample(FixedEquipmentExample example);

    int deleteByPrimaryKey(String id);

    int insert(FixedEquipment record);

    int insertSelective(FixedEquipment record);

    List<FixedEquipment> selectByExample(FixedEquipmentExample example);

    FixedEquipment selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FixedEquipment record, @Param("example") FixedEquipmentExample example);

    int updateByExample(@Param("record") FixedEquipment record, @Param("example") FixedEquipmentExample example);

    int updateByPrimaryKeySelective(FixedEquipment record);

    int updateByPrimaryKey(FixedEquipment record);
}