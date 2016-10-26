package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.DoMain;
import com.web.example.DoMainExample;
import com.web.mappers.DoMainMapper;
import com.web.service.DoMainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 域管理（组织结构） 逻辑接口实现类
 *
 * @author 杜延雷
 * @date 2016-10-22
 */
@Service
@Transactional
public class DoMainServiceImpl implements DoMainService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoMainServiceImpl.class) ;

    @Autowired
    DoMainMapper doMainMapper;

    @Override
    public int save(DoMain doMain) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save DoMain: {}", JSON.toJSONString(doMain));
        }

        if (null == doMain) {
            LOGGER.warn("the doMain object is null.");
            return 0 ;
        }

        // 插入记录
        int result = doMainMapper.insertSelective(doMain) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save doMain object result: {}", result);
        }

        return result;
    }

    @Override
    public int updateById(DoMain doMain) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update DoMain: {}", JSON.toJSONString(doMain));
        }

        if (null == doMain) {
            LOGGER.warn("the doMain object is null.");
            return 0 ;
        }

        // 更新记录
        int result = doMainMapper.updateByPrimaryKeySelective(doMain) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update doMain object result: {}", result);
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

        // 删除记录数
        int result = doMainMapper.deleteByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete doMain object by id result: {}", result);
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public DoMain getById(String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find doMain by id: {}", key);
        }

        if (StringUtils.isEmpty(key)) {
            LOGGER.warn("the doMain id object is null.");
            return null ;
        }

        // 查找实体对象
        DoMain doMain = doMainMapper.selectByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find muen object by id result: {}", JSON.toJSONString(doMain));
        }

        return doMain;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<DoMain> getAll() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get all doMain");
        }

        //查询所有域
        DoMainExample example = new DoMainExample();
        //排序
        example.setOrderByClause("CREATE_DATE");
        List<DoMain> doMainList = doMainMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get doMain all object count: {}", doMainList.size());
        }

        return doMainList;
    }

    /**
     * 分页处理
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Page<DoMain> getScrollData(int pageNum, int pageSize, DoMainExample example) {

        // 分页
        PageHelper.startPage(pageNum, pageSize) ;
        PageHelper.orderBy("CREATE_DATE");

        // 查询数据
        List<DoMain> doMains = doMainMapper.selectByExamplePage(example);
        Page<DoMain> page = new Page<>(doMains) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get doMain scroll object count: {}", page.getCount());
        }

        return page;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public int countByExample(DoMainExample example) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find count by: example{}", example.toString());
        }

        int result = doMainMapper.countByExample(example);

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<DoMain> getByParentId(String key){
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find doMain by parentID: {}", key);
        }

        //查询所有菜单
        DoMainExample example = new DoMainExample();
        DoMainExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isEmpty(key)){
            criteria.andLevelEqualTo(1);
        }else {
            criteria.andParentIdEqualTo(key);
        }

        List<DoMain> doMainList = doMainMapper.selectByExample(example);

        return doMainList;
    }

    /**
     * 根据传递参数返回树形结构
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<DoMain> getTree(Map<String,String> params) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find doMain by : params{}", params.toString());
        }
        // 查询数据
        List<DoMain> doMains = doMainMapper.selectByTree(params);
        return doMains;
    }

    @Override
    public List<DoMain> getByUserId(Map<String, String> params) {
        List<DoMain> doMains = doMainMapper.selectByUserId(params);
        return doMains;
    }
}
