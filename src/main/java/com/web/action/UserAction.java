package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.core.action.BaseController;
import com.web.entity.User;
import com.web.util.StringUtil;

/**
 * 用户登录
 * 
 * @author qgl
 * 
 */
@Controller
public class UserAction extends BaseController {

	/**
	 * 网页登录
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login")
	public String login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		System.out.println(request.getParameterMap());
		if (StringUtil.isEmpty(username, password)) {
			request.setAttribute("errorMsg", "用户名或密码不能为空！");
		} else {
			User user = userService.getUserByName(username);
			if (null != user && null != user.getPassword() && user.getPassword().equals(password)) {
				request.setAttribute("user", user);
				return "index.jsp";
			} else {
				request.setAttribute("errorMsg", "用户名或密码错误！");
			}
		}
		return "login.jsp";
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
			User user = userService.getUserByName(username);
			if (null != user && null != user.getPassword() && user.getPassword().equals(password)) {
				obj.put("msg", "登录成功！");
				obj.put("status", "true");
				request.setAttribute("user", user);
			} else {
				obj.put("msg", "用户名或密码错误！");
				obj.put("status", "false");

			}
		}
		super.writerResponse(obj.toString(), response);
	}
}
