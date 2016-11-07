package com.web.action.xtgl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.web.core.action.BaseController;
import com.web.entity.Menu;
import com.web.entity.MenuOperation;
import com.web.entity.MenuRole;
import com.web.entity.OperLog;
import com.web.entity.User;
import com.web.entity.UserRole;
import com.web.service.MenuOperationService;
import com.web.service.MenuRoleService;
import com.web.service.MenuService;
import com.web.service.UserRoleService;
import com.web.util.AllResult;
import com.web.util.MD5;
import com.web.util.StringUtil;
import com.web.util.WebUtils;

/**
 * 用户登录
 * 
 * @author qgl
 * 
 */
@RestController
@RequestMapping("/main")
public class LoginController extends BaseController {
	private Logger LOGGER = Logger.getLogger(LoginController.class);

	@Autowired
	UserRoleService userRoleService; // 用户角色关系 接口
	@Autowired
	MenuRoleService menuRoleService; // 菜单角色关系 接口
	@Autowired
	MenuService menuService; // 菜单接口
	@Autowired
	MenuOperationService menuOperationService; // 菜单操作接口

	/**
	 * web网页登录
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object login(String username, String password, String code, HttpServletRequest request, HttpServletResponse response) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("params[username: {}, password: {}]" + username + "," + password);
		}

		// 验证码校验
		String randCode = WebUtils.getCode(request);
		if (StringUtil.isEmpty(code) || StringUtil.isEmpty(randCode) || !randCode.equalsIgnoreCase(code)) {
			return AllResult.build(0, "验证码错误！");
		}
		if (StringUtil.isEmpty(username, password)) {
			return AllResult.build(0, "用户名或密码不能为空");
		}
		try {
			User user = userService.getUserByName(username);
			if (null == user) {
				return AllResult.build(0, "该用户不存在!");
			}

			if (user.getStatus().intValue() == 1) {
				return AllResult.build(0, "该用户已经被禁用!");
			} else if (user.getStatus().intValue() != 0) {
				return AllResult.build(0, "该用户已经被删除!");
			}

			if (!MD5.MD5Encode(password).equals(user.getPassword())) {
				return AllResult.build(0, "密码输入错误!");
			}

			// 设置用户Session信息
			setUserSession(request, user);

			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.login, null);
			return AllResult.okJSON(user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,登录失败");
		}

	}

	/**
	 * 退出登录
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginOut")
	public Object loginOut(HttpServletRequest request, HttpServletResponse response) {
		WebUtils.removeAll(request); // 退出时删除用户Session信息
		return AllResult.build(1, "退出登录成功");
	}

	/**
	 * APP登录
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 */
	@RequestMapping("/app/login")
	public void appLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		if (StringUtil.isEmpty(username, password)) {
			obj.put("msg", "用户名或密码不能为空！");
			obj.put("status", "false");
		} else {
			try {
				User user = userService.getUserByName(username);
				if (null != user && null != user.getPassword() && user.getPassword().equals(password)) {
					obj.put("msg", "登录成功！");
					obj.put("status", "true");
					request.setAttribute("user", user);
				} else {
					obj.put("msg", "用户名或密码错误！");
					obj.put("status", "false");

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				obj.put("msg", "系统异常，登录失败！");
				obj.put("status", "false");
				super.writerResponse(obj.toString(), response);
			}

		}
		super.writerResponse(obj.toString(), response);
	}

	/**
	 * 设置用户Session信息
	 *
	 * @param request
	 * @param user
	 */
	private void setUserSession(HttpServletRequest request, User user) {
		Set<String> privileges = new LinkedHashSet<>();
		Set<String> menuIds = new LinkedHashSet<>();
		if ("admin".equals(user.getUsername())) {
			List<Menu> menus = menuService.getAll();
			List<MenuOperation> menuOperations = menuOperationService.getAll();
			for (Menu menu : menus) {
				privileges.add(menu.getUrl());
				menuIds.add(menu.getId());
			}
			for (MenuOperation mo : menuOperations) {
				privileges.add(mo.getUrl());
			}
		} else {
			// 1.查询用户权限URL
			List<UserRole> userRoles = userRoleService.getUserRole(user.getId());
			if (!CollectionUtils.isEmpty(userRoles)) {
				for (UserRole ur : userRoles) {
					List<MenuRole> menuRoles = menuRoleService.getRoleMenu(ur.getRoleId());
					if (!CollectionUtils.isEmpty(menuRoles)) {
						for (MenuRole mr : menuRoles) {
							// TODO 后期优化查询 用SQL实现 目前用循环会慢（回头改）
							Menu menu = menuService.getById(mr.getMenuId());
							privileges.add(menu.getUrl());
							menuIds.add(menu.getId());
							if (StringUtils.isNotEmpty(mr.getOperationId())) {
								String[] ids = mr.getOperationId().split(",");
								for (int i = 0; i < ids.length; i++) {
									privileges.add(menuOperationService.getById(ids[i]).getUrl());
								}
							}
						}
					}
				}
			}
		}

		// 2.设置用户信息
		WebUtils.addUser(request, user);
		// 3.设置菜单ID
		WebUtils.addMenuIds(request, menuIds);
		// 4.设置权限URL
		WebUtils.addPrivilege(request, privileges);
	}

}
