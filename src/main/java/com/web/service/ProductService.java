package com.web.service;


import com.web.core.util.page.Page;
import com.web.entity.Product;
import com.web.example.ProductExample;

import java.util.List;

/**
 * 设备型号 逻辑接口
 *
 * @author 杜延雷
 * @date 2016-11-14
 */
public interface ProductService extends IService<Product,String> {

	/**
	 * 根据查询条件获取查询数量
     */
	int count(ProductExample example);

	/**
	 * 分页处理 根据查询条件进行分页
	 * @param pageNum
	 * @param pageSize
	 * @param example
	 * @return
	 */
	Page<Product> getScrollData(int pageNum, int pageSize, ProductExample example);

	/**
	 * 根据传递条件查询 集合
	 *
	 * @param example
	 * @return
     */
	List<Product> getExample(ProductExample example) ;
}
