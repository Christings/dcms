package com.web.entity;

/**
 * 机房和用户关联关系表
 *
 * @author 田军兴
 * @date 2016-10-30
 */
public class ServiceRoomUserRel {
	/**
	 * 机房ID
	 */
	private String serviceRoomId;
	/**
	 * 用户ID
	 */
	private String userId;

	public String getServiceRoomId() {
		return serviceRoomId;
	}

	public void setServiceRoomId(String serviceRoomId) {
		this.serviceRoomId = serviceRoomId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
