package com.web.mappers;

import com.web.core.BaseMapper;
import com.web.entity.User;

import java.util.List;

/**
 * Created by tians on 2016/6/12.
 * user表数据操作接口，不需要实现，但是方法名要与对应的mapper.xml中sql操作的配置文件ID相对应,相当于DAO
 */
public interface UserMapper extends BaseMapper<User>{

    public User getUserByName(String name);

}
