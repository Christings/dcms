package com.web.service;

import com.web.core.util.page.Page;
import com.web.entity.FixedEquipment;
import com.web.example.FixedEquipmentExample;

import java.util.List;

/**
 * 机柜设备管理  接口逻辑
 *
 * @author 杜延雷
 * @date 2016/8/25.
 */
public interface FixedEquipmentService extends IService<FixedEquipment,String> {

    /**
     * 根据查询条件获取查询数量
     */
    int count(FixedEquipmentExample example);

    /**
     * 分页处理
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    Page<FixedEquipment> getScrollData(int pageNum, int pageSize, FixedEquipmentExample example);

    /**
     * 根据条件查询
     * @param example
     * @return
     */
    List<FixedEquipment> getExample(FixedEquipmentExample example);
}
