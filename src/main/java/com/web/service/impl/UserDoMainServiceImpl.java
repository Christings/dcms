package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.web.core.exception.BusinessException;
import com.web.entity.UserDoMain;
import com.web.example.UserDoMainExample;
import com.web.mappers.UserDoMainMapper;
import com.web.service.UserDoMainService;
import com.web.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户域关系 逻辑接口 实现类
 *
 * @author 杜延雷
 * @date 2016-10-22
 */
@Service
@Transactional
public class UserDoMainServiceImpl implements UserDoMainService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDoMainServiceImpl.class) ;

    @Autowired
    UserDoMainMapper userDoMainMapper;

    @Override
    public int save(UserDoMain userDomain) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save UserDoMain: {}", JSON.toJSONString(userDomain));
        }

        if (null == userDomain) {
            LOGGER.warn("the UserDoMain object is null.");
            return 0 ;
        }

        //删除原有的
        UserDoMainExample example = new UserDoMainExample();
        UserDoMainExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userDomain.getUserId());
        criteria.andDomainIdEqualTo(userDomain.getDomainId());
        userDoMainMapper.deleteByExample(example);

        // 插入记录
        int result = userDoMainMapper.insertSelective(userDomain) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save UserDoMain object result: {}", result);
        }

        return result;
    }

    @Override
    public int updateById(UserDoMain userDomain) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update UserDoMain: {}", JSON.toJSONString(userDomain));
        }

        if (null == userDomain) {
            LOGGER.warn("the userDomain object is null.");
            return 0 ;
        }

        UserDoMainExample example = new UserDoMainExample();
        UserDoMainExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userDomain.getId());

        // 更新记录
        int result = userDoMainMapper.updateByExampleSelective(userDomain,example) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update userDomain object result: {}", result);
        }

        return result;
    }

    @Override
    public int deleteById(String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete key by id: {}", key);
        }

        if (StringUtils.isEmpty(key)) {
            LOGGER.warn("the key id object is null.");
            return 0 ;
        }

        UserDoMainExample example = new UserDoMainExample();
        UserDoMainExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(key);

        // 删除记录数
        int result = userDoMainMapper.deleteByExample(example) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete userDomain object by id result: {}", result);
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public UserDoMain getById(String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find userDomain by id: {}", key);
        }

        if (StringUtils.isEmpty(key)) {
            LOGGER.warn("the menu id object is null.");
            return null ;
        }

        UserDoMainExample example = new UserDoMainExample();
        UserDoMainExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(key);

        // 查找实体对象
        List<UserDoMain> userDomains = userDoMainMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find userDomains object by id result: {}", JSON.toJSONString(userDomains));
        }

        return null == userDomains ? null : userDomains.get(0);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<UserDoMain> getAll() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get all UserDoMain");
        }

        //查询所有操作
        UserDoMainExample example = new UserDoMainExample();
        List<UserDoMain> userDomains = userDoMainMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get UserDoMains all object count: {}", userDomains.size());
        }

        return userDomains;
    }

    @Override
    public void batchUserDoMain(String userId, String[] domainIds) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("param userId: {}, domainIds:{}", userId,domainIds);
        }

        try {
            //设置删除条件 并删除原有关系
            UserDoMainExample example = new UserDoMainExample();
            UserDoMainExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(userId);
            userDoMainMapper.deleteByExample(example);

            //批量插入记录
            UserDoMain udm = null;
            for(String dmId:domainIds){
                udm = new UserDoMain();
                udm.setId(UUIDGenerator.generatorRandomUUID());
                udm.setUserId(userId);
                udm.setDomainId(dmId);
                userDoMainMapper.insertSelective(udm);
            }

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("batch Save UserDoMain object result: {}");
            }
        }catch (Exception e){
            throw new BusinessException("批量用户保存组织结构失败",e);
        }
    }

    @Override
    public List<UserDoMain> getUserDomain(String userId) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("param userId: {}", userId);
        }

        UserDoMainExample example = new UserDoMainExample();
        UserDoMainExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<UserDoMain> userDomains = userDoMainMapper.selectByExample(example);

        return userDomains;
    }

    @Override
    public int delete(UserDoMainExample example) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete condition example by id: {}", example);
        }

        // 删除记录数
        int result = userDoMainMapper.deleteByExample(example) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete condition object by id result: {}", result);
        }
        return result;
    }
}
