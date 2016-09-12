package com.web.entity;

import com.web.core.entity.BaseEntity;

/**
 * 菜单-->操作类
 *
 * @author 杜延雷
 * @date 2016-09-11
 */
public class MenuOperation extends BaseEntity{
    /**
     * URL 标识
     */
    private String url;

    /**
     * 操作名称
     */
    private String name;

    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 图标ID 预留
     */
    private String iconId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId == null ? null : iconId.trim();
    }

}