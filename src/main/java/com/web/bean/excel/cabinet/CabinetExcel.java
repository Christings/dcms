package com.web.bean.excel.cabinet;

/**
 * excel导入模板
 *
 * @author 田军兴
 * @date 2016-11-23
 */
public class CabinetExcel {

	/**
	 * 资源编码
	 */
	private String resourceCode;
	/**
	 * 设备名称
	 */
	private String cabinetName;
	/**
	 * 设备类型
	 */
	private String equType;
	/**
	 * 设备型号
	 */
	private String productType;
	/**
	 * 上级资源编码
	 */
	private String roomResourceCode;
	/**
	 * U高度
	 */
	private String uHight;
	/**
	 * 排序方式
	 */
	private String uOrder;

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getCabinetName() {
		return cabinetName;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}

	public String getEquType() {
		return equType;
	}

	public void setEquType(String equType) {
		this.equType = equType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getRoomResourceCode() {
		return roomResourceCode;
	}

	public void setRoomResourceCode(String roomResourceCode) {
		this.roomResourceCode = roomResourceCode;
	}

	public String getuHight() {
		return uHight;
	}

	public void setuHight(String uHight) {
		this.uHight = uHight;
	}

	public String getuOrder() {
		return uOrder;
	}

	public void setuOrder(String uOrder) {
		this.uOrder = uOrder;
	}
}
