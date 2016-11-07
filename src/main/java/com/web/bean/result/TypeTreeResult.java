package com.web.bean.result;

import com.web.entity.Type;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 分类以树方式返回
 *
 * @author 杜延雷
 * @date 2016-11-07
 */
public class TypeTreeResult {
	private String id;

	private String name;

	private String parentId;

	private List<TypeTreeResult> children;

	/**
	 * 将分类转换成分类树 返回到前端结果集
	 *
	 * @return 分类结果
	 */
	public static TypeTreeResult convert(Type type) {
		if (null == type) {
			return null;
		}

		TypeTreeResult result = new TypeTreeResult();
		BeanUtils.copyProperties(type, result);

		return result;
	}

	/**
	 * 将分类转换成分类树 返回到前端结果集
	 *
	 * @param types
	 * @return 分类结果集
	 */
	public static List<TypeTreeResult> convert(Collection<Type> types) {
		if (null == types || types.size()==0) {
			return null;
		}

		List<TypeTreeResult> result = new ArrayList<>();
		for(Type type:types){
			result.add(TypeTreeResult.convert(type));
		}

		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

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

	public List<TypeTreeResult> getChildren() {
		return children;
	}

	public void setChildren(List<TypeTreeResult> children) {
		this.children = children;
	}
}