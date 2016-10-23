package com.web.entity;

import com.web.core.entity.BaseEntity;

import java.util.List;

/**
 * 域实体（组织结构）
 *
 * @author 杜延雷
 * @date 2016-10-23
 */
public class DoMain extends BaseEntity{
    private String name;

    private String parentId;

    private String description;

    private Integer level;

    /**
     * 子域集合（用来返回数据）
     */
    private List<DoMain> childDoMain;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<DoMain> getChildDoMain() {
        return childDoMain;
    }

    public void setChildDoMain(List<DoMain> childDoMain) {
        this.childDoMain = childDoMain;
    }
}