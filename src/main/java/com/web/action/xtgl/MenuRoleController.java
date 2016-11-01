package com.web.action.xtgl;

import com.alibaba.fastjson.JSON;
import com.web.core.action.BaseController;
import com.web.entity.MenuRole;
import com.web.entity.OperLog;
import com.web.service.MenuRoleService;
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
 * 菜单角色关系接口
 *
 * @author 杜延雷
 * @date 2016-07-12
 */
@RestController
@RequestMapping("/menu/role")
public class MenuRoleController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuRoleController.class);

	@Autowired
	MenuRoleService menuRoleService;

	/**
	 * 添加菜单角色
	 *
	 * @param menuRole
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object add(MenuRole menuRole, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menuRole: {}]", JSON.toJSONString(menuRole));
		}

		// TODO 需要添加判断
		if (StringUtils.isEmpty(menuRole.getRoleId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID不能为空");
		}else if(StringUtils.isEmpty(menuRole.getMenuId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单ID不能为空");
		}

		try {
			menuRole.setId(UUIDGenerator.generatorRandomUUID());
			int result = menuRoleService.save(menuRole);

			if(result > 0){
				operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.menuRole,
						JSON.toJSONString(menuRole));
			}

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("save menuRole object error. : {}", JSON.toJSONString(menuRole), e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加角色-菜单关系失败");
	}

	/**
	 * 根据角色批量保存菜单
	 *
	 * @param {roleId,menuIds,request}
	 * @return
	 */
	@RequestMapping(value = "/batchMenus", method = RequestMethod.POST)
	public Object batchMenus(String roleId,String menuIds, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [roleId: {},menuIds:{}]",roleId,menuIds );
		}

		// TODO 需要添加判断
		if (StringUtils.isEmpty(roleId)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID不能为空");
		}else if(StringUtils.isEmpty(menuIds)){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单ID不能为空");
		}

		String [] menuIdArr = menuIds.split(",");
		if(menuIdArr.length<=0){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单ID传递有误,格式[菜单ID1,菜单ID2,...]");
		}

		try {
			menuRoleService.batchRoleMenu(roleId,menuIdArr);

			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.menuRole,
					"角色ID："+roleId+",菜单IDs:["+menuIdArr+"]");

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("batchMenus save object error. : {}", e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,根据角色ID批量保存菜单失败");
	}

	/**
	 * 根据菜单批量保存角色
	 *
	 * @param {menuId,roleIds,request}
	 * @return
	 */
	@RequestMapping(value = "/batchRoles", method = RequestMethod.POST)
	public Object batchRoles(String menuId,String roleIds) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menuId: {},roleIds:{}]",menuId,roleIds );
		}

		// TODO 需要添加判断
		if (StringUtils.isEmpty(menuId)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单ID不能为空");
		}else if(StringUtils.isEmpty(roleIds)){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID不能为空");
		}

		String [] reloIdArr = roleIds.split(",");
		if(reloIdArr.length<=0){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID传递有误,格式[角色ID1,角色ID2,...]");
		}

		try {
			menuRoleService.batchMenuRole(menuId,reloIdArr);

			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.menuRole,
					"菜单ID："+menuId+",角色IDs:["+reloIdArr+"]");

			return AllResult.okJSON("保存成功");
		} catch (Exception e) {
			LOGGER.error("batchRoles save object error. : {}", e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,根据角色ID批量保存菜单失败");
	}

	/**
	 * 根据角色Id 获取所有角色菜单关系数据
	 *
	 * @return
	 */
	@RequestMapping(value = "/getRoleId", method = RequestMethod.POST)
	public Object getRoleId(String roleId) {
		try {
			if(StringUtils.isEmpty(roleId)){
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色ID不能为空");
			}

			List<MenuRole> menuRoleList = menuRoleService.getRoleMenu(roleId);

			if(null == menuRoleList || menuRoleList.size() == 0){
				return AllResult.build(1, "未查询到相关数据");
			}

			// 增加日志
//			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuRole,null);

			String jsonStr = JSON.toJSONString(menuRoleList, FastjsonUtils.newIgnorePropertyFilter("operationId"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("menuRole object error. getMenuRoleList ", e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuRole, null,
					OperLog.logLevelEnum.error);

		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取角色菜单时失败");
	}

	/**
	 * 根据角色Id和菜单ID获取关系
	 *
	 * @return
	 */
	@RequestMapping(value = "/getRoleIdAndMenuId", method = {RequestMethod.POST,RequestMethod.GET})
	public Object getRoleIdAndMenuId(String roleId,String menuId) {
		//验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(roleId).notEmpty().maxLength(32), "角色ID必须提供且长度最大32位")
				.addGroup(GroupBuilder.build(menuId).notEmpty().maxLength(32), "菜单ID必须提供且最大长度32位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {

			List<MenuRole> menuRoleList = menuRoleService.getRoleMenuOperation(roleId,menuId);

//			if(null == menuRoleList || menuRoleList.size() == 0){
//				return AllResult.build(1, "未查询到相关数据");
//			}

			// 增加日志
//			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuRole,null);

			return AllResult.okJSON(menuRoleList);
		} catch (Exception e) {
			LOGGER.error("menuRole object error. getMenuRoleList ", e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuRole, null,
					OperLog.logLevelEnum.error);

		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取角色菜单时失败");
	}

	/**
	 * 根据菜单Id 获取所有菜单角色关系数据
	 *
	 * @return
	 */
	@RequestMapping(value = "/getMenuId", method = RequestMethod.POST)
	public Object getMenuId(String menuId) {
		try {
			if(StringUtils.isEmpty(menuId)){
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "菜单ID不能为空");
			}

			List<MenuRole> menuRoleList = menuRoleService.getMenuRole(menuId);

			if(null == menuRoleList || menuRoleList.size() == 0){
				return AllResult.build(1, "未查询到相关数据");
			}

			// 增加日志
//			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuRole,null);

			String jsonStr = JSON.toJSONString(menuRoleList, FastjsonUtils.newIgnorePropertyFilter("operationId"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("menuRole object error. getMenuRoleList ", e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuRole, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取菜单角色时失败");
	}

	/**
	 * 获取所有菜单角色关系
	 *
	 * @return
	 */
	@RequestMapping(value = "/getAll", method = {RequestMethod.GET,RequestMethod.POST})
	public Object getAll() {
		try {
			List<MenuRole> menuRoleList = menuRoleService.getAll();

			if(null == menuRoleList || menuRoleList.size() == 0){
				return AllResult.build(1, "未查询到相关数据");
			}

			// 增加日志
//			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuRole,null);

			return AllResult.okJSON(menuRoleList);
		} catch (Exception e) {
			LOGGER.error("menuRole object error. getAll ", e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menuRole, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取所有角色菜单失败");
	}
}
