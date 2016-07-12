package com.web.service;

import java.util.List;

import com.web.core.util.Page;
import com.web.entity.DataDict;
import com.web.example.DataDictExample;

/**
 * 数据词典接口
 * 
 * @author 田军兴
 * @date 2016-07-09
 */
public interface DataDictService extends IService<DataDict, String> {

	public List<DataDict> getByPage(Page<DataDict> page, DataDictExample example);

	public List<DataDict> getByGroupId(DataDict dataDict);
}
