package com.web.service;

import java.util.List;

import com.web.core.util.page.Page;
import com.web.entity.Room;
import com.web.example.RoomExample;

/**
 * 机房管理接口
 *
 * @author 田军兴
 * @date 2016-07-30
 */
public interface RoomService extends IService<Room, String> {

	/**
	 *机房管理分页查询
	 *
	 * @param example
	 */
	public Page<Room> getByPage(int pageCurrent, int count, RoomExample example);

	/**
	 *条件查询获取机房集合
	 *
	 * @param example
	 */
	List<Room> getByExample(RoomExample example);

	/**
	 * 下拉列表
	 * */
	List<Room> selectForChoose(RoomExample example);

}
