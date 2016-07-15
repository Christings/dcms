package com.web.action;

import com.alibaba.fastjson.JSON;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.Role;
import com.web.example.RoleExample;
import com.web.service.RoleSerivce;
import com.web.util.AllResult;
import com.web.util.UUIDGenerator;
import com.web.util.WebUtils;
import com.web.util.fastjson.FastjsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
			role.setUpdateDate(new Date());
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
	


	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param request
     * @return
     */
	@RequestMapping(value="/scroll",method= { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getScroll(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize,
							HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", pageNum, pageSize);
		}

		// 校验参数
		if (pageNum < 1 || pageSize < 1) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}

		try {

			RoleExample example = new RoleExample();
			// 排序设置
			// example.setOrderByClause("UPDATE_DATETIME DESC");
			RoleExample.Criteria criteria = example.createCriteria();
			// 条件设置
			// criteria.andIconIdIsNull();

			Page<Role> queryResult = roleService.getScrollData(pageNum, pageSize, example);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("queryResult record count: {}", queryResult.getRecords().size());
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult, FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get scroll data error. page: {}, count: {}", pageNum, pageSize, e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
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
