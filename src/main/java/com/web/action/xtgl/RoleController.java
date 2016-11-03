package com.web.action.xtgl;

import com.alibaba.fastjson.JSON;
import com.web.bean.form.RoleForm;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.Role;
import com.web.example.RoleExample;
import com.web.example.UserRoleExample;
import com.web.service.RoleSerivce;
import com.web.service.UserRoleService;
import com.web.util.AllResult;
import com.web.util.UUIDGenerator;
import com.web.util.fastjson.FastjsonUtils;
import com.web.util.validation.GroupBuilder;
import com.web.util.validation.ValidationHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.web.util.AllResult.buildJSON;

/**
 * 角色接口
 *
 * @author 杜延雷
 * @date 2016-08-10
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleSerivce roleService;
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * 添加
	 */
	@RequestMapping(value="/add",method={RequestMethod.POST,RequestMethod.GET})
	public Object save(Role role){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [role: {}]", JSON.toJSONString(role));
		}

		//验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(role.getRolecode()).notEmpty().maxLength(20), "角色编码必须提供且最大长度20位")
				.addGroup(GroupBuilder.build(role.getRolename()).notEmpty().maxLength(100), "角色名称必须提供且长度在100位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		//角色名称不可以重复
		RoleExample example = new RoleExample();
		RoleExample.Criteria criteria =example.createCriteria();
		criteria.andRolecodeEqualTo(role.getRolecode());

		RoleExample.Criteria criteria1 = example.createCriteria();
		criteria1.andRolenameEqualTo(role.getRolename());

		example.or(criteria1);

		if(roleService.count(example)>0){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色编码或角色名称已经存在，不允许重复");
		}
		try {
			role.setId(UUIDGenerator.generatorRandomUUID());
			int result=roleService.save(role);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.role,
					JSON.toJSONString(role));

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(role, FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("save Role fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加角色失败") ;
		}
	}

	/**
	 *修改
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST,RequestMethod.GET})
	public Object update(Role role, HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [role: {}]", JSON.toJSONString(role));
		}

		//验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(role.getId()).notEmpty().maxLength(32), "角色ID必须提供且最大长度32位")
				.addGroup(GroupBuilder.build(role.getRolecode()).notEmpty().maxLength(20), "角色编码必须提供且最大长度20位")
				.addGroup(GroupBuilder.build(role.getRolename()).notEmpty().maxLength(100), "角色名称必须提供且长度在100位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		if(null == roleService.getById(role.getId())){
			return buildJSON(0, "未查找到该信息");
		}

		//角色名称不可以重复
		RoleExample example = new RoleExample();
		RoleExample.Criteria criteria =example.createCriteria();
		criteria.andRolecodeEqualTo(role.getRolecode());

		RoleExample.Criteria criteria1 = example.createCriteria();
		criteria1.andRolenameEqualTo(role.getRolename());

		example.or(criteria1);
		Role r = roleService.getByExample(example);

		if(null != r && !r.getId().equals(role.getId())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色编码或角色名称已经存在，不可以重复");
		}

		try {
			int result=roleService.updateById(role);

			if(result > 0){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.role,
						JSON.toJSONString(role));
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(role, FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("update Role fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改角色失败") ;
		}
	}
	
	/**
	 * 获取所有
	 */
	@RequestMapping(value="/getAll",method={RequestMethod.POST,RequestMethod.GET})
	public Object getAll(HttpServletRequest request){
		try {
			
			List<Role> list=roleService.getAll();

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(list, FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("getAll Role fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,查询角色失败") ;
		}
	}
	
	/**
	 * 分页查询
     */
	@RequestMapping(value="/datagrid",method= { RequestMethod.GET, RequestMethod.POST })
	public Object datagrid(RoleForm roleForm) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [roleForm: {}]", JSON.toJSONString(roleForm));
		}

		//1.验证参数
		String errorTip = ValidationHelper.build()
				//必输条件验证
				.addGroup(GroupBuilder.build(roleForm.getPageNum()).notNull().minValue(1), "页码必须从1开始")
				.addGroup(GroupBuilder.build(roleForm.getPageSize()).notNull().minValue(1), "每页记录数量最少1条")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			RoleExample example = new RoleExample();
			RoleExample.Criteria criteria = example.createCriteria();
			if(StringUtils.isNotEmpty(roleForm.getRolecode())){
				criteria.andRolecodeLike("%"+roleForm.getRolecode().trim()+"%");
			}
			if(StringUtils.isNotEmpty(roleForm.getRolename())){
				criteria.andRolenameLike("%"+roleForm.getRolename().trim()+"%");
			}

			// 设置排序条件
			StringBuffer orderBy = new StringBuffer("");
			if(StringUtils.isNotEmpty(roleForm.getSortName())){
				if("rolecode".equalsIgnoreCase(roleForm.getSortName())){
					orderBy.append("rolecode " + ("asc".equalsIgnoreCase(roleForm.getSortDesc()) ? "asc" : "desc") + ",");
				}else if("rolename".equalsIgnoreCase(roleForm.getSortName())){
					orderBy.append("rolename " + ("asc".equalsIgnoreCase(roleForm.getSortDesc()) ? "asc" : "desc") + ",");
				}
			}

			orderBy.append("create_date desc,id asc");
			example.setOrderByClause(orderBy.toString());

			Page<Role> queryResult = roleService.getScrollData(roleForm.getPageNum(), roleForm.getPageSize(), example);

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult, FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get 获取角色分页数据时异常:", e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 根据ID查找
	 */
	@RequestMapping(value="/get",method={RequestMethod.GET,RequestMethod.POST})
	public Object getById(String id, HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", JSON.toJSONString(id));
		}
		//TODO 后期可能要添加验证
		if(StringUtils.isEmpty(id)){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色id不能为空");
		}

		try {
			Role role = roleService.getById(id);

			if(null == role){
				return AllResult.buildJSON(HttpStatus.NOT_FOUND.value(), "未查询到相关数据");
			}

			// 增加日志
//			if(null != role){
//				operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.role,
//					JSON.toJSONString(role));
//			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(role,
					FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("get Role fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,查询角色失败") ;
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping(value="/delete",method={RequestMethod.GET,RequestMethod.POST})
	public Object deleteById(String id, HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", JSON.toJSONString(id));
		}

		//TODO 后期可能要添加验证
		if(StringUtils.isEmpty(id)){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色id不能为空");
		}

		try {
			UserRoleExample example = new UserRoleExample();
			UserRoleExample.Criteria criteria = example.createCriteria();
			criteria.andRoleIdEqualTo(id);
			if(userRoleService.getCount(example)>0){
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色下包含用户,不允许删除!!!");
			}

			int result=roleService.deleteById(id);

			if(result > 0){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.role,
						"delete role id:"+id);
			}

			return AllResult.build(1,"角色删除成功");
		} catch (Exception e) {
			LOGGER.error("delete Role fail:", e.getMessage());
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,删除角色失败") ;
		}
	}
}
