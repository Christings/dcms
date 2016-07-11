package com.web.service;

import java.util.List;

import com.web.core.util.page.QueryResult;
import com.web.entity.DataDict;
import com.web.example.DataDictExample;

/**
 * 数据词典接口
 * 
 * @author 田军兴
 * @date 2016-07-09
 */
public interface DataDictService extends IService<DataDict, String> {

	public QueryResult<DataDict> getByPage(int pageCurrent, int count, DataDictExample example);

	public List<DataDict> getByGroupId(DataDict dataDict);
}
