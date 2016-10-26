package com.web.action.xtgl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web.bean.form.UserForm;
import com.web.bean.result.UserResult;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.User;
import com.web.entity.UserDoMain;
import com.web.entity.UserRole;
import com.web.example.DoMainExample;
import com.web.example.RoleExample;
import com.web.example.UserExample;
import com.web.service.DoMainService;
import com.web.service.RoleSerivce;
import com.web.service.UserDoMainService;
import com.web.service.UserRoleService;
import com.web.util.AllResult;
import com.web.util.MD5;
import com.web.util.UUIDGenerator;
import com.web.util.WebUtils;
import com.web.util.fastjson.FastjsonUtils;
import com.web.util.validation.GroupBuilder;
import com.web.util.validation.ValidationHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static com.web.util.AllResult.buildJSON;

/**
 * 用户管理接口
 * 
 * @author 杜延雷
 * @date 2016-08-09
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	RoleSerivce roleSerivce; // 角色Service
	@Autowired
	UserRoleService userRoleService; // 菜单角色关系Service
	@Autowired
	DoMainService doMainService; // 域（组织管理）
	@Autowired
	UserDoMainService userDoMainService;// 用户域关系

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	public Object addUser(User user,
						  @RequestParam(value = "roleIds") String roleIds,
						  @RequestParam(value = "domainIds")String domainIds) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [user: {}]", JSON.toJSONString(user));
		}

		//验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(user.getUsername()).notEmpty().maxLength(20), "登录名必须提供且最大长度20位")
				.addGroup(GroupBuilder.build(user.getPassword()).notEmpty().minLength(6).maxLength(16), "密码必须提供且长度在6~16位")
				.addGroup(GroupBuilder.build(user.getRealname()).notEmpty().maxLength(50), "真实名必须提供且最大长度在50位")
				.addGroup(GroupBuilder.build(user.getStatus()).notNull().valueIn((short) 0, (short) 1), "状态输入有误,请检查")
				.addGroup(GroupBuilder.build(roleIds).notEmpty(), "用户所属角色必须提供")
				.addGroup(GroupBuilder.build(domainIds).notEmpty(), "用户所属组织结构必须提供")

				// 非必输条件验证
				.addGroup(GroupBuilder.buildOr(user.getIdentificationno()).empty().maxLength(50), "证件号码最大长度50位")
				.addGroup(GroupBuilder.buildOr(user.getPhone()).empty().maxLength(11), "手机号最大长度11位")
				.addGroup(GroupBuilder.buildOr(user.getEmail()).empty().maxLength(100), "邮箱最大长度100位")
				.addGroup(GroupBuilder.buildOr(user.getMobile()).empty().maxLength(20), "电话（座机）最大长度20位")
				.addGroup(GroupBuilder.buildOr(user.getSex()).isNull().valueIn((short) 0, (short) 1), "性别输入有误,请检查")
				.addGroup(GroupBuilder.buildOr(user.getRemark()).empty().maxLength(300), "备注最大长度300位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(user.getUsername());
		if (userService.count(example) > 0) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "登录名已存在不允许重复添加");
		}

		try {
			// 1.判断用户角色传递是否正确
			String[] ids = roleIds.split(",");
			RoleExample example1 = new RoleExample();
			RoleExample.Criteria criteria1 = example1.createCriteria();
			criteria1.andIdIn(Arrays.asList(ids));

			if (ids.length != roleSerivce.count(example1)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "角色参数传递有误");
			}

			// 2.判断组织结构参数是否正确
			String[] dmIds = domainIds.split(",");
			DoMainExample e1 = new DoMainExample();
			DoMainExample.Criteria c1 = e1.createCriteria();
			c1.andIdIn(Arrays.asList(dmIds));

			if (dmIds.length != doMainService.countByExample(e1)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "组织结构参数传递有误");
			}

			// 3.设置用户相关信息
			user.setId(UUIDGenerator.generatorRandomUUID());
			user.setPassword(MD5.MD5Encode(user.getPassword()));

			// 4.保存用户、用户角色关系
			userService.saveUser(user, ids, dmIds);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.user, JSON.toJSONString(user));

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(user,
					FastjsonUtils.newIgnorePropertyFilter("password", "updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save User fail:", e.getMessage());
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加用户失败");
		}

	}

	/**
	 * 修改用户
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.GET })
	public Object update(User user,
						 @RequestParam(value = "roleIds") String roleIds,
						 @RequestParam(value = "domainIds")String domainIds) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [user: {}]", JSON.toJSONString(user));
		}

		//验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(user.getId()).notEmpty().maxLength(32), "ID必须提供且最大长度32位")
				.addGroup(GroupBuilder.build(user.getRealname()).notEmpty().maxLength(50), "真实名必须提供且最大长度在50位")
				.addGroup(GroupBuilder.build(user.getStatus()).notNull().valueIn((short) 0, (short) 1), "状态输入有误,请检查")
				.addGroup(GroupBuilder.build(roleIds).notEmpty(), "用户所属角色必须提供")
				.addGroup(GroupBuilder.build(domainIds).notEmpty(), "用户所属组织结构必须提供")

				// 非必输条件验证
				.addGroup(GroupBuilder.buildOr(user.getIdentificationno()).empty().maxLength(50), "证件号码最大长度50位")
				.addGroup(GroupBuilder.buildOr(user.getPhone()).empty().maxLength(11), "手机号最大长度11位")
				.addGroup(GroupBuilder.buildOr(user.getEmail()).empty().maxLength(100), "邮箱最大长度100位")
				.addGroup(GroupBuilder.buildOr(user.getMobile()).empty().maxLength(20), "电话（座机）最大长度20位")
				.addGroup(GroupBuilder.buildOr(user.getSex()).isNull().valueIn((short) 0, (short) 1), "性别输入有误,请检查")
				.addGroup(GroupBuilder.buildOr(user.getRemark()).empty().maxLength(300), "备注最大长度300位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		user.setPassword(null);
		user.setUsername(null);
		try {
			// 1.判断用户角色传递是否正确
			String[] ids = roleIds.split(",");
			RoleExample example1 = new RoleExample();
			RoleExample.Criteria criteria1 = example1.createCriteria();
			criteria1.andIdIn(Arrays.asList(ids));

			if (ids.length != roleSerivce.count(example1)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "角色参数传递有误");
			}

			// 2.判断组织结构参数是否正确
			String[] dmIds = domainIds.split(",");
			DoMainExample e1 = new DoMainExample();
			DoMainExample.Criteria c1 = e1.createCriteria();
			c1.andIdIn(Arrays.asList(dmIds));

			if (dmIds.length != doMainService.countByExample(e1)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "组织结构参数传递有误");
			}

			// 3.更新用户信息
			userService.updateUser(user, ids, dmIds);

			// 4.增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user,
					JSON.toJSONString(user, SerializerFeature.IgnoreNonFieldGetter));

			// 5.去除不需要的字段（返回前端数据）
			String jsonStr = JSON.toJSONString(user,
					FastjsonUtils.newIgnorePropertyFilter("password", "updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("update User fail:", e.getMessage());
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改失败");
		}
	}

	/**
	 * 修改用户状态
	 */
	@RequestMapping(value = "/status", method = { RequestMethod.POST, RequestMethod.GET })
	public Object status(String id,Short status) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}, status: {}]", id, status);
		}

		//验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(id).notEmpty().maxLength(32), "ID必须提供且最大长度32位")
				.addGroup(GroupBuilder.build(status).notNull().valueIn((short) 0, (short) 1), "状态输入有误,请检查")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			User user = new User();
			user.setId(id);
			user.setStatus(status);
			int result = userService.updateById(user);
			if (result > 0) {
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user,
						JSON.toJSONString(user, SerializerFeature.IgnoreNonFieldGetter));
			}
			return AllResult.build(1, "修改状态成功");
		} catch (Exception e) {
			LOGGER.error("status User fail:", e.getMessage());
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
		}
	}

	/**
	 * 删除用户
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
	public Object delete(String id, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}

		if (StringUtils.isEmpty(id) || "".equals(id.trim())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID必须提供");
		}

		try {
			String[] ids = id.split(",");
			UserExample example = new UserExample();
			UserExample.Criteria criteria = example.createCriteria();
			criteria.andIdIn(Arrays.asList(ids));
			List<User> users = userService.getExample(example);
			if(null == users || users.size()==0){
				return buildJSON(0, "未查询到用户信息");
			}

			//批量删除用户信息
			userService.deleteBatch(id);
			User u = WebUtils.getUser(request);

			//增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user,
						JSON.toJSONString(users, SerializerFeature.IgnoreNonFieldGetter));

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("delete User fail:", e.getMessage());
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误, 用户删除失败");
		}
	}

	/**
	 * 获取用户信息
	 */
	@RequestMapping(value = "/get", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getById(String id, HttpServletRequest request) {
		// 1.验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(id).notEmpty().maxLength(32), "用户ID必须提供且长度最大为32位").validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			User user = userService.getById(id);

			if (null != user) {
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.user,
						JSON.toJSONString(user, SerializerFeature.IgnoreNonFieldGetter));
			} else {
				return buildJSON(0, "未查询到用户信息");
			}

			// 去除不需要的字段
			UserResult result = new UserResult();
			BeanUtils.copyProperties(user, result);
			List<UserRole> userRoles = userRoleService.getUserRole(user.getId());
			for (UserRole userRole : userRoles) {
				result.addRoleIds(userRole.getRoleId());
			}
			List<UserDoMain> userDomains = userDoMainService.getUserDomain(user.getId());
			for (UserDoMain userDoMain : userDomains) {
				result.addDomainIds(userDoMain.getDomainId());
			}

			return AllResult.okJSON(result);
		} catch (Exception e) {
			LOGGER.error("get User fail:", e.getMessage());
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误, 获取用户信息失败");
		}
	}

	/**
	 * 查询所有用户
	 */
	@RequestMapping(value = "/getAll", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getAll(HttpServletRequest request) {

		try {
			List<User> users = userService.getAll();

			if (null == users || users.size() == 0) {
				return AllResult.build(1, "未查询到信息");
			}

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(users,
					FastjsonUtils.newIgnorePropertyFilter("password", "updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			// 增加日志
			// operLogService.addSystemLog(OperLog.operTypeEnum.select,
			// OperLog.actionSystemEnum.user,JSON.toJSONString(users.size()));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("getAll User fail:", e.getMessage());
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取全部用户失败");
		}
	}

	/**
	 * 分页获取用户信息
	 */
	@RequestMapping(value = "/datagrid", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getDataGrid(UserForm userForm, HttpServletRequest request) {

		// 1.验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(userForm.getPageNum()).notNull().minValue(1), "页码必须从1开始")
				.addGroup(GroupBuilder.build(userForm.getPageSize()).notNull().minValue(1), "每页记录数量最少1条")

				// 非必输条件验证
				.addGroup(GroupBuilder.buildOr(userForm.getStatus()).isNull().valueIn((short) 0, (short) 1), "查询状态传值有误")
				.addGroup(GroupBuilder.buildOr(userForm.getSex()).isNull().valueIn((short) 0, (short) 1), "查询性别传值有误").validate();

		if (!StringUtils.isEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {

			UserExample example = new UserExample();
			UserExample.Criteria criteria = example.createCriteria();
			// 条件设置
			if (!StringUtils.isEmpty(userForm.getUsername())) {
				criteria.andUsernameLike("%" + userForm.getUsername().trim() + "%");
			}
			if (!StringUtils.isEmpty(userForm.getRealname())) {
				criteria.andRealnameLike("%" + userForm.getRealname().trim() + "%");
			}
			if (null != userForm.getStatus()) {
				criteria.andStatusEqualTo(userForm.getStatus());
			} else {
				criteria.andStatusNotEqualTo((short) 2);
			}
			if (null != userForm.getSex()) {
				criteria.andSexEqualTo(userForm.getSex());
			}

			// 设置排序条件
			StringBuffer orderBy = new StringBuffer("");
			if (!StringUtils.isEmpty(userForm.getUsernameSort())) {
				orderBy.append("username " + ("asc".equalsIgnoreCase(userForm.getUsernameSort()) ? "asc" : "desc") + ",");
			}
			if (!StringUtils.isEmpty(userForm.getRealnameSort())) {
				orderBy.append("realname " + ("asc".equalsIgnoreCase(userForm.getRealnameSort()) ? "asc" : "desc") + ",");
			}
			if (!StringUtils.isEmpty(userForm.getIdCardSort())) {
				orderBy.append("identificationNo " + ("asc".equalsIgnoreCase(userForm.getIdCardSort()) ? "asc" : "desc") + ",");
			}
			if (!StringUtils.isEmpty(userForm.getEmailSort())) {
				orderBy.append("email " + ("asc".equalsIgnoreCase(userForm.getEmailSort()) ? "asc" : "desc") + ",");
			}
			orderBy.append("create_date desc,id asc");
			example.setOrderByClause(orderBy.toString());

			Page<User> queryResult = userService.getScrollData(userForm.getPageNum(), userForm.getPageSize(), example);

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("password", "updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			// operLogService.addSystemLog(OperLog.operTypeEnum.select,
			// OperLog.actionSystemEnum.user,JSON.toJSONString(queryResult.getRecords().size()));

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get datagrid data error. page: {}, count: {}", userForm.getPageNum(), userForm.getPageSize(), e);
		}

		return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * @Description: 修改用户密码(管理员修改时)
	 *
	 * @author 杜延雷
	 * @date 2016-08-10
	 */
	@RequestMapping(value = "modifyPassword", method = RequestMethod.POST)
	public Object modifyPassword(@RequestParam(value = "id") String id, @RequestParam(value = "password") String password,
			HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {},password: {}]", id, password);
		}
		try {
			if (StringUtils.isEmpty(id)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID不能为空");
			}
			if (StringUtils.isEmpty(password)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "密码不能为空");
			}
			User user = new User();
			user.setId(id);
			user.setPassword(MD5.MD5Encode(password));

			if (userService.updateById(user) > 0) {
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user,
						"管理员:" + JSON.toJSONString(WebUtils.getUser(request)) + ",修改用户密码：" + JSON.toJSONString(user));

				return buildJSON(1, "密码修改成功");
			} else {
				return buildJSON(HttpStatus.NOT_FOUND.value(), "密码修改失败,未查询到相关数据");
			}
		} catch (Exception e) {
			LOGGER.error("modifyPassword User fail:", e.getMessage());
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改密码失败");
		}
	}

	/**
	 * 修改用户密码(普通用户)
	 *
	 * @author 杜延雷
	 * @date 2016-08-10
	 */
	@RequestMapping(value = "password", method = RequestMethod.POST)
	public Object password(@RequestParam(value = "oldPassword", required = false) String oldPassword,
			@RequestParam(value = "newPassword", required = false) String newPassword, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [oldPassword: {},newPassword: {}]", oldPassword, newPassword);
		}
		try {
			if (StringUtils.isEmpty(oldPassword)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "原始密码不能为空");
			}
			if (StringUtils.isEmpty(newPassword)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "新密码不能为空");
			}
			if (!MD5.MD5Encode(oldPassword).equals(WebUtils.getUser(request).getPassword())) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "原始密码输入错误,修改失败");
			}

			User user = new User();
			user.setId(WebUtils.getUser(request).getId());
			user.setPassword(MD5.MD5Encode(newPassword));

			if (userService.updateById(user) > 0) {
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user, JSON.toJSONString(user));
				return buildJSON(1, "密码修改成功");
			} else {
				return buildJSON(HttpStatus.NOT_FOUND.value(), "密码修改失败,未查询到相关数据");
			}
		} catch (Exception e) {
			LOGGER.error("modifyPassword User fail:", e.getMessage());
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改密码失败");
		}
	}
}
