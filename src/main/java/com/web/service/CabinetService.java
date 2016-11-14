package com.web.service;

import java.util.List;

import com.web.bean.form.CabinetForm;
import com.web.bean.result.CabinetResult;
import com.web.core.util.page.Page;
import com.web.entity.Cabinet;
import com.web.example.CabinetExample;

/**
 * 机柜管理接口
 *
 * @author 田军兴
 * @date 2016-11-07
 */
public interface CabinetService extends IService<Cabinet,String>{

    /**
     * 机柜管理分页查询
     *
     * @param example
     */
    public Page<Cabinet> getByPage(int pageCurrent, int count,CabinetExample example);

    public Page<CabinetResult> getByPage(int pageCurrent, int count, CabinetForm form);

    List<Cabinet> getByExample(CabinetExample example);

    int getCount(CabinetExample example);

    int updateAreaIdByExample(CabinetExample example, String areaId);
    /**
     * 清除区域和机柜关联关系
     * */
    int updateAreaByExample(String areaId);

    CabinetResult selectResultById(String id);
}
