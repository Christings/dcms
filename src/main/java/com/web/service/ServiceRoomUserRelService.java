package com.web.service;

import com.web.entity.ServiceRoomUserRel;

import java.util.List;

/**
 * 机房用户关联关系接口
 *
 * @author 田军兴
 * @date 2016-10-30
 */
public interface ServiceRoomUserRelService {
	/**
	 * 根据机房ID查询所有用户ID
	 */
	public List<ServiceRoomUserRel> selectByServiceRoomId(ServiceRoomUserRel serviceRoomUserRel);

	/**
	 * 根据机房ID删除对应关系
	 */
	public int deleteByServiceRoomId(String serviceRoomId);

	/**
	 * 根据条件查询机房和用户对应关系
	 */
	public ServiceRoomUserRel getOne(ServiceRoomUserRel serviceRoomUserRel);

	/**
	 * 插入机房和用户关联关系
	 */
	public int save(ServiceRoomUserRel serviceRoomUserRel);

	/**
	 * 批量插入机房ID和用户关联关系
	 */
	public int batchSave(String serviceRoomId, String[] userIds);
}
