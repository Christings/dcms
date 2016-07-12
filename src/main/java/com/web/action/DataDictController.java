package com.web.action;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.web.core.action.BaseController;
import com.web.core.util.Page;
import com.web.entity.DataDict;
import com.web.entity.OperLog;
import com.web.example.DataDictExample;
import com.web.service.DataDictService;
import com.web.util.AllResult;
import com.web.util.StringUtil;

/**
 * 数据词典控制器
 *
 * @author 田军兴
 * @date 2016-07-09
 */
@Controller
@RequestMapping(value = "/dataDict")
public class DataDictController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);
	@Autowired
	private DataDictService dataDictService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object save(DataDict dataDict, HttpServletRequest request) {
		try {
			if (null == dataDict) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request add dataDict param: [dataDict: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "数据词典入参为空");
			}
			if (StringUtil.isEmpty(dataDict.getDictValue())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "数据词典的关键值不能为空");
			}
			if (dataDictService.save(dataDict) > 0) {
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.insert, OperLog.actionSystemEnum.dataDic,
						JSON.toJSONString(dataDict));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("数据词典新增成功", JSON.toJSONString(dataDict));
				}
				return AllResult.okJSON(dataDict);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "词典新增失败:数据未能持久化");
			}
		} catch (Exception e) {
			LOGGER.error("词典新增失败,后台报错", JSON.toJSONString(dataDict));
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "词典新增失败:后台报错");
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Object update(DataDict dataDict, HttpServletRequest request) {
		try {
			if (null == dataDict) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request edit dataDict param: [dataDict: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "数据词典入参为空");
			}
			if (StringUtil.isEmpty(dataDict.getDictValue())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "数据词典的关键值不能为空");
			}
			if (dataDictService.updateById(dataDict) > 0) {
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.dataDic,
						JSON.toJSONString(dataDict));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("数据词典修改成功", JSON.toJSONString(dataDict));
				}
				return AllResult.okJSON(dataDict);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "词典修改失败:数据未能持久化");
			}
		} catch (Exception e) {
			LOGGER.error("词典修改失败,后台报错", JSON.toJSONString(dataDict));
			operLogService.addSystemLog(OperLog.operTypeEnum.update, OperLog.actionSystemEnum.dataDic, null,
					OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "词典修改失败:后台报错");
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(DataDict dataDict, HttpServletRequest request) {
		try {
			if (null == dataDict) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request delete dataDict param: [dataDict: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "数据词典入参为空");
			}
			if (StringUtil.isEmpty(dataDict.getId())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "数据词典ID不能为空");
			}
			if (dataDictService.deleteById(dataDict.getId()) > 0) {
				// 增加日志
				operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.dataDic,
						JSON.toJSONString(dataDict));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("数据词典删除成功", JSON.toJSONString(dataDict));
				}
				return AllResult.okJSON(dataDict);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "词典删除失败:删除条数0");
			}
		} catch (Exception e) {
			LOGGER.error("词典删除失败,后台报错", JSON.toJSONString(dataDict));
			operLogService.addSystemLog(OperLog.operTypeEnum.delete, OperLog.actionSystemEnum.dataDic, null,
					OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "词典删除失败:后台报错");
		}
	}

	@RequestMapping(value = "/selectById", method = RequestMethod.POST)
	@ResponseBody
	public Object selectById(DataDict dataDict, HttpServletRequest request) {
		try {
			if (null == dataDict) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request select dataDict param: [dataDict: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "入参不能为空");
			}
			if (StringUtil.isEmpty(dataDict.getId())) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request select dataDict param: [dataDict.id: {null}]");
				}
				operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.dataDic, null);
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "数据词典ID不能为空");
			}
			return AllResult.okJSON(dataDictService.getById(dataDict.getId()));
		} catch (Exception e) {
			LOGGER.error("词典删除失败,后台报错", JSON.toJSONString(dataDict));
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.dataDic, null,
					OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "后台出错");
		}
	}

	@RequestMapping(value = "/selectByGroup", method = RequestMethod.POST)
	@ResponseBody
	public Object selectByGroup(DataDict dataDict, HttpServletRequest request) {
		try {
			if (null == dataDict) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request select dataDict param: [dataDict: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "入参不能为空");
			}
			if (StringUtil.isEmpty(dataDict.getGroupId())) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request select dataDict param: [dataDict.groupId: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "数据词典GROUP_ID不能为空");
			}
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.dataDic, null);
			return AllResult.okJSON(dataDictService.getByGroupId(dataDict));
		} catch (Exception e) {
			LOGGER.error("根据GROUP_ID获取词典失败,后台报错", JSON.toJSONString(dataDict));
			operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.dataDic, null,
					OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "后台出错");
		}
	}

	@RequestMapping(value = "/getPage", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getPageData(@RequestBody Page<DataDict> page, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", page.getPageNo(), page.getPageSize());
		}
		// 校验参数
		if (page.getPageNo() < 1 || page.getPageSize() < 1) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}
		try {
			DataDictExample example = new DataDictExample();
			// 排序设置
			example.setOrderByClause("SORT asc");
			DataDictExample.Criteria criteria = example.createCriteria();
			dataDictService.getByPage(page, example);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("queryResult record count: {}", page.getPageSize());
			}
			return AllResult.okJSON(page);
		} catch (Exception e) {
			LOGGER.error("get dataDict data error. page: {}, count: {}", page, e);
		}
		operLogService.addSystemLog(OperLog.operTypeEnum.select, OperLog.actionSystemEnum.dataDic, null, OperLog.logLevelEnum.error);
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

}
