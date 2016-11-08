package com.web.bean.result;

import com.web.entity.Cabinet;

import java.util.Date;

/**
 * 机柜管理前台展示用
 *
 * @author 田军兴
 * @date 2016-11-8
 */
public class CabinetResult{

	/**
	 * 机房名称
	 */
	private String roomName;
	/**
	 * 设备类型名称
	 */
	private String equipmentTypeName;
    /**
     * ID
     * */
    private String id;
    /**
     * 机柜名称
     * */
    private String name;
    /**
     * 机柜高度
     * */
    private Integer height;
    /**
     * 资源编码
     * */
    private String resourceCode;
    /**
     * 状态
     * */
    private Integer status;
    /**
     * 保修时间
     * */
    private Date warrantyTime;
    /**
     * 投运时间
     * */
    private Date workTime;
    /**
     * 机房ID
     * */
    private String roomId;

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getEquipmentTypeName() {
		return equipmentTypeName;
	}

	public void setEquipmentTypeName(String equipmentTypeName) {
		this.equipmentTypeName = equipmentTypeName;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(Date warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
