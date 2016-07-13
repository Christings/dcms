package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.core.util.page.QueryResult;
import com.web.entity.Menu;
import com.web.example.MenuExample;
import com.web.mappers.MenuMapper;
import com.web.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 菜单 逻辑接口 实现类
 * @author 杜延雷
 * @date 2016-06-20
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class) ;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public int save(Menu menu) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save Menu: {}", JSON.toJSONString(menu));
        }

        if (null == menu) {
            LOGGER.warn("the wangdianItem object is null.");
            return 0 ;
        }

        // 插入记录
        int result = menuMapper.insertSelective(menu) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save Menu object result: {}", result);
        }

        return result;
    }

    @Override
    public int updateById(Menu menu) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update Menu: {}", JSON.toJSONString(menu));
        }

        if (null == menu) {
            LOGGER.warn("the wangdianItem object is null.");
            return 0 ;
        }

        // 更新记录
        int result = menuMapper.updateByPrimaryKeySelective(menu) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("update menu object result: {}", result);
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
        int result = menuMapper.deleteByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete menu object by id result: {}", result);
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Menu getById(String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find menu by id: {}", key);
        }

        if (StringUtils.isEmpty(key)) {
            LOGGER.warn("the menu id object is null.");
            return null ;
        }

        // 查找实体对象
        Menu menu = menuMapper.selectByPrimaryKey(key) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find muen object by id result: {}", JSON.toJSONString(menu));
        }

        return menu;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Menu> getAll() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get all menu");
        }

        //查询所有菜单
        MenuExample example = new MenuExample();
        //排序
        example.setOrderByClause("RANK");
        List<Menu> menuList = menuMapper.selectByExample(example);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get menu all object count: {}", menuList.size());
        }

        return menuList;
    }

    /**
     * 分页处理
     * @param pageCurrent
     * @param count
     * @param example
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public QueryResult<Menu> getScrollData(int pageCurrent, int count, MenuExample example) {

        //排序
        example.setOrderByClause("RANK");

        // 分页
        PageHelper.startPage(pageCurrent, count) ;

        // 查询数据
        List<Menu> menus = menuMapper.selectByExample(example);
        PageInfo<Menu> pageInfo = new PageInfo<>(menus) ;

        QueryResult<Menu> queryResult = new QueryResult<>(pageInfo.getList(), pageInfo.getTotal()) ;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("get menu scroll object count: {}", queryResult.getTotalRecord());
        }

        return queryResult;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Menu> getByParentId(String key){
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("find menu by parentID: {}", key);
        }

        //查询所有菜单
        MenuExample example = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isEmpty(key)){
            criteria.andParentIdIsNull();
        }else {
            criteria.andParentIdEqualTo(key);
        }

        //排序
        example.setOrderByClause("RANK");

        List<Menu> menuList = menuMapper.selectByExample(example);

        return menuList;
    }
}
