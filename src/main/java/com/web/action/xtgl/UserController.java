package com.web.action.xtgl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web.bean.form.UserForm;
import com.web.bean.result.UserResult;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.User;
import com.web.entity.UserRole;
import com.web.example.RoleExample;
import com.web.example.UserExample;
import com.web.service.RoleSerivce;
import com.web.service.UserRoleService;
import com.web.util.AllResult;
import com.web.util.MD5;
import com.web.util.UUIDGenerator;
import com.web.util.WebUtils;
import com.web.util.fastjson.FastjsonUtils;
import com.web.util.validation.GroupBuilder;
import com.web.util.validation.ValidationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	RoleSerivce roleSerivce; //角色Service
	@Autowired
	UserRoleService userRoleService; //菜单角色关系Service

	/**
	 * 添加用户
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object addUser(User user,String roleIds, HttpServletRequest request) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [user: {}]", JSON.toJSONString(user));
		}

		// TODO 需要添加判断
		if (StringUtils.isEmpty(user.getUsername()) || "".equals(user.getUsername().trim())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "登录名不能为空");
		} else if (StringUtils.isEmpty(user.getPassword())|| "".equals(user.getPassword().trim())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "用户密码不能为空");
		} else if (StringUtils.isEmpty(user.getRealname())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "真实名不能为空");
		} else if(null == user.getStatus()){
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "状态输入有误,请检查");
		} else if(StringUtils.isEmpty(roleIds)){
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "用户所属角色必须提供");
		}

		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(user.getUsername());
		if(userService.count(example)>0){
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "登录名已经存在不可以重复");
		}

		try {
			//1.设置用户相关信息
			user.setId(UUIDGenerator.generatorRandomUUID());
			user.setPassword(MD5.MD5Encode(user.getPassword()));

			//2.判断用户角色传递是否正确
			String[] ids = roleIds.split(",");
			RoleExample example1 = new RoleExample();
			RoleExample.Criteria criteria1 = example1.createCriteria();
			criteria1.andIdIn(Arrays.asList(ids));

			if(ids.length != roleSerivce.count(example1)){
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "角色参数传递有误");
			}

			//3.保存用户、用户角色关系
			userService.saveUserAndRoleRelation(user,ids);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.user,
					JSON.toJSONString(user));

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(user,
					FastjsonUtils.newIgnorePropertyFilter("password","updateName","updateDate","createName","createDate"),
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
	@RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object update(User user, String roleIds, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [user: {}]", JSON.toJSONString(user));
		}
		// TODO 需要添加判断
		if(StringUtils.isEmpty(user.getId())|| "".equals(user.getId().trim())){
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "用户id不能为空");
		}else if (StringUtils.isEmpty(user.getRealname())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "真实名不能为空");
		} else if(null == user.getStatus()){
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "状态必须提供");
		} else if(StringUtils.isEmpty(roleIds)){
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "用户所属角色必须提供");
		}

		try {
			//1.防止用户前端传入值更新数据
			user.setPassword(null);
			user.setUsername(null);

			//2.判断用户角色传递是否正确
			String[] ids = roleIds.split(",");
			RoleExample example1 = new RoleExample();
			RoleExample.Criteria criteria1 = example1.createCriteria();
			criteria1.andIdIn(Arrays.asList(ids));

			if(ids.length != roleSerivce.count(example1)){
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "角色参数传递有误");
			}

			//3.更新用户信息
			userService.updateUserAndRoleRelation(user,ids);

			//4.增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.user,
					JSON.toJSONString(user,SerializerFeature.IgnoreNonFieldGetter));

			//5.去除不需要的字段（返回前端数据）
			String jsonStr = JSON.toJSONString(user,
					FastjsonUtils.newIgnorePropertyFilter("password","updateName","updateDate","createName","createDate"),
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
	@RequestMapping(value = "/status", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object status(String id,Short status, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}, status: {}]", id,status);
		}
		// TODO 需要添加判断
		if (StringUtils.isEmpty(id)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "用户id不能为空");
		}else if(null == status){
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "状态必须填写");
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
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
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
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "用户id不能为空");
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
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误, 用户删除失败");
		}
	}

	/**
	 * 获取用户信息
	 */
	@RequestMapping(value = "/get", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object getById(String id, HttpServletRequest request) {
		//1.验证参数
		String errorTip = ValidationHelper.build()
				//必输条件验证
				.addGroup(GroupBuilder.build(id).notEmpty().maxLength(32), "用户ID必须提供且长度最大为32位")
				.validate();

		if (!StringUtils.isEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			User user = userService.getById(id);

			if(null != user){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.user,
						JSON.toJSONString(user,SerializerFeature.IgnoreNonFieldGetter));
			}else{
				return buildJSON(0, "未找到用户数据");
			}

			//去除不需要的字段
			UserResult result = new UserResult();
			BeanUtils.copyProperties(user,result);
			List<UserRole> userRoles = userRoleService.getUserRole(user.getId());
			for(UserRole userRole:userRoles){
				result.addRoleIds(userRole.getRoleId());
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
	@RequestMapping(value="/getAll",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object getAll(HttpServletRequest request){

		try {
			List<User> users = userService.getAll();

			if(null == users || users.size() == 0){
				return AllResult.build(1, "未获取到菜单");
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(users,
					FastjsonUtils.newIgnorePropertyFilter("password","updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			// 增加日志
//			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.user,JSON.toJSONString(users.size()));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("getAll User fail:", e.getMessage());
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取全部用户失败") ;
		}
	}

	/**
	 * 分页获取用户信息
	 */
	@RequestMapping(value="/datagrid",method= {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Object getDataGrid(UserForm userForm,HttpServletRequest request) {

		//1.验证参数
		String errorTip = ValidationHelper.build()
				//必输条件验证
				.addGroup(GroupBuilder.build(userForm.getPageNum()).notNull().minValue(1), "页码必须从1开始")
				.addGroup(GroupBuilder.build(userForm.getPageSize()).notNull().minValue(1), "每页记录数量最少1条")

				//非必输条件验证
				.addGroup(GroupBuilder.buildOr(userForm.getStatus()).isNull().valueIn((short)0,(short)1),"查询状态传值有误")
				.addGroup(GroupBuilder.buildOr(userForm.getSex()).isNull().valueIn((short)0,(short)1),"查询性别传值有误")
				.validate();

		if (!StringUtils.isEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {

			UserExample example = new UserExample();
			UserExample.Criteria criteria = example.createCriteria();
			// 条件设置
			if(!StringUtils.isEmpty(userForm.getUsername())){
				criteria.andUsernameLike("%"+userForm.getUsername().trim()+"%");
			}
			if(!StringUtils.isEmpty(userForm.getRealname())){
				criteria.andRealnameLike("%"+userForm.getRealname().trim()+"%");
			}
			if(null != userForm.getStatus()){
				criteria.andStatusEqualTo(userForm.getStatus());
			}else{
				criteria.andStatusNotEqualTo((short)2);
			}
			if(null != userForm.getSex()){
				criteria.andSexEqualTo(userForm.getSex());
			}

			//设置排序条件
			StringBuffer orderBy = new StringBuffer("");
			if(!StringUtils.isEmpty(userForm.getUsernameSort())){
				orderBy.append("username "+("asc".equalsIgnoreCase(userForm.getUsernameSort())?"asc":"desc")+",");
			}
			if(!StringUtils.isEmpty(userForm.getRealnameSort())){
				orderBy.append("realname "+("asc".equalsIgnoreCase(userForm.getRealnameSort())?"asc":"desc")+",");
			}
			if(!StringUtils.isEmpty(userForm.getIdCardSort())){
				orderBy.append("identificationNo "+("asc".equalsIgnoreCase(userForm.getIdCardSort())?"asc":"desc")+",");
			}
			if(!StringUtils.isEmpty(userForm.getEmailSort())){
				orderBy.append("email "+("asc".equalsIgnoreCase(userForm.getEmailSort())?"asc":"desc")+",");
			}
			orderBy.append("create_date desc,id asc");
			example.setOrderByClause(orderBy.toString());

			Page<User> queryResult = userService.getScrollData(userForm.getPageNum(), userForm.getPageSize(), example);

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("password","updateName","updateDate","createName","createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

//			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.user,JSON.toJSONString(queryResult.getRecords().size()));

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get datagrid data error. page: {}, count: {}",userForm.getPageNum(), userForm.getPageSize(),  e);
		}

		return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}


	/**
	 * @Description: 修改用户密码(管理员修改时)
	 *
	 *  @param id
	 * @param password
	 * @param request
	 * @author 杜延雷
	 * @date 2016-08-10 00:04:52
	 */
	@RequestMapping(value = "modifyPassword", method = RequestMethod.POST)
	@ResponseBody
	public Object modifyPassword(@RequestParam(value = "id") String id,
								 @RequestParam(value = "password") String password,
								 HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {},password: {}]",id,password);
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
				operLogService.addSystemLog(OperLog.operTypeEnum.update,
						OperLog.actionSystemEnum.user, "管理员:"+JSON.toJSONString(WebUtils.getUser(request))+",修改用户密码："+JSON.toJSONString(user));

				return buildJSON(1,"密码修改成功");
			} else {
				return buildJSON(HttpStatus.NOT_FOUND.value(), "密码修改失败,未查询到相关数据");
			}
		} catch (Exception e) {
			LOGGER.error("modifyPassword User fail:", e.getMessage());
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改密码失败");
		}
	}

	/**
	 *
	 * @Description: 修改用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param request
	 * @author 杜延雷
	 * @date 2016-08-10 00:04:52
	 */
	@RequestMapping(value = "password", method = RequestMethod.POST)
	@ResponseBody
	public Object password(@RequestParam(value = "oldPassword",required = false)  String oldPassword,
						   @RequestParam(value = "newPassword",required = false)String newPassword,
						   HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [oldPassword: {},newPassword: {}]",oldPassword,newPassword);
		}
		try {
			if (StringUtils.isEmpty(oldPassword)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "原始密码不能为空");
			}
			if (StringUtils.isEmpty(newPassword)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "新密码不能为空");
			}
			if(!MD5.MD5Encode(oldPassword).equals(WebUtils.getUser(request).getPassword())){
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "原始密码输入错误,修改失败");
			}

			User user = new User();
			user.setId(WebUtils.getUser(request).getId());
			user.setPassword(MD5.MD5Encode(newPassword));

			if (userService.updateById(user) > 0) {
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update,
						OperLog.actionSystemEnum.user, JSON.toJSONString(user));
				return buildJSON(1,"密码修改成功");
			} else {
				return buildJSON(HttpStatus.NOT_FOUND.value(), "密码修改失败,未查询到相关数据");
			}
		} catch (Exception e) {
			LOGGER.error("modifyPassword User fail:", e.getMessage());
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改密码失败");
		}
	}
}
