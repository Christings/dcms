package com.web.service;

import com.web.core.util.Page;
import com.web.core.util.page.QueryResult;
import com.web.entity.UserRole;
import com.web.example.RoleUserExample;

import java.util.List;

/**
 * Created by tong_yunpeng on 2016/7/13.
 */
public interface RoleUserService extends IService<UserRole,String> {

    public List<UserRole> getUserRoleByPage(Page<UserRole> page)throws Exception;

    public int countByExample(RoleUserExample example);

    public List<UserRole> selectByExample(RoleUserExample example);

    public QueryResult<UserRole> getScrollData(int pageCurrent, int count, RoleUserExample example);

    public int saveList(List<UserRole> list);

    public int deleteByUserId(String userId);

    public QueryResult<UserRole> getScrollDataByUserId(int pageCurrent, int count, String userId);

    public QueryResult<UserRole> getScrollDataByRoleId(int pageCurrent, int count, String roleId);
}
