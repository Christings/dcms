package com.web.bean.form;

/**
 * 接收用户访问数据
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class FixedEquipmentForm extends CommonForm {
    /**
     * 设备名称
     */
    private String equName;

    /**
     * 设备类型
     */
    private String equType;

    /**
     * 设备厂商
     */
    private String equVendor;

    /**
     * rso 文件路径
     */
    private String rsoPath;

    /**
     * max文件路径
     */
    private String maxPath;

    /**
     * 设备状态
     */
    private Integer status;

    /**
     * 状态信息
     */
    private String statusMsg;

    public String getEquName() {
        return equName;
    }

    public void setEquName(String equName) {
        this.equName = equName;
    }

    public String getEquType() {
        return equType;
    }

    public void setEquType(String equType) {
        this.equType = equType;
    }

    public String getEquVendor() {
        return equVendor;
    }

    public void setEquVendor(String equVendor) {
        this.equVendor = equVendor;
    }

    public String getRsoPath() {
        return rsoPath;
    }

    public void setRsoPath(String rsoPath) {
        this.rsoPath = rsoPath;
    }

    public String getMaxPath() {
        return maxPath;
    }

    public void setMaxPath(String maxPath) {
        this.maxPath = maxPath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }
}
