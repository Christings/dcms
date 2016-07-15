package com.web.action;

import com.alibaba.fastjson.JSONObject;
import com.web.core.action.BaseController;
import com.web.entity.OperLog;
import com.web.entity.User;
import com.web.util.AllResult;
import com.web.util.MD5;
import com.web.util.StringUtil;
import com.web.util.WebUtils;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录
 * 
 * @author qgl
 * 
 */
@Controller
@RequestMapping("/main")
public class LoginController extends BaseController {
	private Logger LOGGER = Logger.getLogger(LoginController.class);

	/**
	 * web网页登录
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public Object login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
//		System.out.println(request.getParameterMap());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("params[username: {}, password: {}]"+ username +","+ password);
		}
		if (StringUtil.isEmpty(username, password)) {
			return AllResult.build(0, "用户名或密码不能为空");
		}
        try {
        	User user = userService.getUserByName(username);
    	 	if (null == user ) {
    			return AllResult.build(0, "该用户不存在!");
    	 	}

    		if(!MD5.MD5Encode(password).equals(user.getPassword())){
    			return AllResult.build(0, "密码输入错误!");
    		}

    		WebUtils.addUser(request, user);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.login,null);
    		return AllResult.okJSON(user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,登录失败") ;
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
	@ResponseBody
	public Object loginOut(HttpServletRequest request, HttpServletResponse response) {
		WebUtils.removeUser(request);
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
}
