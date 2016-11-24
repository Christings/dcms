package com.web.service;

import com.web.bean.result.BoxEquipmentResult;
import com.web.core.util.page.Page;
import com.web.entity.BoxEquipment;
import com.web.example.BoxEquipmentExample;

import java.util.List;

/**
 * 机柜和设备关联关系接口
 *
 * @author 田军兴
 * @date 2016-11-8
 */
public interface BoxEquipmentService extends IService<BoxEquipment,String>{

    public Page<BoxEquipment> getByPage(int pageCurrent, int count, BoxEquipmentExample example);

    int getCount(BoxEquipmentExample example);

    List<BoxEquipment> selectByExample(BoxEquipmentExample example);

    List<BoxEquipmentResult> selectWithEquipment(BoxEquipment boxEquipment);

}
