package com.web.action;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.web.core.action.BaseController;
import com.web.entity.User;
import com.web.util.AllResult;
import com.web.util.MD5;
import com.web.util.UUIDGenerator;
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	@ResponseBody
	public Object addUser(@RequestBody User user, HttpServletRequest request){
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menu: {}]", JSON.toJSONString(user));
		}
		//TODO 需要添加判断
		if(StringUtils.isEmpty(user.getAccount())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:account用户账号不能为空");
		}else if(StringUtils.isEmpty(user.getPassword())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:password密码不能为空");
		}else if(StringUtils.isEmpty(user.getUserName())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userName密码不能为空");
		}

		try {
			user.setId(UUIDGenerator.generatorRandomUUID());
			user.setPassword(MD5.MD5Encode(user.getPassword()));
			int result=userService.saveUser(user);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", result);
			}
			return AllResult.okJSON(user);
		} catch (Exception e) {
			LOGGER.error("save User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加菜单失败") ;
		}
		
	}
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	@ResponseBody
	public void updateUser(@RequestBody User user,HttpServletRequest request){
		
	}

}