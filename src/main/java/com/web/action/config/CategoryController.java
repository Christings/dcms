package com.web.action.config;

import static com.web.util.AllResult.buildJSON;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.web.bean.form.CategoryForm;
import com.web.bean.result.CategoryResult;
import com.web.bean.result.CategoryTreeResult;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.Category;
import com.web.entity.OperLog;
import com.web.example.CategoryExample;
import com.web.service.CategoryService;
import com.web.util.AllResult;
import com.web.util.UUIDGenerator;
import com.web.util.fastjson.FastjsonUtils;
import com.web.util.validation.GroupBuilder;
import com.web.util.validation.ValidationHelper;

/**
 * 设备分类管理接口
 *
 * @author 杜延雷
 * @date 2016-11-14
 */
@RestController
@RequestMapping("/product/category")
public class CategoryController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	CategoryService categoryService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public Object add(CategoryForm form) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [categoryForm: {}]", JSON.toJSONString(form));
		}

		// 验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(form.getName()).notEmpty().maxLength(50), "设备分类名称提供且最大长度50位")
				// 非必输条件验证
				.addGroup(GroupBuilder.buildOr(form.getParentId()).empty().maxLength(32), "父ID最大长度为32位")
				.addGroup(GroupBuilder.buildOr(form.getDescription()).empty().maxLength(255), "描述最大长度为255位").validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		// 定义父类变量
		Category category = null;

		// 处理外键关联数据传空值问题
		if (null != form.getParentId() && !"".equals(form.getParentId().trim())) {
			category = categoryService.getById(form.getParentId());
			if (null == category) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "父级分类不存在");
			} else {
				form.setLevel(category.getLevel().intValue() + 1);
			}
		} else {
			form.setParentId(null);
			form.setLevel(1);
		}

		CategoryExample pce = new CategoryExample();
		CategoryExample.Criteria c1 = pce.createCriteria();
		c1.andNameEqualTo(form.getName());
		if (categoryService.countByExample(pce) > 0) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "分类名称已存在，不允许重复添加!!!");
		}

		try {
			Category pc = new Category();
			BeanUtils.copyProperties(form, pc, "id");
			pc.setId(UUIDGenerator.generatorRandomUUID());
			categoryService.save(pc);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.category, JSON.toJSONString(pc),
					OperLog.logLevelEnum.success);

			// 返回对象
			CategoryResult pcr = new CategoryResult();
			BeanUtils.copyProperties(pc, pcr);

			return AllResult.okJSON(pcr);
		} catch (Exception e) {
			LOGGER.error("save category object error. : {}", JSON.toJSONString(form), e);
			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.category, JSON.toJSONString(form),
					OperLog.logLevelEnum.error);
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
			Category category = categoryService.getById(id);
			if (null == category) {
				return AllResult.build(1, "分类不存在");
			}

			List<Category> menuList = categoryService.getByParentId(id);
			if (null != menuList && !menuList.isEmpty()) {
				return AllResult.build(0, "存在子分类不允许删除");
			}

			categoryService.deleteById(id);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.category, JSON.toJSONString(category),
					OperLog.logLevelEnum.success);

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("delete category object error. : {}", id, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.category, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public Object update(CategoryForm form) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("params[categoryForm: {}]", JSON.toJSONString(form));
		}

		// 验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(form.getId()).notEmpty().maxLength(32), "分类ID提供且最大长度32位")
				.addGroup(GroupBuilder.build(form.getName()).notEmpty().maxLength(50), "分类名称提供且最大长度50位")
				// 非必输条件验证
				.addGroup(GroupBuilder.buildOr(form.getDescription()).empty().maxLength(255), "描述最大长度为255位")
				// .addGroup(GroupBuilder.buildOr(form.getParentId()).empty().maxLength(32),
				// "父ID最大长度为32位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		Category category = categoryService.getById(form.getId());
		if (null == category) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "分类不存在");
		}

		// 验证是否已经存在分类名称
		CategoryExample te = new CategoryExample();
		CategoryExample.Criteria c1 = te.createCriteria();
		c1.andNameEqualTo(form.getName());
		List<Category> categoryList = categoryService.getByExample(te);
		if (null != categoryList && categoryList.size() != 0) {
			if (categoryList.size() > 1 || !category.getId().equals(categoryList.get(0).getId())) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "分类名称已存在，不允许重复");
			}
		}

		try {
			// 修改
			category.setName(form.getName());
			category.setDescription(form.getDescription());
			category.setUpdateName(null);
			category.setUpdateDate(null);
			categoryService.updateById(category);

			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.category, JSON.toJSONString(category),
					OperLog.logLevelEnum.success);

			// 返回的对象
			CategoryResult tr = new CategoryResult();
			BeanUtils.copyProperties(category, tr);

			return AllResult.okJSON(tr);
		} catch (Exception e) {
			LOGGER.error("update category object error. : category: {}", JSON.toJSONString(category), e);
			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.category, null,
					OperLog.logLevelEnum.error);
		}
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 获取详情
	 */
	@RequestMapping(value = "/getCategoryId", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getCategoryById(String id) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}

		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数异常");
		}

		try {
			Category category = categoryService.getById(id);

			if (null == category) {
				return AllResult.build(1, "分类不存在");
			}

			// 返回的对象
			CategoryResult tr = new CategoryResult();
			BeanUtils.copyProperties(category, tr);

			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.category, "查询条件id:" + id,
					OperLog.logLevelEnum.success);

			return AllResult.okJSON(tr);
		} catch (Exception e) {
			LOGGER.error("category object error. id:{}", id, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.category, null,
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
			List<Category> categoryList = categoryService.getByParentId(parentId);

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(categoryList,
					FastjsonUtils.newIgnorePropertyFilter("level", "updateName", "updateDate", "createName", "createDate"));

			// 增加日志
			// operLogService.addSystemLog(OperLog.operTypeEnum.select,
			// OperLog.actionSystemEnum.menu,jsonStr,OperLog.logLevelEnum.success);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("categoryLists object error. parentId:{}", parentId, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.category, null,
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
			List<Category> categories = categoryService.getAll();

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(categories,
					FastjsonUtils.newIgnorePropertyFilter("level", "updateName", "updateDate", "createName", "createDate"));

			// 增加日志
			// operLogService.addSystemLog(OperLog.operTypeEnum.select,
			// OperLog.actionSystemEnum.menu,jsonStr,OperLog.logLevelEnum.success);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("categorys object error. getAll ", e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.category, null,
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
			CategoryExample example = new CategoryExample();
			CategoryExample.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotEmpty(id)) {
				criteria.andIdEqualTo(id);
			} else {
				criteria.andParentIdIsNull();
			}

			List<Category> categories = categoryService.getTree(example);

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(categories,
					FastjsonUtils.newIgnorePropertyFilter("level", "updateName", "updateDate", "createName", "createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("categorys object error. id:{}", id, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.category, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 分页获取分类信息
	 */
	@RequestMapping(value = "/datagrid", method = { RequestMethod.GET, RequestMethod.POST })
	public Object datagrid(CategoryForm form) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", form.getPageNum(), form.getPageSize());
		}

		// 1.验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(form.getPageNum()).notNull().minValue(1), "页码必须从1开始")
				.addGroup(GroupBuilder.build(form.getPageSize()).notNull().minValue(1), "每页记录数量最少1条").validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			CategoryExample example = new CategoryExample();
			CategoryExample.Criteria criteria = example.createCriteria();
			criteria.andParentIdIsNull();
			// 条件设置
			if (StringUtils.isNotEmpty(form.getName())) {
				criteria.andNameLike("%" + form.getName().trim() + "%");
			}

			example.setOrderByClause("create_date desc,id asc");

			Page<Category> queryResult = categoryService.getScrollData(form.getPageNum(), form.getPageSize(), example);

			Page<CategoryTreeResult> treePage = new Page<>();
			BeanUtils.copyProperties(queryResult, treePage);

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(treePage,
					FastjsonUtils.newIgnorePropertyFilter("level", "updateName", "updateDate", "createName", "createDate"));

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get scroll data error. page: {}, count: {}", form.getPageNum(), form.getPageSize(), e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.category, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}
}
