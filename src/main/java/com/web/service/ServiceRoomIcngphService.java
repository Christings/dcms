package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.ServiceRoomIcngph;
import com.web.example.ServiceRoomIcngphExample;

import java.util.List;

/**
 * 机房平面图管理接口
 *
 * @author 田军兴
 * @date 2016-10-22
 */
public interface ServiceRoomIcngphService extends IService<ServiceRoomIcngph,String>{
    /**
     * 分页处理 根据查询条件进行分页
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    Page<ServiceRoomIcngph> getScrollData(int pageNum, int pageSize, ServiceRoomIcngphExample example);

    List<ServiceRoomIcngph> getByExample(ServiceRoomIcngphExample example);
}
