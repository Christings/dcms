package com.web.action.xtgl;

import com.alibaba.fastjson.JSON;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.Role;
import com.web.example.RoleExample;
import com.web.service.RoleSerivce;
import com.web.util.AllResult;
import com.web.util.UUIDGenerator;
import com.web.util.fastjson.FastjsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

	/**
	 * 添加角色
	 */
	@RequestMapping(value="/add",method={RequestMethod.POST,RequestMethod.GET})
	public Object save(Role role, HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [role: {}]", JSON.toJSONString(role));
		}
		//TODO 需要添加判断
		if(StringUtils.isEmpty(role.getRolecode())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色编码不能为空");
		}else if(StringUtils.isEmpty(role.getRolename())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色名称不能空");
		}

		//角色名称不可以重复
		RoleExample example = new RoleExample();
		RoleExample.Criteria criteria =example.createCriteria();
		criteria.andRolecodeEqualTo(role.getRolecode());

		RoleExample.Criteria criteria1 = example.createCriteria();
		criteria1.andRolenameEqualTo(role.getRolename());

		example.or(criteria1);

		if(roleService.count(example)>0){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色编码或角色名称已经存在，不可以重复");
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
	 *修改角色
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST,RequestMethod.GET})
	public Object update(Role role, HttpServletRequest request){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [role: {}]", JSON.toJSONString(role));
		}
		//TODO 需要添加判断
		if(StringUtils.isEmpty(role.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色id不能为空");
		}else if(StringUtils.isEmpty(role.getRolecode())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色编码不能为空");
		}else if(StringUtils.isEmpty(role.getRolename())){
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "角色名称不能空");
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
	 * 获取所有角色
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
	 * @param pageNum
	 * @param pageSize
	 * @param request
     * @return
     */
	@RequestMapping(value="/datagrid",method= { RequestMethod.GET, RequestMethod.POST })
	public Object datagrid(@RequestParam(value = "pageNum") int pageNum,
						   @RequestParam(value = "pageSize") int pageSize,
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
			Page<Role> queryResult = roleService.getScrollData(pageNum, pageSize, example);

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult, FastjsonUtils.newIgnorePropertyFilter("updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get scroll data error. page: {}, count: {}", pageNum, pageSize, e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}




	/**
	 * 根据ID查找角色
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
	 * 删除角色
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
