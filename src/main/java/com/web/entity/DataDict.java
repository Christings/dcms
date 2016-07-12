package com.web.entity;

import com.web.core.entity.BaseEntity;

/**
 * 菜单实体
 *
 * @author 田军兴
 * @date 2016-07-10
 */
public class DataDict extends BaseEntity {

	/**
	 * 字典值
	 */
	private Integer dictValue;

	/**
	 * 字典名称
	 */
	private String dictName;

	/**
	 * 分组ID
	 */
	private String groupId;

	/**
	 * 分组名称
	 */
	private String groupName;

	/**
	 * 字典数据类型
	 */
	private Integer dictType;

	/**
	 * 字典数据排序
	 */
	private Integer sort;

	/**
	 * 备注
	 */
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
