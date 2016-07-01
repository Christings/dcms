package com.web.entity;

import com.web.core.entity.BaseEntity;

public class Menu extends BaseEntity {
	private Short level;

	private String name;

	private String order;

	private String url;

	private String parentId;

	private String iconId;

	private Short type;

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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order == null ? null : order.trim();
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
}