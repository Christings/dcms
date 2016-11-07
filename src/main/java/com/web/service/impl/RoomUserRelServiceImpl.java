package com.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.entity.RoomUserRel;
import com.web.example.RoomUserRelExample;
import com.web.mappers.RoomUserRelMapper;
import com.web.service.RoomUserRelService;

/**
 * 机房用户关联关系接口
 *
 * @author 田军兴
 * @date 2016-10-30
 */
@Service
@Transactional
public class RoomUserRelServiceImpl implements RoomUserRelService {
    @Autowired
    private RoomUserRelMapper mapper;

    @Override
    public List<RoomUserRel> selectByServiceRoomId(RoomUserRel roomUserRel) {
        return mapper.selectByServiceRoomId(roomUserRel.getServiceRoomId());
    }

    @Override
    public int deleteByServiceRoomId(String serviceRoomId) {
        return mapper.deleteByServiceRoomId(serviceRoomId);
    }

    @Override
    public RoomUserRel getOne(RoomUserRel roomUserRel) {
        RoomUserRelExample example = new RoomUserRelExample();
        RoomUserRelExample.Criteria criteria = example.createCriteria();
        criteria.andServiceRoomIdEqualTo(roomUserRel.getServiceRoomId());
        criteria.andUserIdEqualTo(roomUserRel.getUserId());
        List<RoomUserRel> lsit =  mapper.selectByExample(example);
        if(lsit.size() > 0){
           return lsit.get(0);
        }else{
         return null;
        }
    }

    @Override
    public int save(RoomUserRel roomUserRel) {
        return mapper.insert(roomUserRel);
    }

    @Override
    public int batchSave(String serviceRoomId, String[] userIds) {
        int result = 0;
        for(String userId : userIds){
            RoomUserRel rel = new RoomUserRel();
            rel.setServiceRoomId(serviceRoomId);
            rel.setUserId(userId);
            result = result + mapper.insertSelective(rel);
        }
        return result;
    }
}
