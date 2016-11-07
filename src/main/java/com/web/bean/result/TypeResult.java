package com.web.bean.result;

/**
 * 分类返回对象
 *
 * @author 杜延雷
 * @date 2016/11/7.
 */
public class TypeResult {
    /**
     * 主键ID
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 父ID
     */
    private String parentId;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
