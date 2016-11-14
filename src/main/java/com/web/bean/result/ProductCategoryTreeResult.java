package com.web.bean.result;

import com.web.entity.ProductCategory;
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
public class ProductCategoryTreeResult extends ProductCategoryResult{
	/**
	 * 分类子类集合
	 */
	private List<ProductCategoryTreeResult> children;

	/**
	 * 将分类转换成分类树 返回到前端结果集
	 *
	 * @return 分类结果
	 */
	public static ProductCategoryTreeResult convert(ProductCategory productCategory) {
		if (null == productCategory) {
			return null;
		}

		ProductCategoryTreeResult result = new ProductCategoryTreeResult();
		BeanUtils.copyProperties(productCategory, result);

		return result;
	}

	/**
	 * 将分类转换成分类树 返回到前端结果集
	 *
	 * @param productCategorys
	 * @return 分类结果集
	 */
	public static List<ProductCategoryTreeResult> convert(Collection<ProductCategory> productCategorys) {
		if (null == productCategorys || productCategorys.size()==0) {
			return null;
		}

		List<ProductCategoryTreeResult> result = new ArrayList<>();
		for(ProductCategory productCategory:productCategorys){
			result.add(ProductCategoryTreeResult.convert(productCategory));
		}

		return result;
	}

	public List<ProductCategoryTreeResult> getChildren() {
		return children;
	}

	public void setChildren(List<ProductCategoryTreeResult> children) {
		this.children = children;
	}
}