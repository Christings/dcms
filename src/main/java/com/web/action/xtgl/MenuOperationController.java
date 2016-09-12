package com.web.action.xtgl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web.core.action.BaseController;
import com.web.entity.MenuOperation;
import com.web.entity.OperLog;
import com.web.service.MenuOperationService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 菜单-->操作接口
 *
 * @author 杜延雷
 * @date 2016-06-20
 */
@Controller
@RequestMapping("/menu/operation")
public class MenuOperationController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuOperationController.class);

	@Autowired
	MenuService menuService;
	@Autowired
	MenuOperationService menuOperationService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object add(MenuOperation menuOperation, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menuOperation: {}]", JSON.toJSONString(menuOperation));
		}

		// TODO 需要添加判断
		if (StringUtils.isEmpty(menuOperation.getName())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "名称不能为空");
		}else if(StringUtils.isEmpty(menuOperation.getMenuId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单ID不能为空");
		}else if(null == menuService.getById(menuOperation.getMenuId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单ID不存在");
		}

		try {
			menuOperation.setId(UUIDGenerator.generatorRandomUUID());
			int result = menuOperationService.save(menuOperation);

			// 增加日志
//			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.menu,
//					JSON.toJSONString(menuOperation));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", result);
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menuOperation,
					FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("save menuOperation object error. : {}", JSON.toJSONString(menuOperation), e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加操作失败");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object delete(String id, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [key: {}]", id);
		}

		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			MenuOperation menuOperation = menuOperationService.getById(id);
			if(null == menuOperation){
				return AllResult.build(1, "操作不存在");
			}

			int result = menuOperationService.deleteById(id);

			if(result > 0){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.menuOperation,
						JSON.toJSONString(menuOperation));
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("delete result: {}", result);
			}

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("delete menuOperation object error. : {}", id, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.menu, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,删除操作失败");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object update(MenuOperation menuOperation, HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("params[menuOperation: {}]", JSON.toJSONString(menuOperation));
		}

		// TODO 需要添加判断 后期处理
		if(StringUtils.isEmpty(menuOperation.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "ID不能为空");
		}else if (StringUtils.isEmpty(menuOperation.getName())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "名称不能为空");
		}else if(StringUtils.isEmpty(menuOperation.getMenuId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单ID不能为空");
		}else if(null == menuOperationService.getById(menuOperation.getId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单操作不存在");
		}

		try {
			int result = menuOperationService.updateById(menuOperation);

			if(result > 0){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.menuOperation,
						JSON.toJSONString(menuOperation));
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("update result: {}, after update menuOperation: {}", result, JSON.toJSONString(menuOperation));
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menuOperation,
					FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("update menu object error. : menu: {}", JSON.toJSONString(menuOperation), e);
			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.menu, null,
					OperLog.logLevelEnum.error);
		}
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,更新菜单信息失败");
	}

	/**
	 * 详情
	 */
	@RequestMapping(value = "/get", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object get(String id, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [key: {}]", id);
		}

		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			MenuOperation menuOperation = menuOperationService.getById(id);

			if(null == menuOperation){
				return AllResult.build(1, "未查询到相关数据");
			}

			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuOperation, "查询条件key:"+id);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("menu result: {}", menuOperation);
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menuOperation,
					FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("menuOperation object error. id:{}", id, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuOperation, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取详情信息失败");
	}

	/**
	 * 菜单所有操作
	 */
	@RequestMapping(value = "/getMenuId", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getMenuId(String menuId,HttpServletRequest request) {
		try {
			if (StringUtils.isEmpty(menuId)) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单ID不能为空");
			}
			List<MenuOperation> menuOperations = menuOperationService.getByMenuId(menuId);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("menuOperations result: {}", JSON.toJSONString(menuOperations));
			}

			if(null == menuOperations || menuOperations.size() == 0){
				return AllResult.okJSON(menuOperations);
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menuOperations,
					FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuOperation,jsonStr);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("menuOperations object error. getAll ", e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuOperation, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取菜单所有操作失败");
	}
}
