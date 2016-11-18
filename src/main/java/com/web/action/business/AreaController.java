package com.web.action.business;

import java.util.Arrays;
import java.util.List;

import com.web.entity.Cabinet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.web.bean.form.AreaForm;
import com.web.bean.result.AreaResult;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.Area;
import com.web.entity.OperLog;
import com.web.example.CabinetExample;
import com.web.service.AreaService;
import com.web.service.CabinetService;
import com.web.util.AllResult;
import com.web.util.JSONUtil;
import com.web.util.StringUtil;
import com.web.util.UUIDGenerator;
import com.web.util.fastjson.FastjsonUtils;

/**
 * ${description}
 *
 * @author 田军兴
 * @date ${date}
 */
@RestController
@RequestMapping("/area")
public class AreaController extends BaseController {
	@Autowired
	private AreaService areaService;
	@Autowired
	private CabinetService cabinetService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

	/**
	 * 新增区域管理
	 *
	 * @param area
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object save(Area area) {
		if (null == area) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("request add area param: [area: {null}]");
			}
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "新增区域入参为空");
		}
		if (StringUtil.isEmpty(area.getName())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "区域名称不能为空");
		}
		if (StringUtil.isEmpty(area.getRoomId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "新增区域机房ID不能为空");
		}
		try {
			area.setId(UUIDGenerator.generatorRandomUUID());
			areaService.save(area);
			if (StringUtil.isNotEmpty(area.getCabinetResourceCodes())) {
				String[] strings = area.getCabinetResourceCodes().split(",");
				List<String> list = Arrays.asList(strings);
				CabinetExample example = new CabinetExample();
				CabinetExample.Criteria criteria = example.createCriteria();
				criteria.andResourceCodeIn(list);
				cabinetService.updateAreaIdByExample(example, area.getId());
			}
			operLogService.addBusinessLog(area.getName(), OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.area,
					JSONUtil.object2Json(area));
			return AllResult.okJSON(area);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(area.getName(), OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.area,
					JSONUtil.object2Json(area), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "区域新增失败:后台报错");
		}
	}

	/**
	 * 修改区域
	 *
	 * @param area
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(Area area) {
		if (null == area) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("request update area param: [area: {null}]");
			}
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "新增区域入参为空");
		}
		if (StringUtil.isEmpty(area.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "区域ID不能为空");
		}
		if (StringUtil.isEmpty(area.getName())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "区域名称不能为空");
		}
		if (StringUtil.isEmpty(area.getRoomId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房ID不能为空");
		}
		try {
			areaService.updateById(area);
			operLogService.addBusinessLog(area.getName(), OperLog.operTypeEnum.update, OperLog.actionBusinessEnum.area,
					JSONUtil.object2Json(area));
			return AllResult.okJSON(area);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(area.getName(), OperLog.operTypeEnum.update, OperLog.actionBusinessEnum.area,
					JSONUtil.object2Json(area), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "区域修改失败:后台报错");
		}
	}

	/**
	 * 修改区域
	 *
	 * @param area
	 */
	@RequestMapping(value = "/updateCabinetArea", method = RequestMethod.POST)
	public Object updateCabinetArea(Area area) {
		if (null == area) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("request update areaCabinetRel param: [area: {null}]");
			}
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "区域入参为空");
		}
		if (StringUtil.isEmpty(area.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "区域ID不能为空");
		}
		try {
			// 清除机柜和区域旧的关联关系
			cabinetService.updateAreaByExample(area.getId());
			// 重新关联
			if (StringUtil.isNotEmpty(area.getCabinetResourceCodes())) {
				CabinetExample example = new CabinetExample();
				CabinetExample.Criteria criteria = example.createCriteria();
				String[] resourceCodes = area.getCabinetResourceCodes().split(",");
				List<String> list = Arrays.asList(resourceCodes);
				criteria.andResourceCodeIn(list);
				cabinetService.updateAreaIdByExample(example, area.getId());
			}
			return AllResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(area.getName(), OperLog.operTypeEnum.update, OperLog.actionBusinessEnum.area,
					JSONUtil.object2Json(area), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "区域修改失败:后台报错");
		}
	}

	/**
	 * 根据ID获取表单显示信息
	 *
	 * @param form
	 */
	@RequestMapping(value = "/getAreaById", method = RequestMethod.POST)
	public Object getAreaById(AreaForm form) {
		if (null == form) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("request select getAreaById param: [area: {null}]");
			}
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "新增区域入参为空");
		}
		if (StringUtil.isEmpty(form.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "区域ID不能为空");
		}
		String areaName = "";
		try {
			Area area = areaService.getById(form.getId());
			areaName = area.getName();
			AreaResult result = areaService.getAreaResultById(form);
			CabinetExample example = new CabinetExample();
			CabinetExample.Criteria criteria = example.createCriteria();
			criteria.andRoomIdEqualTo(result.getRoomId());
			List<Cabinet> cabinets = cabinetService.selectCodesByExample(example);
			result.setCabinets(cabinets);
			return AllResult.okJSON(result);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(areaName, OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.area,
					JSONUtil.object2Json(form), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "查询区域信息失败:后台报错");
		}
	}

	/**
	 * 分页查询区域信息
	 *
	 * @param form
	 */
	@RequestMapping(value = "/datagrid", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getPageData(AreaForm form) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", form.getPageNum(), form.getPageSize());
		}
		// 校验参数
		if (form.getPageNum() < 1 || form.getPageSize() < 1) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}
		try {
			StringBuffer orderBy = new StringBuffer();
			if (StringUtil.isNotEmpty(form.getSortName())) {
				if ("name".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("a.name " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("roomName".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("b.room_name " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				}
			}
			orderBy.append("a.create_date desc");
			form.setOrderByClause(orderBy.toString());
			Page<AreaResult> queryResult = areaService.getByPage(form.getPageNum(), form.getPageSize(), form);
			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateCreate", "createName", "createDate"));

			operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.area, null);
			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("get area data error. page: {}, count: {}", form.getPageNum(), e);
		}
		operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.area, null,
				OperLog.logLevelEnum.error);
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 分页查询区域信息
	 *
	 * @param form
	 */
	@RequestMapping(value = "/deleteArea", method = { RequestMethod.GET, RequestMethod.POST })
	public Object deleteArea(AreaForm form) {
		if (null == form) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("request delete area param: [area: {null}]");
			}
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "入参不能为空");
		}
		if (StringUtil.isEmpty(form.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "区域ID不能为空");
		}
		Area area = new Area();
		try {
			area = areaService.getById(form.getId());
			CabinetExample example = new CabinetExample();
			CabinetExample.Criteria criteria = example.createCriteria();
			criteria.andAreaIdEqualTo(form.getId());
			int re = cabinetService.getCount(example);
			if (re > 0) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "存在关联此区域的机柜，无法删除");
			}
			areaService.deleteById(form.getId());
			operLogService.addBusinessLog(area.getName(), OperLog.operTypeEnum.delete, OperLog.actionBusinessEnum.area,
					JSON.toJSONString(area));
			return AllResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(area.getName(), OperLog.operTypeEnum.delete, OperLog.actionBusinessEnum.area,
					JSON.toJSONString(area), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
		}
	}
}
