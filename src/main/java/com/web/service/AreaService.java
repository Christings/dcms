package com.web.service;

import com.web.bean.form.AreaForm;
import com.web.bean.result.AreaResult;
import com.web.core.util.page.Page;
import com.web.entity.Area;
import com.web.example.AreaExample;

/**
 * 区域管理接口
 *
 * @author 田军兴
 * @date 2016-11-13
 */
public interface AreaService extends IService<Area, String> {
	public Page<AreaResult> getByPage(int pageCurrent, int count, AreaForm form);

	int getCount(AreaExample example);

	AreaResult getAreaResultById(AreaForm form);
}
