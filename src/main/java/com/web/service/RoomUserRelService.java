package com.web.service;

import com.web.entity.RoomUserRel;

import java.util.List;

/**
 * 机房用户关联关系接口
 *
 * @author 田军兴
 * @date 2016-10-30
 */
public interface RoomUserRelService {
	/**
	 * 根据机房ID查询所有用户ID
	 */
	public List<RoomUserRel> selectByServiceRoomId(RoomUserRel roomUserRel);

	/**
	 * 根据机房ID删除对应关系
	 */
	public int deleteByServiceRoomId(String serviceRoomId);

	/**
	 * 根据条件查询机房和用户对应关系
	 */
	public RoomUserRel getOne(RoomUserRel roomUserRel);

	/**
	 * 插入机房和用户关联关系
	 */
	public int save(RoomUserRel roomUserRel);

	/**
	 * 批量插入机房ID和用户关联关系
	 */
	public int batchSave(String serviceRoomId, String[] userIds);
}
