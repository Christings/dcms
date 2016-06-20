package com.web.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.web.core.action.BaseController;
import com.web.entity.Menu;
import com.web.service.MenuService;
import com.web.util.AllResult;
import com.web.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 菜单获取
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
	 * @param menu
	 * @param request
     * @return
     */
	@RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object add(Menu menu, HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menu: {}]", JSON.toJSONString(menu));
		}

		//TODO 需要添加判断
		if(StringUtils.isEmpty(menu.getName())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			menu.setId(UUIDGenerator.generatorRandomUUID());
			int result = menuService.save(menu);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", result);
			}

			return AllResult.okJSON(menu);
		} catch (Exception e) {
			LOGGER.error("save menu object error. : {}", JSON.toJSONString(menu), e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加菜单失败") ;
	}

	/**
	 * 删除菜单
	 * @param key
	 * @param request
     * @return
     */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object delete(String key, HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [key: {}]", key);
		}

		if(StringUtils.isEmpty(key)){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			int result = menuService.deleteById(key);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("delete result: {}", result);
			}

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("delete menu object error. : {}", key, e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,删除菜单失败") ;
	}

	/**
	 * 修改菜单信息
	 * @param menu
	 * @param request
     * @return
     */
	@RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object update(Menu menu,HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("params[menu: {}]", JSON.toJSONString(menu));
		}

		//TODO 后期调整需要添加 验证信息
		if (StringUtils.isEmpty(menu.getId()) || StringUtils.isEmpty(menu.getName()) ) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}

		try {
			int result = menuService.updateById(menu);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("update result: {}, after update menu: {}", result, JSON.toJSONString(menu));
			}

			return AllResult.okJSON(menu);
		} catch (Exception e) {
			LOGGER.error("update menu object error. : menu: {}", JSON.toJSONString(menu), e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,更新菜单信息失败");
	}

	/**
	 * 获取某个菜单信息
	 * @param key
	 * @param request
     * @return
     */
	@RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object get(String key,HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [key: {}]", key);
		}

		if(StringUtils.isEmpty(key)){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			Menu menu = menuService.getById(key);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("menu result: {}", menu);
			}

			return AllResult.okJSON(menu);
		} catch (Exception e) {
			LOGGER.error("menu object error. id:{}", key, e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取菜单信息失败") ;
	}

	/**
	 * 获取根据父ID获取所有子ID
	 * @param key
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getParentId", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object getParentId(String key,HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [key: {}]", key);
		}

		if(StringUtils.isEmpty(key)){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			List<Menu> menuList= menuService.getByParentId(key);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("menuList result: {}", JSON.toJSONString(menuList));
			}

			return AllResult.okJSON(menuList);
		} catch (Exception e) {
			LOGGER.error("menus object error. id:{}", key, e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取菜单信息失败") ;
	}

	/**
	 * 获取所有菜单信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAll", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object getAll(HttpServletRequest request){
		try {
			List<Menu> menuList= menuService.getAll();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("menuList result: {}", JSON.toJSONString(menuList));
			}

			return AllResult.okJSON(menuList);
		} catch (Exception e) {
			LOGGER.error("menus object error. getAll ", e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取所有菜单时失败") ;
	}
}
