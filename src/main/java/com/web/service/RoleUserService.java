package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.UserRole;
import com.web.example.RoleUserExample;

import java.util.List;

/**
 * Created by tong_yunpeng on 2016/7/13.
 */
public interface RoleUserService extends IService<UserRole,String> {



    public int countByExample(RoleUserExample example);

    public List<UserRole> selectByExample(RoleUserExample example);

    public Page<UserRole> getScrollData(int pageNum, int pageSize,  RoleUserExample example);

    public int saveList(List<UserRole> list);

    public int deleteByUserId(String userId);

    public Page<UserRole> getScrollDataByUserId(int pageNum, int pageSize,  String userId);

    public Page<UserRole> getScrollDataByRoleId(int pageNum, int pageSize,  String roleId);
}
