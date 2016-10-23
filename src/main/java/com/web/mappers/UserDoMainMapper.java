package com.web.mappers;

import com.web.entity.UserDoMain;
import com.web.example.UserDoMainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDoMainMapper {
    int countByExample(UserDoMainExample example);

    int deleteByExample(UserDoMainExample example);

    int insert(UserDoMain record);

    int insertSelective(UserDoMain record);

    List<UserDoMain> selectByExample(UserDoMainExample example);

    int updateByExampleSelective(@Param("record") UserDoMain record, @Param("example") UserDoMainExample example);

    int updateByExample(@Param("record") UserDoMain record, @Param("example") UserDoMainExample example);
}