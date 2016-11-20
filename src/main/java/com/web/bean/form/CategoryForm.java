package com.web.bean.form;

/**
 * 设备分类管理Form
 *
 * @author 杜延雷
 * @date 2016/11/14.
 */
public class CategoryForm extends CommonForm {
    /**
     * 父ID
     */
    private String parentId;

    /**
     * 分类级别
     */
    private Integer level;

    /**
     * 描述
     */
    private String description;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
