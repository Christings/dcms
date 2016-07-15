package com.web.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web.bean.MenuTree;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.Menu;
import com.web.example.MenuExample;
import com.web.service.MenuService;
import com.web.util.AllResult;
import com.web.util.UUIDGenerator;
import com.web.util.fastjson.FastjsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单接口
 *
 * @author 杜延雷
 * @date 2016-06-20
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	MenuService menuService;

	/**
	 * 添加菜单
	 *
	 * @param menu
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object add(Menu menu, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menu: {}]", JSON.toJSONString(menu));
		}

		// TODO 需要添加判断
		if (StringUtils.isEmpty(menu.getName())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单名称不能为空");
		}

		// 处理外键关联数据传空值问题
		if (null != menu.getParentId() && "".equals(menu.getParentId().trim())) {
			menu.setParentId(null);
		}

		try {
//			menu.setCreateName(WebUtils.getUser(request).getUserName());
//			menu.setCreateDate(new Date());
//			menu.setUpdateDate(menu.getCreateDate());
			menu.setId(UUIDGenerator.generatorRandomUUID());
			int result = menuService.save(menu);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", result);
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menu,FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"), SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("save menu object error. : {}", JSON.toJSONString(menu), e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加菜单失败");
	}

	/**
	 * 删除菜单
	 *
	 * @param key
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object delete(String key, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [key: {}]", key);
		}

		if (StringUtils.isEmpty(key)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			List<Menu> menuList = menuService.getByParentId(key);
			if (null != menuList && !menuList.isEmpty()) {
				return AllResult.build(0, "存在子菜单不允许删除");
			}
			int result = menuService.deleteById(key);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("delete result: {}", result);
			}

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("delete menu object error. : {}", key, e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,删除菜单失败");
	}

	/**
	 * 修改菜单信息
	 *
	 * @param menu
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object update(Menu menu, HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("params[menu: {}]", JSON.toJSONString(menu));
		}

		// TODO 需要添加判断 后期处理
		if (StringUtils.isEmpty(menu.getName())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单名称不能为空");
		}

		// 处理外键关联数据传空值问题
		if (null != menu.getParentId() && "".equals(menu.getParentId().trim())) {
			menu.setParentId(null);
		}

		try {
//			menu.setUpdateName(WebUtils.getUser(request).getUserName());
//			menu.setUpdateDate(new Date());
			int result = menuService.updateById(menu);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("update result: {}, after update menu: {}", result, JSON.toJSONString(menu));
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menu,FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"), SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("update menu object error. : menu: {}", JSON.toJSONString(menu), e);
		}
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,更新菜单信息失败");
	}

	/**
	 * 获取某个菜单信息
	 *
	 * @param key
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object get(String key, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [key: {}]", key);
		}

		if (StringUtils.isEmpty(key)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			Menu menu = menuService.getById(key);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("menu result: {}", menu);
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menu,FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("menu object error. id:{}", key, e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取菜单信息失败");
	}

	/**
	 * 获取根据父ID获取所有子ID
	 *
	 * @param key
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getParentId", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getParentId(String key, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [key: {}]", key);
		}

		try {
			List<Menu> menuList = menuService.getByParentId(key);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("menuList result: {}", JSON.toJSONString(menuList));
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menuList,FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("menus object error. id:{}", key, e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取菜单信息失败");
	}

	/**
	 * 获取所有菜单信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAll", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getAll(HttpServletRequest request) {
		try {
			List<Menu> menuList = menuService.getAll();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("menuList result: {}", JSON.toJSONString(menuList));
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menuList,FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("menus object error. getAll ", e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取所有菜单时失败");
	}

	/**
	 * 获取所有菜单信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tree", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getTree(String key, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [key: {}]", key);
		}

		try {
			// 根据条件查询某一级菜单（空时查询一级菜单；不等与空时查询该菜单下的所有子菜单）
			List<Menu> menuList = menuService.getByParentId(key);

			// 查询
			List<MenuTree> menuTreeList = treeMenu(menuList);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("menuList result: {}", JSON.toJSONString(menuList));
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menuTreeList,FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("menus object error. id:{}", key, e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取菜单信息失败");
	}

	/**
	 * 分页获取菜单信息
	 *
	 * @param pageNum
	 *            当前页
	 * @param pageSize
	 *            显示多少行
	 */
	@RequestMapping(value = "/scroll", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getScroll(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize,
			HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", pageNum, pageSize);
		}

		// 校验参数
		if (pageNum < 1 || pageSize < 1) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}

		try {
			MenuExample example = new MenuExample();

			Page<Menu> queryResult = menuService.getScrollData(pageNum, pageSize, example);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("queryResult record count: {}", queryResult.getRecords().size());
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get scroll data error. page: {}, count: {}", pageNum, pageSize, e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 查询所有菜单子子菜单
	 */
	private List<MenuTree> treeMenu(List<Menu> menus) {

		if (null == menus) {
			return null;
		}

		List<MenuTree> menuTrees = new ArrayList<MenuTree>(menus.size());

		for (Menu menu : menus) {
			MenuTree menuTree = MenuTree.convert(menu);
			List<Menu> menuList = menuService.getByParentId(menu.getId());

			if (null != menuList && menuList.size() > 0) {
				menuTree.setChildMenu(treeMenu(menuList));
			}
			menuTrees.add(menuTree);
		}

		return menuTrees;
	}
}
