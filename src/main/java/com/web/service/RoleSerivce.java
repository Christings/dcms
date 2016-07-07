package com.web.service;

import java.util.List;

import com.web.core.util.page.QueryResult;
import com.web.entity.Menu;
import com.web.entity.Role;
import com.web.example.MenuExample;

public interface RoleSerivce extends IService<Role, String>{
	/**
     * 分页处理 根据查询条件进行分页
     * @param pagination
     * @param maxResult
     * @param example
     * @return
     */
    QueryResult<Menu> getScrollData(int pagination, int maxResult, MenuExample example);
    
}
