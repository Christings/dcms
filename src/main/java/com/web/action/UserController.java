package com.web.action;

import java.util.Date;

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
import com.web.util.WebUtils;
/**
 * 用户管理Controller
* @ClassName: UserController 
* @Description: TODO
* @author 童云鹏 
* @date 2016年7月5日 上午9:58:51
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * 新增用户
	* @Title: addUser 
	* @Description: TODO
	* @param @param user
	* @param @param request
	* @param @return   参数 
	* @return Object    返回类型 
	* @throws 
	* @author  童云鹏
	* @date 2016年7月5日 上午9:59:16
	 */
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
			user.setCreateName(WebUtils.getUser(request).getUserName());
			user.setCreateDate(new Date());
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
	/**
	 * 修改用户
	* @Title: updateUser 
	* @Description: TODO
	* @param @param user
	* @param @param request
	* @param @return   参数 
	* @return Object    返回类型 
	* @throws 
	* @author  童云鹏
	* @date 2016年7月5日 上午9:59:38
	 */
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	@ResponseBody
	public Object updateUser(@RequestBody User user,HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menu: {}]", JSON.toJSONString(user));
		}
		//TODO 需要添加判断
		if(StringUtils.isEmpty(user.getAccount())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:account用户账号不能为空");
		}else if(StringUtils.isEmpty(user.getPassword())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:password密码不能为空");
		}else if(StringUtils.isEmpty(user.getUserName())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userName用户名不能为空");
		}else if(StringUtils.isEmpty(user.getId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userId不能为空");
		}

		try {
			//user.setId(UUIDGenerator.generatorRandomUUID());
			//user.setPassword(MD5.MD5Encode(user.getPassword()));
			user.setUpdateName(WebUtils.getUser(request).getUserName());
			user.setUpdateCreate(new Date());
			int result=userService.updateUser(user);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", result);
			}
			return AllResult.okJSON(user);
		} catch (Exception e) {
			LOGGER.error("save User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加菜单失败") ;
		}
	}
	
	/**
	 * 
	* @Title: updateUserEnabled 
	* @Description: 设置用户是否启用
	* @param @param user
	* @param @param request
	* @param @return   参数 
	* @return Object    返回类型 
	* @throws 
	* @author  童云鹏
	* @date 2016年7月5日 上午10:04:40
	 */
	@RequestMapping(value="/updateUserEnabled",method=RequestMethod.POST)
	@ResponseBody
	public Object updateUserEnabled(@RequestBody User user,HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menu: {}]", JSON.toJSONString(user));
		}
		//TODO 需要添加判断
		 if(StringUtils.isEmpty(user.getId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userId不能为空");
		}

		try {
			user.setUpdateName(WebUtils.getUser(request).getUserName());
			user.setUpdateCreate(new Date());
			//user.setId(UUIDGenerator.generatorRandomUUID());
			//user.setPassword(MD5.MD5Encode(user.getPassword()));
			userService.updateUserDelete(user.getEnabled(), user.getId());
			return AllResult.okJSON(user);
		} catch (Exception e) {
			LOGGER.error("updateUserEnabled User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加菜单失败") ;
		}
	}
	
	/**
	 * 
	* @Title: updateUserDelete 
	* @Description: 用户逻辑删除
	* @param @param user
	* @param @param request
	* @param @return   参数 
	* @return Object    返回类型 
	* @throws 
	* @author  童云鹏
	* @date 2016年7月5日 上午10:05:35
	 */
	@RequestMapping(value="/updateUserDelete",method=RequestMethod.POST)
	@ResponseBody
	public Object updateUserDelete(@RequestBody User user,HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menu: {}]", JSON.toJSONString(user));
		}
		//TODO 需要添加判断
		if(StringUtils.isEmpty(user.getId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userId不能为空");
		}

		try {
			user.setUpdateName(WebUtils.getUser(request).getUserName());
			user.setUpdateCreate(new Date());
			//user.setId(UUIDGenerator.generatorRandomUUID());
			//user.setPassword(MD5.MD5Encode(user.getPassword()));
			userService.updateUserDelete(user.getDeleted(), user.getId());
			
			return AllResult.okJSON(user);
		} catch (Exception e) {
			LOGGER.error("delete User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加菜单失败") ;
		}
	}
	

}
