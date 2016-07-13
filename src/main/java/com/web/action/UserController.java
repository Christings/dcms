package com.web.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.web.core.util.Page;
import com.web.core.util.page.PageViewResult;
import com.web.core.util.page.QueryResult;
import com.web.entity.Menu;
import com.web.example.MenuExample;
import com.web.example.UserExample;
import com.web.util.fastjson.FastjsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.web.core.action.BaseController;
import com.web.entity.OperLog;
import com.web.entity.User;
import com.web.util.*;

/**
 * 用户管理Controller
 * 
 * @ClassName: UserController
 * @Description: TODO
 * @author 童云鹏
 * @date 2016年7月5日 上午9:58:51
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	/**
	 * @Description:  新增用户
	 * @author 童云鹏
	 * @date 2016年7月5日 上午9:59:16
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public Object addUser(@RequestBody User user, HttpServletRequest request) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menu: {}]", JSON.toJSONString(user));
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(user.getAccount())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:account用户账号不能为空");
		} else if (StringUtils.isEmpty(user.getPassword())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:password密码不能为空");
		} else if (StringUtils.isEmpty(user.getUserName())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userName密码不能为空");
		}

		try {
			user.setId(UUIDGenerator.generatorRandomUUID());
			user.setPassword(MD5.MD5Encode(user.getPassword()));
			user.setCreateName("admin");
			user.setCreateDate(new Date());
			int result = userService.saveUser(user);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", result);
			}
			return AllResult.okJSON(user);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加用户失败");
		}

	}

	/**
	 * @Description: 修改用户
	 * @author 童云鹏
	 * @date 2016年7月5日 上午9:59:38
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Object updateUser(@RequestBody User user, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menu: {}]", JSON.toJSONString(user));
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(user.getAccount())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:account用户账号不能为空");
		} else if (StringUtils.isEmpty(user.getPassword())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:password密码不能为空");
		} else if (StringUtils.isEmpty(user.getUserName())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userName用户名不能为空");
		} else if (StringUtils.isEmpty(user.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userId不能为空");
		}

		try {
			// user.setId(UUIDGenerator.generatorRandomUUID());
			// user.setPassword(MD5.MD5Encode(user.getPassword()));
			user.setUpdateName(WebUtils.getUser(request).getUserName());
			user.setUpdateCreate(new Date());
			int result = userService.updateUser(user);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("save result: {}", result);
			}
			return AllResult.okJSON(user);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改失败");
		}
	}

	/**
	 * @Description: 设置用户是否启用
     * @author         童云鹏
	 * @date 2016年7月5日 上午10:04:40
	 */
	@RequestMapping(value = "/updateUserEnabled", method = RequestMethod.POST)
	@ResponseBody
	public Object updateUserEnabled(@RequestBody User user, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menu: {}]", JSON.toJSONString(user));
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(user.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userId不能为空");
		}

		try {
			user.setUpdateName(WebUtils.getUser(request).getUserName());
			user.setUpdateCreate(new Date());
			// user.setId(UUIDGenerator.generatorRandomUUID());
			// user.setPassword(MD5.MD5Encode(user.getPassword()));
			userService.updateUserDelete(user.getEnabled(), user.getId());
			return AllResult.okJSON(user);
		} catch (Exception e) {
			LOGGER.error("updateUserEnabled User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,设置用户是否启用失败");
		}
	}

	/**
	 * @Description: 用户逻辑删除
	 * @author  童云鹏
	 * @date 2016年7月5日 上午10:05:35
	 */
	@RequestMapping(value = "/updateUserDelete", method = RequestMethod.POST)
	@ResponseBody
	public Object updateUserDelete(@RequestBody User user, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [menu: {}]", JSON.toJSONString(user));
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(user.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:userId不能为空");
		}

		try {
			user.setUpdateName(WebUtils.getUser(request).getUserName());
			user.setUpdateCreate(new Date());
			// user.setId(UUIDGenerator.generatorRandomUUID());
			// user.setPassword(MD5.MD5Encode(user.getPassword()));
			userService.updateUserDelete(user.getDeleted(), user.getId());

			return AllResult.okJSON(user);
		} catch (Exception e) {
			LOGGER.error("delete User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误, 用户逻辑删除失败");
		}
	}

	/**
	* @Description: 分页查询
	* @author  童云鹏
	* @date 2016年7月13日 上午11:41:44
	 */
	@RequestMapping(value="/getUsersByPage",method=RequestMethod.GET)
	@ResponseBody
	public Object getUsersByPage(@RequestBody Page<User> page, HttpServletRequest request){
		page.setPageNo(1);
		page.setPageSize(10);
		try {
			userService.getUserPage(page);
			return AllResult.okJSON(page);
		} catch (Exception e) {
			LOGGER.error("delete User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,分页查询失败") ;
		}
	}

	/**
	 * 分页查询
	 * @param page
	 * @param count
	 * @param request
     * @return
     */
	@RequestMapping(value="/scroll",method= { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getScroll(@RequestParam(value = "page") int page, @RequestParam(value = "count") int count,
							HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", page, count);
		}

		// 校验参数
		if (page < 1 || count < 1) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}

		try {

			UserExample example = new UserExample();
			// 排序设置
			// example.setOrderByClause("UPDATE_DATETIME DESC");
			UserExample.Criteria criteria = example.createCriteria();
			// 条件设置
			// criteria.andIconIdIsNull();

			QueryResult<User> queryResult = userService.getScrollData(page, count, example);
			PageViewResult<User> pageViewResult = new PageViewResult<>(count, page);
			pageViewResult.setQueryResult(queryResult);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("queryResult record count: {}", queryResult.getResultList().size());
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(pageViewResult, FastjsonUtils.newIgnorePropertyFilter("updateName","updateCreate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get scroll data error. page: {}, count: {}", page, count, e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}


	/**
	 *
	 * @Description: 修改用户密码
	 * @param user
	 * @param request
	 * @author 田军兴
	 * @date 2016年7月9日
	 */
	@RequestMapping(value = "modifyPassword", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object modifyPassword(@RequestBody User user, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [user: {}]", JSON.toJSONString(user));
		}
		try {
			if (StringUtil.isEmpty(user.getId())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:用户ID不能为空");
			}
			if (StringUtil.isEmpty(user.getPassword())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常:密码不能为空");
			}
			User oldUser = userService.getUserById(user.getId());
			if (oldUser.getPassword().equals(MD5.MD5Encode(user.getPassword()))) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request param: [user: {}]", JSON.toJSONString(user));
				}
				return AllResult.okJSON(user);
			}
			user.setPassword(MD5.MD5Encode(user.getPassword()));
			if (userService.updateUserPassword(user) > 0) {
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user, JSON.toJSONString(user));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("密码修改成功", JSON.toJSONString(user));
				}
				return AllResult.okJSON(user);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "密码修改失败:数据未能持久化");
			}
		} catch (Exception e) {
			LOGGER.error("delete User fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改密码失败");
		}
	}
}
