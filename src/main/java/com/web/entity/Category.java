package com.web.entity;

import com.web.core.entity.BaseEntity;

import java.util.List;

/**
 * 设备分类实体对象
 *
 * @author 杜延雷
 * @date 2016-11-14
 */
public class Category extends BaseEntity {

    private Integer level;

    private String name;

    private String description;

    private String parentId;

    /**
     * 子分类集合
     */
    private List<Category> children;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}