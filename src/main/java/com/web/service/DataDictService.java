package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.DataDict;
import com.web.example.DataDictExample;

import java.util.List;

/**
 * 数据词典接口
 *
 * @author 田军兴
 * @date 2016-07-09
 */
public interface DataDictService extends IService<DataDict, String> {

	/**
	 * 数据词典分页查询
	 *
	 * @param example
	 */
	public Page<DataDict> getByPage(int pageCurrent, int count, DataDictExample example);

	/**
	 * 根据groupId查询
	 *
	 * @param dataDict
	 */
	public List<DataDict> getByGroupId(DataDict dataDict);
}
