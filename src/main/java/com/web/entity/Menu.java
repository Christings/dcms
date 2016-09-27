package com.web.entity;

import com.web.core.entity.BaseEntity;

import java.util.List;

/**
 * 菜单实体
 *
 * @author 杜延雷
 * @date 2016-07-06
 */
public class Menu extends BaseEntity {
	/**
	 * 菜单级别
	 */
	private Short level;

	/**
	 * 菜单名称
	 */
	private String name;

    /**
     * 排序
     */
    private Short rank;

	/**
	 * 访问链接
	 */
	private String url;

	/**
	 * 菜单父ID
	 */
	private String parentId;

	/**
	 * 菜单图标ID
	 */
	private String iconId;

	/**
	 * 菜单类型
	 */
	private Short type;

	/**
	 * 子菜单集合
     */
	private List<Menu> childMenu;

	public Short getLevel() {
		return level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

    public Short getRank() {
        return rank;
    }

    public void setRank(Short rank) {
        this.rank = rank;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId == null ? null : parentId.trim();
	}

	public String getIconId() {
		return iconId;
	}

	public void setIconId(String iconId) {
		this.iconId = iconId == null ? null : iconId.trim();
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public List<Menu> getChildMenu() {
		return childMenu;
	}

	public void setChildMenu(List<Menu> childMenu) {
		this.childMenu = childMenu;
	}
}