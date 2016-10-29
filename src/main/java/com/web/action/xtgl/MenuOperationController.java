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
import com.web.util.validation.GroupBuilder;
import com.web.util.validation.ValidationHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.web.util.AllResult.buildJSON;

/**
 * 菜单-->操作接口
 *
 * @author 杜延雷
 * @date 2016-09-12
 */
@RestController
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
	public Object add(MenuOperation menuOperation) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menuOperation: {}]", JSON.toJSONString(menuOperation));
		}

		//验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(menuOperation.getName()).notEmpty().maxLength(50), "名称必须提供且最大长度50位")
				.addGroup(GroupBuilder.build(menuOperation.getMenuId()).notEmpty().maxLength(32), "菜单ID必须提供且最大长度32位")
				// 非必输条件验证
				.addGroup(GroupBuilder.buildOr(menuOperation.getUrl()).empty().maxLength(255), "链接最大长度255位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}
		if(null == menuService.getById(menuOperation.getMenuId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单不存在");
		}

		try {
			//添加数据
			menuOperation.setId(UUIDGenerator.generatorRandomUUID());
			menuOperationService.save(menuOperation);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.menuOperation,
					JSON.toJSONString(menuOperation),OperLog.logLevelEnum.success);

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menuOperation,
					FastjsonUtils.newIgnorePropertyFilter("iconId","updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("save menuOperation object error. : {}", JSON.toJSONString(menuOperation), e);
			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.menuOperation,
					JSON.toJSONString(menuOperation),OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加操作失败");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public Object delete(String id, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [key: {}]", id);
		}

		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "ID必须提供");
		}

		try {
			MenuOperation menuOperation = menuOperationService.getById(id);
			if(null == menuOperation){
				return AllResult.build(1, "未找到相关操作信息");
			}

			menuOperationService.deleteById(id);

			operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.menuOperation,
					JSON.toJSONString(menuOperation),OperLog.logLevelEnum.success);

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("delete menuOperation object error. : {}", id, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.menuOperation, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,删除操作失败");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public Object update(MenuOperation menuOperation) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("params[menuOperation: {}]", JSON.toJSONString(menuOperation));
		}

		//验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(menuOperation.getId()).notEmpty().maxLength(32), "ID必须提供且最大长度32位")
				.addGroup(GroupBuilder.build(menuOperation.getName()).notEmpty().maxLength(50), "名称必须提供且最大长度50位")
				.addGroup(GroupBuilder.build(menuOperation.getMenuId()).notEmpty().maxLength(32), "菜单ID必须提供且最大长度32位")
				// 非必输条件验证
				.addGroup(GroupBuilder.buildOr(menuOperation.getUrl()).empty().maxLength(255), "链接最大长度255位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}
		if(null == menuService.getById(menuOperation.getMenuId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单不存在");
		}if(null == menuOperationService.getById(menuOperation.getId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单操作不存在");
		}

		try {
			menuOperationService.updateById(menuOperation);

			//添加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.menuOperation,
					JSON.toJSONString(menuOperation),OperLog.logLevelEnum.success);

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menuOperation,
					FastjsonUtils.newIgnorePropertyFilter("iconId","updateName","updateDate","createName","createDate"),
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
	public Object get(String id) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}

		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			MenuOperation menuOperation = menuOperationService.getById(id);

			if(null == menuOperation){
				return AllResult.build(1, "未查询到相关数据");
			}

			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuOperation, "查询条件key:"+id,OperLog.logLevelEnum.success);

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menuOperation,
					FastjsonUtils.newIgnorePropertyFilter("iconId","updateName","updateDate","createName","createDate"),
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
	public Object getMenuId(String menuId,HttpServletRequest request) {
		try {
			if (StringUtils.isEmpty(menuId)) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单ID不能为空");
			}
			List<MenuOperation> menuOperations = menuOperationService.getByMenuId(menuId);

			if(null == menuOperations || menuOperations.size() == 0){
				return AllResult.okJSON(menuOperations);
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(menuOperations,
					FastjsonUtils.newIgnorePropertyFilter("iconId","updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			// 增加日志
//			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuOperation,jsonStr,OperLog.logLevelEnum.success);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("menuOperations object error. getAll ", e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuOperation, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取菜单所有操作失败");
	}
}
