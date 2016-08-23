package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.ServiceRoom;
import com.web.example.ServiceRoomExample;

/**
 * 机房管理接口
 *
 * @author 田军兴
 * @date 2016-07-30
 */
public interface ServiceRoomService extends IService<ServiceRoom, String> {

	/**
	 * 数据词典分页查询
	 *
	 * @param example
	 */
	public Page<ServiceRoom> getByPage(int pageCurrent, int count, ServiceRoomExample example);

}