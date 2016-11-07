package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.RoomIcngph;
import com.web.example.RoomIcngphExample;

import java.util.List;

/**
 * 机房平面图管理接口
 *
 * @author 田军兴
 * @date 2016-10-22
 */
public interface RoomIcngphService extends IService<RoomIcngph,String>{
    /**
     * 分页处理 根据查询条件进行分页
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    Page<RoomIcngph> getScrollData(int pageNum, int pageSize, RoomIcngphExample example);

    List<RoomIcngph> getByExample(RoomIcngphExample example);
}
