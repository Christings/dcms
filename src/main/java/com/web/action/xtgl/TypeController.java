package com.web.action.xtgl;

import com.alibaba.fastjson.JSON;
import com.web.bean.form.TypeForm;
import com.web.bean.result.TypeResult;
import com.web.bean.result.TypeTreeResult;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.Type;
import com.web.example.TypeExample;
import com.web.service.TypeService;
import com.web.util.AllResult;
import com.web.util.UUIDGenerator;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.web.util.AllResult.buildJSON;

/**
 * 分类管理接口
 *
 * @author 杜延雷
 * @date 2016-11-07
 */
@RestController
@RequestMapping("/type")
public class TypeController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TypeController.class);

	@Autowired
	TypeService typeService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public Object add(TypeForm form) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [typeFrom: {}]", JSON.toJSONString(form));
		}

		//验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(form.getName()).notEmpty().maxLength(50), "分类名称提供且最大长度50位")
				// 非必输条件验证
				.addGroup(GroupBuilder.buildOr(form.getParentId()).empty().maxLength(32), "父ID最大长度为32位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		//定义父类变量
		Type parentType = null;

		// 处理外键关联数据传空值问题
		if (null != form.getParentId() && !"".equals(form.getParentId().trim())) {
			parentType = typeService.getById(form.getParentId());
			if(null == parentType){
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "父级分类不存在");
			}else {
				form.setLevel(parentType.getLevel().intValue()+1);
			}
		}else{
			form.setParentId(null);
			form.setLevel(1);
		}

		TypeExample te = new TypeExample();
		TypeExample.Criteria c1 = te.createCriteria();
		c1.andNameEqualTo(form.getName());
		if(typeService.countByExample(te)>0){
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "分类名称已存在，不允许重复添加!!!");
		}

		try {
			Type type = new Type();
			BeanUtils.copyProperties(form,type,"id");
			type.setId(UUIDGenerator.generatorRandomUUID());
			typeService.save(type);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.type,
					JSON.toJSONString(type),OperLog.logLevelEnum.success);

			//返回对象
			TypeResult tr = new TypeResult();
			BeanUtils.copyProperties(type,tr);

			return AllResult.okJSON(tr);
		} catch (Exception e) {
			LOGGER.error("save type object error. : {}", JSON.toJSONString(form), e);
			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.type,
					JSON.toJSONString(form),OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 删除 TODO 回头要调整将关联关系的所有数据进行删除或验证是否可以删除操作
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public Object delete(String id) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}

		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			Type type = typeService.getById(id);
			if(null == type){
				return AllResult.build(1, "分类不存在");
			}

			List<Type> menuList = typeService.getByParentId(id);
			if (null != menuList && !menuList.isEmpty()) {
				return AllResult.build(0, "存在子分类不允许删除");
			}

			typeService.deleteById(id);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.type,
					JSON.toJSONString(type),OperLog.logLevelEnum.success);

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("delete type object error. : {}", id, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.type, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public Object update(TypeForm form) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("params[typeForm: {}]", JSON.toJSONString(form));
		}

		//验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(form.getId()).notEmpty().maxLength(32), "分类ID提供且最大长度32位")
				.addGroup(GroupBuilder.build(form.getName()).notEmpty().maxLength(50), "分类名称提供且最大长度50位")
				// 非必输条件验证
//				.addGroup(GroupBuilder.buildOr(form.getParentId()).empty().maxLength(32), "父ID最大长度为32位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		Type type = typeService.getById(form.getId());
		if(null == type){
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "分类不存在");
		}

		//验证是否已经存在分类名称
		TypeExample te = new TypeExample();
		TypeExample.Criteria c1 = te.createCriteria();
		c1.andNameEqualTo(form.getName());
		List<Type> typeList = typeService.getByExample(te);
		if(null != typeList && typeList.size() != 0){
			if(typeList.size()>1 || !type.getId().equals(typeList.get(0).getId())){
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "分类名称已存在，不允许重复");
			}
		}

		try {
			//修改
			type.setName(form.getName());
			type.setUpdateName(null);
			type.setUpdateDate(null);
			typeService.updateById(type);

			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.type,
					JSON.toJSONString(type),OperLog.logLevelEnum.success);

			//返回的对象
			TypeResult tr = new TypeResult();
			BeanUtils.copyProperties(type,tr);

			return AllResult.okJSON(tr);
		} catch (Exception e) {
			LOGGER.error("update type object error. : type: {}", JSON.toJSONString(type), e);
			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.type, null,
					OperLog.logLevelEnum.error);
		}
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 获取详情
	 */
	@RequestMapping(value = "/getTypeId", method = { RequestMethod.GET, RequestMethod.POST })
	public Object get(String id) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}

		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			Type type = typeService.getById(id);

			if(null == type){
				return AllResult.build(1, "分类不存在");
			}

			//返回的对象
			TypeResult tr = new TypeResult();
			BeanUtils.copyProperties(type,tr);

			operLogService.addSystemLog(OperLog.operTypeEnum.select,
					OperLog.actionSystemEnum.type, "查询条件id:"+id,
					OperLog.logLevelEnum.success);

			return AllResult.okJSON(tr);
		} catch (Exception e) {
			LOGGER.error("type object error. id:{}", id, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.type, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 获取根据父ID获取所有子ID
	 */
	@RequestMapping(value = "/getParentId", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getParentId(String parentId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [parentId: {}]", parentId);
		}

		try {
			List<Type> typeList = typeService.getByParentId(parentId);

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(typeList,FastjsonUtils.newIgnorePropertyFilter("level","updateName","updateDate","createName","createDate"));

			// 增加日志
//			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menu,jsonStr,OperLog.logLevelEnum.success);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("typeLists object error. parentId:{}", parentId, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.type, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 获取所有分类
	 */
	@RequestMapping(value = "/getAll", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getAll() {
		try {
			List<Type> types = typeService.getAll();

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(types,
					FastjsonUtils.newIgnorePropertyFilter("level","updateName","updateDate","createName","createDate"));

			// 增加日志
//			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menu,jsonStr,OperLog.logLevelEnum.success);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("types object error. getAll ", e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.type, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 获取Tree
	 */
	@RequestMapping(value = "/tree", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getTree(String id) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}

		try {
			// 根据条件查询某一级分类（空时查询一级分类；不等与空时查询该分类下的所有子分类）
			TypeExample example = new TypeExample();
			TypeExample.Criteria criteria = example.createCriteria();
			if(StringUtils.isNotEmpty(id)){
				criteria.andIdEqualTo(id);
			}else{
				criteria.andParentIdIsNull();
			}

			List<Type> types = typeService.getTree(example);

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(types,
					FastjsonUtils.newIgnorePropertyFilter("level","updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("types object error. id:{}", id, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.type, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 分页获取分类信息
	 */
	@RequestMapping(value = "/datagrid", method = { RequestMethod.GET, RequestMethod.POST })
	public Object datagrid(TypeForm form) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", form.getPageNum(), form.getPageSize());
		}

		//1.验证参数
		String errorTip = ValidationHelper.build()
				//必输条件验证
				.addGroup(GroupBuilder.build(form.getPageNum()).notNull().minValue(1), "页码必须从1开始")
				.addGroup(GroupBuilder.build(form.getPageSize()).notNull().minValue(1), "每页记录数量最少1条")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			TypeExample example = new TypeExample();
			TypeExample.Criteria criteria = example.createCriteria();
			criteria.andParentIdIsNull();
			// 条件设置
			if (StringUtils.isNotEmpty(form.getName())) {
				criteria.andNameLike("%" + form.getName().trim() + "%");
			}

			Page<Type> queryResult = typeService.getScrollData(form.getPageNum(), form.getPageSize(), example);

			Page<TypeTreeResult> treePage = new Page<>();
			BeanUtils.copyProperties(queryResult,treePage);

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(treePage,
					FastjsonUtils.newIgnorePropertyFilter("level","updateName","updateDate","createName","createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get scroll data error. page: {}, count: {}", form.getPageNum(), form.getPageSize(), e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.type, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}
}
