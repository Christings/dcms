package com.web.entity;

import com.web.core.entity.BaseEntity;

/**
 * 菜单实体
 *
 * @author 田军兴
 * @date 2016-07-10
 */
public class DataDict extends BaseEntity {

	private Integer dictValue;

	private String dictName;

	private String groupId;

	private String groupName;

	private Integer dictType;

	private Integer sort;

	private String comment;

	public Integer getDictValue() {
		return dictValue;
	}

	public void setDictValue(Integer dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getDictType() {
		return dictType;
	}

	public void setDictType(Integer dictType) {
		this.dictType = dictType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
