package com.web.bean.result;

/**
 * 机柜上架结果列表
 *
 * @author 田军兴
 * @date 2016-11-24
 */
public class BoxEquipmentResult {

	private String id;
	private String equipmentId;
	private String cabinetId;
	private Integer pos;
	private Integer direction;
	private Integer confirmed;
	private String equipmentName;
	private String equipmentSerial;
	private Integer equipmentType;
	private Integer equipmentStatus;
	private Integer rfid;
	private String productName;
	private String productId;
	private Integer height;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Integer getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentSerial() {
		return equipmentSerial;
	}

	public void setEquipmentSerial(String equipmentSerial) {
		this.equipmentSerial = equipmentSerial;
	}

	public Integer getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}

	public Integer getEquipmentStatus() {
		return equipmentStatus;
	}

	public void setEquipmentStatus(Integer equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}

	public Integer getRfid() {
		return rfid;
	}

	public void setRfid(Integer rfid) {
		this.rfid = rfid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
}
