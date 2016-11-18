package com.web.bean.result;

import com.web.entity.Category;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 设备分类以树方式返回
 *
 * @author 杜延雷
 * @date 2016-11-14
 */
public class CategoryTreeResult extends CategoryResult {
	/**
	 * 分类子类集合
	 */
	private List<CategoryTreeResult> children;

	/**
	 * 将分类转换成分类树 返回到前端结果集
	 *
	 * @return 分类结果
	 */
	public static CategoryTreeResult convert(Category category) {
		if (null == category) {
			return null;
		}

		CategoryTreeResult result = new CategoryTreeResult();
		BeanUtils.copyProperties(category, result);

		return result;
	}

	/**
	 * 将分类转换成分类树 返回到前端结果集
	 *
	 * @param categories
	 * @return 分类结果集
	 */
	public static List<CategoryTreeResult> convert(Collection<Category> categories) {
		if (null == categories || categories.size()==0) {
			return null;
		}

		List<CategoryTreeResult> result = new ArrayList<>();
		for(Category category : categories){
			result.add(CategoryTreeResult.convert(category));
		}

		return result;
	}

	public List<CategoryTreeResult> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryTreeResult> children) {
		this.children = children;
	}
}