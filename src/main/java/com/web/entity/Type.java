package com.web.entity;

import com.web.core.entity.BaseEntity;

import java.util.List;

/**
 * 分类实体对象
 *
 * @author 杜延雷
 * @date 2016-11-07
 */
public class Type extends BaseEntity{

    private String name;

    private String parentId;

    private Integer level;

    /**
     * 子分类集合
     */
    private List<Type> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Type> getChildren() {
        return children;
    }

    public void setChildren(List<Type> children) {
        this.children = children;
    }
}