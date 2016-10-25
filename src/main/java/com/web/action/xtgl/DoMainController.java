package com.web.action.xtgl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.DoMain;
import com.web.entity.OperLog;
import com.web.example.DoMainExample;
import com.web.service.DoMainService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.web.util.AllResult.buildJSON;

/**
 * 域管理（组织结构） 前端访问接口
 *
 * @author 杜延雷
 * @date 2016-10-22
 */
@RestController
@RequestMapping("/domain")
public class DoMainController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DoMainController.class);

	@Autowired
	DoMainService doMainService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public Object add(DoMain doMain) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [doMain: {}]", JSON.toJSONString(doMain));
		}

		//1.验证参数
		String errorTip = ValidationHelper.build()
				//必输条件验证
				.addGroup(GroupBuilder.build(doMain.getName()).notEmpty().maxLength(50), "名称必须提供且最大长度50位")

				//非必输条件验证
				.addGroup(GroupBuilder.buildOr(doMain.getDescription()).empty().maxLength(255),"描述最大长度255位")
				.addGroup(GroupBuilder.buildOr(doMain.getParentId()).empty().maxLength(32),"父类ID最大长度为32位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			if (StringUtils.isNotEmpty(doMain.getParentId())){
				DoMain dm = doMainService.getById(doMain.getParentId());
				if (null == dm) {
					return buildJSON(HttpStatus.BAD_REQUEST.value(), "父级ID不存在");
				}else {
					doMain.setLevel(dm.getLevel()+1);
				}
			}else{
				doMain.setLevel(1);
			}

			doMain.setId(UUIDGenerator.generatorRandomUUID());
			doMainService.save(doMain);

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.domain,
					JSON.toJSONString(doMain));

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(doMain,FastjsonUtils.newIgnorePropertyFilter("childDoMain","level","updateName","updateDate","createName","createDate"), SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("save doMain object error. : {}", JSON.toJSONString(doMain), e);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加失败");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public Object delete(String id) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}

		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数ID必须提供");
		}

		try {
			DoMain doMain = doMainService.getById(id);
			if(null == doMain){
				return AllResult.build(0, "未查找到该信息");
			}

			DoMainExample example = new DoMainExample();
			DoMainExample.Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(id);
			int count = doMainService.countByExample(example);
			if(count>0){
				return AllResult.build(0, "存在子级不允许删除");
			}

			int result = doMainService.deleteById(id);

			if(result > 0){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.domain,
						JSON.toJSONString(doMain));
			}

			return AllResult.ok();
		} catch (Exception e) {
			LOGGER.error("delete doMain object error. : id{}", id, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.domain, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,删除失败");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public Object update(DoMain doMain, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("params[doMain: {}]", JSON.toJSONString(doMain));
		}

		//1.验证参数
		String errorTip = ValidationHelper.build()
				//必输条件验证
				.addGroup(GroupBuilder.build(doMain.getId()).notEmpty().maxLength(32), "ID必须提供且最大长度32位")
				.addGroup(GroupBuilder.build(doMain.getName()).notEmpty().maxLength(50), "名称必须提供且最大长度50位")

				//非必输条件验证
				.addGroup(GroupBuilder.buildOr(doMain.getDescription()).empty().maxLength(255),"描述最大长度255位")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			DoMain dm = doMainService.getById(doMain.getId());
			if(null == dm) {
				return buildJSON(0, "未查询到该信息");
			}else{
				dm.setName(doMain.getName());
				dm.setDescription(doMain.getDescription());
			}

			int result = doMainService.updateById(dm);

			if(result > 0){
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.domain,
						JSON.toJSONString(dm));
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(dm,FastjsonUtils.newIgnorePropertyFilter("childDoMain","level","updateName","updateDate","createName","createDate"), SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("update doMain object error. : doMain: {}", JSON.toJSONString(doMain), e);
			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.domain, null,
					OperLog.logLevelEnum.error);
		}
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,更新信息失败");
	}

	/**
	 * 获取详情
	 */
	@RequestMapping(value = "/get", method = { RequestMethod.GET, RequestMethod.POST })
	public Object get(String id) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [id: {}]", id);
		}

		if (StringUtils.isEmpty(id)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请求参数ID必须提供");
		}

		try {
			DoMain dm = doMainService.getById(id);
			if(null == dm){
				return AllResult.build(0, "未查询到信息");
			}

			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.domain, "查询条件id:"+id);

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(dm,FastjsonUtils.newIgnorePropertyFilter("childDoMain","level","updateName","updateDate","createName","createDate"));
			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("doMain object error. id:{}", id, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.domain, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取信息失败");
	}

	/**
	 * 获取根据父ID获取所有子域
	 */
	@RequestMapping(value = "/getParentId", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getParentId(String parentId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [parentId: {}]", parentId);
		}

		try {
			List<DoMain> doMainList = doMainService.getByParentId(parentId);

			if(null == doMainList || doMainList.size() == 0){
				return AllResult.build(1, "未查询到子信息");
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(doMainList,FastjsonUtils.newIgnorePropertyFilter("childDoMain","level","updateName","updateDate","createName","createDate"));

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menu,jsonStr);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("doMains object error. parentId:{}", parentId, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.domain, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取信息失败");
	}

	/**
	 * 获取所有信息(不分级别)
	 */
	@RequestMapping(value = "/getAll", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getAll() {
		try {
			List<DoMain> doMainList = doMainService.getAll();

			if(null == doMainList || doMainList.size() == 0){
				return AllResult.build(1, "未查询到信息");
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(doMainList,FastjsonUtils.newIgnorePropertyFilter("childDoMain","level","updateName","updateDate","createName","createDate"));

			// 增加日志
//			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.domain,jsonStr);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("doMains object error. getAll ", e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.domain, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取所有信息失败");
	}

	/**
	 * 分页获取域信息
	 *
	 * @param pageNum 当前页
	 * @param pageSize 显示多少行
	 */
	@RequestMapping(value = "/datagrid", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getDatagrid(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "parentId",required = false)String parentId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", pageNum, pageSize);
		}

		//1.验证参数
		String errorTip = ValidationHelper.build()
				//必输条件验证
				.addGroup(GroupBuilder.build(pageNum).notNull().minValue(1), "页码必须从1开始")
				.addGroup(GroupBuilder.build(pageSize).notNull().minValue(1), "每页记录数量最少1条")
				.validate();

		if (StringUtils.isNotEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {
			DoMainExample example = new DoMainExample();
			DoMainExample.Criteria criteria = example.createCriteria();
			if(StringUtils.isNotEmpty(parentId) && !"".equals(parentId.trim())){
				criteria.andParentIdEqualTo(parentId);
			}else{
				criteria.andLevelEqualTo(1);
			}

			Page<DoMain> queryResult = doMainService.getScrollData(pageNum, pageSize, example);

			if(null == queryResult.getRecords() || queryResult.getRecords().size() == 0){
				return AllResult.build(1, "未获取到信息");
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,FastjsonUtils.newIgnorePropertyFilter("level","updateName","updateDate","createName","createDate"));

			// 增加日志
//			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.domain,jsonStr);

			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get scroll data error. page: {}, count: {}", pageNum, pageSize, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.domain, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 获取根据父ID获取所有子域
	 */
	@RequestMapping(value = "/tree", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getTree(String parentId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [parentId: {}]", parentId);
		}

		try {
			Map<String,String> params = new HashMap<>();
			if(StringUtils.isEmpty(parentId)||"".equals(parentId.trim())){
				params.put("parentId",null);
			}else{
				params.put("parentId",parentId);
			}
			List<DoMain> doMainList = doMainService.getTree(params);

			if(null == doMainList || doMainList.size() == 0){
				return AllResult.build(1, "未查询到信息");
			}

			//去除不需要的字段
			String jsonStr = JSON.toJSONString(doMainList,FastjsonUtils.newIgnorePropertyFilter("level","updateName","updateDate","createName","createDate"));

			// 增加日志
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.menu,jsonStr);

			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("doMains object error. parentId:{}", parentId, e);
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.domain, null,
					OperLog.logLevelEnum.error);
		}

		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,获取信息失败");
	}

}
