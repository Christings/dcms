package com.web.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.entity.OperLog;
import com.web.example.OperLogExample;

/**
 * 业务日志mapper
 *
 * @author 田军兴
 * @date 2016-06-29
 */
public interface OperLogMapper {
    
    public int countByExample(OperLogExample example);
    
    public int deleteByExample(OperLogExample example);
    
    public int deleteByPrimaryKey(String id);
    
    public int insert(OperLog record);

    public int insertSelective(OperLog record);

    public List<OperLog> selectByExample(OperLogExample example);
    
    public OperLog selectByPrimaryKey(String id);
    
    public int updateByExampleSelective(@Param("record") OperLog record, @Param("example") OperLogExample example);
    
    public int updateByExample(@Param("record") OperLog record, @Param("example") OperLogExample example);
    
    public int updateByPrimaryKeySelective(OperLog record);
    
    public int updateByPrimaryKey(OperLog record);
}
