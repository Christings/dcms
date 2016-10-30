package com.web.service.impl;

import com.web.entity.ServiceRoomUserRel;
import com.web.example.ServiceRoomUserRelExample;
import com.web.mappers.ServiceRoomUserRelMapper;
import com.web.service.ServiceRoomUserRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 机房用户关联关系接口
 *
 * @author 田军兴
 * @date 2016-10-30
 */
@Service
@Transactional
public class ServiceRoomUserRelServiceImpl implements ServiceRoomUserRelService{
    @Autowired
    private ServiceRoomUserRelMapper mapper;

    @Override
    public List<ServiceRoomUserRel> selectByServiceRoomId(ServiceRoomUserRel serviceRoomUserRel) {
        return mapper.selectByServiceRoomId(serviceRoomUserRel.getServiceRoomId());
    }

    @Override
    public int deleteByServiceRoomId(String serviceRoomId) {
        return mapper.deleteByServiceRoomId(serviceRoomId);
    }

    @Override
    public ServiceRoomUserRel getOne(ServiceRoomUserRel serviceRoomUserRel) {
        ServiceRoomUserRelExample example = new ServiceRoomUserRelExample();
        ServiceRoomUserRelExample.Criteria criteria = example.createCriteria();
        criteria.andServiceRoomIdEqualTo(serviceRoomUserRel.getServiceRoomId());
        criteria.andUserIdEqualTo(serviceRoomUserRel.getUserId());
        List<ServiceRoomUserRel> lsit =  mapper.selectByExample(example);
        if(lsit.size() > 0){
           return lsit.get(0);
        }else{
         return null;
        }
    }

    @Override
    public int save(ServiceRoomUserRel serviceRoomUserRel) {
        return mapper.insert(serviceRoomUserRel);
    }

    @Override
    public int batchSave(String serviceRoomId, String[] userIds) {
        int result = 0;
        for(String userId : userIds){
            ServiceRoomUserRel rel = new ServiceRoomUserRel();
            rel.setServiceRoomId(serviceRoomId);
            rel.setUserId(userId);
            result = result + mapper.insertSelective(rel);
        }
        return result;
    }
}
