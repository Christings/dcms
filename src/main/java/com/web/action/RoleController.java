package com.web.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.web.core.action.BaseController;
import com.web.core.util.Page;
import com.web.entity.Role;
import com.web.entity.User;
import com.web.service.RoleSerivce;
import com.web.util.AllResult;
import com.web.util.UUIDGenerator;
import com.web.util.WebUtils;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private RoleSerivce roleService;
	/**
	 * 
	* @Title: save 
	* @Description: 添加Role
	* @param @param role
	* @param @param request
	* @param @return   参数 
	* @return Object    返回类型 
	* @throws 
	* @author  童云鹏
	* @date 2016年7月7日 下午3:12:25
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public Object save(@RequestBody Role role, HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [role: {}]", JSON.toJSONString(role));
		}
		//TODO 需要添加判断
		if(StringUtils.isEmpty(role.getRoleCode())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常不能为空");
		}else if(StringUtils.isEmpty(role.getRoleName())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常不能为空");
		}

		try {
			role.setId(UUIDGenerator.generatorRandomUUID());
			role.setCreateName(WebUtils.getUser(request).getUserName());
			role.setCreateDate(new Date());
			int result=roleService.save(role);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", result);
			}
			return AllResult.okJSON(role);
		} catch (Exception e) {
			LOGGER.error("save Role fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加角色失败") ;
		}
	}
	/**
	 * 
	* @Title: update 
	* @Description: 修改role根据ID
	* @param @param role
	* @param @param request
	* @param @return   参数 
	* @return Object    返回类型 
	* @throws 
	* @author  童云鹏
	* @date 2016年7月7日 下午3:12:01
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Object update(@RequestBody Role role, HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [role: {}]", JSON.toJSONString(role));
		}
		//TODO 需要添加判断
		if(StringUtils.isEmpty(role.getRoleCode())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常不能为空");
		}else if(StringUtils.isEmpty(role.getRoleName())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常不能为空");
		}

		try {
			//role.setId(UUIDGenerator.generatorRandomUUID());
			role.setUpdateName(WebUtils.getUser(request).getUserName());
			role.setUpdateCreate(new Date());
			int result=roleService.updateById(role);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", result);
			}
			return AllResult.okJSON(role);
		} catch (Exception e) {
			LOGGER.error("save Role fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改角色失败") ;
		}
	}
	
	/**
	 * 
	* @Title: getAll 
	* @Description: 查询所有的Role
	* @param @param request
	* @param @return   参数 
	* @return Object    返回类型 
	* @throws 
	* @author  童云鹏
	* @date 2016年7月7日 下午3:11:31
	 */
	@RequestMapping(value="/getAll",method=RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request){
		try {
			
			List<Role> list=roleService.getAll();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", list);
			}
			return AllResult.okJSON(list);
		} catch (Exception e) {
			LOGGER.error("save Role fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,查询角色失败") ;
		}
	}
	
	@RequestMapping(value="/getRolesByPage",method=RequestMethod.GET)
	@ResponseBody
	public Object getRolesByPage(@RequestBody Page<Role> page,HttpServletRequest request){
		page.setPageNo(1);
		page.setPageSize(10);
		try {
			
			List<Role> list=roleService.getByPage(page);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", list);
			}
			return AllResult.okJSON(page);
		} catch (Exception e) {
			LOGGER.error("save Role fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,查询角色失败") ;
		}
	}
	/**
	 * 
	* @Title: getRoleById 
	* @Description: 根据ID查询Role
	* @param @param id
	* @param @param request
	* @param @return   参数 
	* @return Object    返回类型 
	* @throws 
	* @author  童云鹏
	* @date 2016年7月7日 下午3:10:41
	 */
	@RequestMapping(value="/getRoleById/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Object getRoleById(@PathVariable String id, HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [role: {}]", JSON.toJSONString(id));
		}
		//TODO 需要添加判断
		if(StringUtils.isEmpty(id)){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常不能为空");
		}

		try {
			
			Role role=roleService.getById(id);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", role);
			}
			return AllResult.okJSON(role);
		} catch (Exception e) {
			LOGGER.error("save Role fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,查询角色失败") ;
		}
	}
}
