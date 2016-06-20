package com.web.action;

import com.web.core.action.BaseController;
import com.web.entity.User;
import com.web.util.MD5;
import com.web.util.StringUtil;
import com.web.util.WebUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			//TODO 后期需要修改
//			User user = userService.getUserByName(username);
//			if (null != user && null != user.getPassword() && user.getPassword().equals(password)) {
//				request.setAttribute("user", user);
//				return "index.jsp";
//			} else {
//				request.setAttribute("errorMsg", "用户名或密码错误！");
//			}

			User user = new User();
			user.setUsername(username);
			user.setPassword(MD5.MD5Encode(password));
			WebUtils.addUser(request,user);
			return "index.html"; //登录成功后跳转的页面
		}
		return "index.html";
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
