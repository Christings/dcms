package com.web.bean.form;

/**
 * 分类管理Form
 *
 * @author 杜延雷
 * @date 2016/11/7.
 */
public class TypeForm extends CommonForm {
    /**
     * 父ID
     */
    private String parentId;

    /**
     * 分类级别
     */
    private Integer level;

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
}
