package com.web.service;

import com.web.entity.UserDoMain;
import com.web.example.UserDoMainExample;

import java.util.List;

/**
 * 用户域关系 逻辑接口
 *
 * @author 杜延雷
 * @date 2016-10-22
 */
public interface UserDoMainService extends IService<UserDoMain, String> {
    /**
     * 批量保存用户域关系
     */
    void batchUserDoMain(String userId, String[] domainIds);

    /**
     * 根据用户ID 查询用户与域关系数据
     */
    List<UserDoMain> getUserDomain(String userId) ;

    /**
     * 根据条件删除
     * @param example
     * @return
     */
    int delete(UserDoMainExample example);
}
