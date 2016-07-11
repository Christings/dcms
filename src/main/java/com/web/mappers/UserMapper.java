package com.web.mappers;

import org.apache.ibatis.annotations.Param;

import com.web.core.mapper.BaseMapper;
import com.web.entity.User;

/**
 * Created by tians on 2016/6/12.
 * user表数据操作接口，不需要实现，但是方法名要与对应的mapper.xml中sql操作的配置文件ID相对应,相当于DAO
 */
public interface UserMapper extends BaseMapper<User>{
 
    public User getUserByName(String name)throws Exception;
    
    public void updateUserEnabled(@Param("enabled")Integer enabled,@Param("id")String id)throws Exception;
    
    public void updateUserDelete(@Param("deleted")Integer deleted,@Param("id")String id)throws Exception;

    public int updateUserPassword(User user);

}
