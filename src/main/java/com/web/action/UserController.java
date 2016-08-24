package com.web.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.User;
import com.web.example.UserExample;
import com.web.util.AllResult;
import com.web.util.MD5;
import com.web.util.UUIDGenerator;
import com.web.util.fastjson.FastjsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户管理接口
 * 
 * @author 杜延雷
 * @date 2016-08-09
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	/**
	 * 添加用户
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object addUser(User user, HttpServletRequest request) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [user: {}]", JSON.toJSONString(user));
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(user.getUsername()) || "".equals(user.getUsername().trim())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "登录名不能为空");
		} else if (StringUtils.isEmpty(user.getPassword())|| "".equals(user.getPassword().trim())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户密码不能为空");
		} else if (StringUtils.isEmpty(user.getRealname())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "真实名不能为空");
		} else if(null == user.getStatus()){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "状态输入有误,请检查");
		}

		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(user.getUsername());
		if(userService.count(example)>0){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "登录名已经存在不可以重复");
		}

		try {
			user.setId(UUIDGenerator.generatorRandomUUID());
			user.setPassword(MD5.MD5Encode(user.getPassword()));
			int result = userService.save(user);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.user,
					JSON.toJSONString(user));

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", result);
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(user,
					FastjsonUtils.newIgnorePropertyFilter("password","updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加用户失败");
		}

	}

	/**
	 * 修改用户
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object update(User user, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [user: {}]", JSON.toJSONString(user));
		}
		// TODO 需要添加判断
		if(StringUtils.isEmpty(user.getId())|| "".equals(user.getId().trim())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户id不能为空");
		}else if (StringUtils.isEmpty(user.getPassword())|| "".equals(user.getPassword().trim())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户密码不能为空");
		} else if (StringUtils.isEmpty(user.getRealname())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "真实名不能为空");
		} else if(null == user.getStatus()){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "状态必须提供");
		}

		try {
			user.setPassword(MD5.MD5Encode(user.getPassword()));
			int result = userService.updateById(user);

			if(result > 0){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user,
						JSON.toJSONString(user,SerializerFeature.IgnoreNonFieldGetter));
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("update result: {}", result);
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(user,
					FastjsonUtils.newIgnorePropertyFilter("password","updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("update User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改失败");
		}
	}

	/**
	 * 修改用户状态
	 */
	@RequestMapping(value = "/status", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object status(String id,Short status, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}, status: {}]", id,status);
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户id不能为空");
		}else if(null == status){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "状态必须填写");
		}

		try {
			User user = new User();
			user.setId(id);
			user.setStatus(status);
			int result =userService.updateById(user);
			if(result > 0){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user,
						JSON.toJSONString(user,SerializerFeature.IgnoreNonFieldGetter));
			}
			return AllResult.build(1,"修改状态成功");
		} catch (Exception e) {
			LOGGER.error("status User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
		}
	}

	/**
	 * 删除用户（不是真实删除） TODO 回头有需求在进行调整
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object delete(String id, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户id不能为空");
		}

		try {
			User user = new User();
			user.setId(id);
			user.setStatus(Short.valueOf("2"));
			int result = userService.updateById(user);

			if(result > 0){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user,
						JSON.toJSONString(user,SerializerFeature.IgnoreNonFieldGetter));
			}

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("delete User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误, 用户删除失败");
		}
	}

	/**
	 * 获取用户信息
	 */
	@RequestMapping(value = "/get", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object getById(String id, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户id不能为空");
		}

		try {
			User user = userService.getById(id);

			if(null != user){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user,
						JSON.toJSONString(user,SerializerFeature.IgnoreNonFieldGetter));
			}else{
				return AllResult.buildJSON(HttpStatus.NOT_FOUND.value(), "未找到用户数据");
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(user,
					FastjsonUtils.newIgnorePropertyFilter("password","updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("get User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误, 用户删除失败");
		}
	}


	/**
	 * 查询所有用户
	 */
	@RequestMapping(value="/getAll",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object getAll(HttpServletRequest request){

		try {
			List<User> users = userService.getAll();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("users result: {}", JSON.toJSONString(users));
			}

			if(null == users || users.size() == 0){
				return AllResult.build(1, "未获取到菜单");
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(users,
					FastjsonUtils.newIgnorePropertyFilter("password","updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.user,JSON.toJSONString(users.size()));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("getAll User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取全部用户失败") ;
		}
	}

	/**
	 * 分页获取用户信息
	 *
	 * @param pageNum
	 *            当前页
	 * @param pageSize
	 *            显示多少行
	 */
	@RequestMapping(value="/datagrid",method= {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Object getDataGrid(@RequestParam(value = "pageNum") int pageNum,
							  @RequestParam(value = "pageSize") int pageSize,
							  @RequestParam(required = false,value = "username") String username,
							  @RequestParam(required = false,value = "realname") String realname,
							  HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", pageNum, pageSize);
		}

		// 校验参数
		if (pageNum < 1 || pageSize < 1) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}

		try {

			UserExample example = new UserExample();
			// 排序设置
			UserExample.Criteria criteria = example.createCriteria();
			// 条件设置
			criteria.andStatusNotEqualTo((short)2);
			if(!StringUtils.isEmpty(username)){
				criteria.andUsernameLike("%"+username+"%");
			}
			if(!StringUtils.isEmpty(realname)){
				criteria.andRealnameLike("%"+realname+"%");
			}

			Page<User> queryResult = userService.getScrollData(pageNum, pageSize, example);


			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("queryResult record count: {}", queryResult.getRecords().size());
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("password","updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.user,JSON.toJSONString(queryResult.getRecords().size()));

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get datagrid data error. page: {}, count: {}",pageNum, pageSize,  e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}


	/**
	 *
	 * @Description: 修改用户密码
	 * @param id
	 * @param password
	 * @param request
	 * @author 杜延雷
	 * @date 2016-08-10 00:04:52
	 */
	@RequestMapping(value = "modifyPassword", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object modifyPassword(@RequestParam(value = "id") String id,
								 @RequestParam(value = "password") String password,
								 HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {},password: {}]",id,password);
		}
		try {
			if (StringUtils.isEmpty(id)) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID不能为空");
			}
			if (StringUtils.isEmpty(password)) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "密码不能为空");
			}
			User user = new User();
			user.setId(id);
			user.setPassword(MD5.MD5Encode(password));

			if (userService.updateById(user) > 0) {
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user, JSON.toJSONString(user));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("密码修改成功", JSON.toJSONString(user));
				}
				return AllResult.buildJSON(1,"密码修改成功");
			} else {
				return AllResult.buildJSON(HttpStatus.NOT_FOUND.value(), "密码更新失败,未查询到相关数据");
			}
		} catch (Exception e) {
			LOGGER.error("modifyPassword User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改密码失败");
		}
	}
}
