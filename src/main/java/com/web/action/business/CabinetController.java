package com.web.action.business;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.web.bean.form.CabinetForm;
import com.web.bean.result.CabinetResult;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.*;
import com.web.example.BoxEquipmentExample;
import com.web.example.CabinetExample;
import com.web.service.BoxEquipmentService;
import com.web.service.CabinetService;
import com.web.service.RoomIcngphService;
import com.web.service.RoomService;
import com.web.util.AllResult;
import com.web.util.StringUtil;
import com.web.util.UUIDGenerator;
import com.web.util.YmlUtil;
import com.web.util.fastjson.FastjsonUtils;

/**
 * 机柜管理控制器
 *
 * @author 田军兴
 * @date 2016-11-7
 */
@RestController
@RequestMapping("/cabinet")
public class CabinetController extends BaseController {

	@Autowired
	private CabinetService cabinetService;
	@Autowired
	private BoxEquipmentService boxEquipmentService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private RoomIcngphService roomIcngphService;

	private static final Logger LOGGER = LoggerFactory.getLogger(CabinetController.class);

	/**
	 * 新增机柜
	 *
	 * @param cabinet
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object save(Cabinet cabinet) {
		try {
			if (null == cabinet) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request add cabinet param: [cabinet: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "新增机柜入参为空");
			}
			if (StringUtil.isEmpty(cabinet.getResourceCode())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "资源编码不能为空");
			}
			if (StringUtil.isEmpty(cabinet.getEquipmentTypeId())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "设备类型不能为空");
			}
			CabinetExample example = new CabinetExample();
			CabinetExample.Criteria criteria = example.createCriteria();
			criteria.andResourceCodeEqualTo(cabinet.getResourceCode());
			int countExist = cabinetService.getCount(example);
			if (countExist > 0) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "资源编码已存在，请检查");
			}
			cabinet.setId(UUIDGenerator.generatorRandomUUID());
			if (cabinetService.save(cabinet) > 0) {
				// 增加日志
				operLogService.addBusinessLog(cabinet.getName(), OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.cabinet,
						JSON.toJSONString(cabinet));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("机柜新增成功", JSON.toJSONString(cabinet));
				}
				return AllResult.okJSON(cabinet);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机柜新增失败:数据未能持久化");
			}
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(cabinet.getName(), OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.cabinet,
					JSON.toJSONString(cabinet), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机柜新增失败:后台报错");
		}
	}

	/**
	 * 新增机柜
	 *
	 * @param cabinet
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(Cabinet cabinet) {
		try {
			if (null == cabinet) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request add cabinet param: [cabinet: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "修改机柜入参不能为空");
			}
			if (StringUtil.isEmpty(cabinet.getId())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机柜ID不能为空");
			}
			if (StringUtil.isEmpty(cabinet.getEquipmentTypeId())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "设备类型不能为空");
			}
			CabinetExample example = new CabinetExample();
			CabinetExample.Criteria criteria = example.createCriteria();
			criteria.andResourceCodeEqualTo(cabinet.getResourceCode());
			criteria.andIdNotEqualTo(cabinet.getId());
			int countExist = cabinetService.getCount(example);
			if (countExist > 0) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "资源编码已存在，请检查");
			}
			if (cabinetService.updateById(cabinet) > 0) {
				// 增加日志
				operLogService.addBusinessLog(cabinet.getName(), OperLog.operTypeEnum.update, OperLog.actionBusinessEnum.cabinet,
						JSON.toJSONString(cabinet));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("机柜修改成功", JSON.toJSONString(cabinet));
				}
				return AllResult.okJSON(cabinet);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机柜修改失败:数据未能持久化");
			}
		} catch (Exception e) {
			e.printStackTrace();
			;
			operLogService.addBusinessLog(cabinet.getName(), OperLog.operTypeEnum.update, OperLog.actionBusinessEnum.cabinet,
					JSON.toJSONString(cabinet), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机柜修改失败:后台报错");
		}
	}

	/**
	 * 新增机柜
	 *
	 * @param cabinet
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(Cabinet cabinet) {
		if (StringUtil.isEmpty(cabinet.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机柜ID不能为空");
		}
		String cabinetName = "";
		try {
			Cabinet cabinet1 = cabinetService.getById(cabinet.getId());
			if (null != cabinet1) {
				cabinetName = cabinet1.getName();
			}
			// 校验是否有设备在机柜上，如果有不允许删除
			BoxEquipmentExample example = new BoxEquipmentExample();
			BoxEquipmentExample.Criteria criteria = example.createCriteria();
			criteria.andCabinetIdEqualTo(cabinet.getId());
			int deviceCount = boxEquipmentService.getCount(example);
			if (deviceCount > 0) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "有设备与机柜关联，不允许删除");
			} else {
				cabinetService.deleteById(cabinet.getId());
				operLogService.addBusinessLog(cabinetName, OperLog.operTypeEnum.delete, OperLog.actionBusinessEnum.cabinet,
						JSON.toJSONString(cabinet));
				return AllResult.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
			;
			operLogService.addBusinessLog(cabinetName, OperLog.operTypeEnum.delete, OperLog.actionBusinessEnum.cabinet,
					JSON.toJSONString(cabinet), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机柜删除失败:后台报错");
		}
	}

	/**
	 * 分页查询机柜信息
	 *
	 * @param form
	 */
	@RequestMapping(value = "/datagrid", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getPageData(CabinetForm form) {
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
				} else if ("resourceCode".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("a.resource_code " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("status".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("a.status " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("roomName".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("b.name " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				}
			}
			orderBy.append("a.create_date desc");
			form.setOrderByClause(orderBy.toString());
			Page<CabinetResult> queryResult = cabinetService.getByPage(form.getPageNum(), form.getPageSize(), form);
			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateCreate", "createName", "createDate"));

			operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.cabinet, null);
			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("get cabinet data error. page: {}, count: {}", form.getPageNum(), e);
		}
		operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.cabinet, null,
				OperLog.logLevelEnum.error);
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 新增机柜设备关联关系
	 *
	 */
	@RequestMapping(value = "/addBoxEquipmeng", method = { RequestMethod.GET, RequestMethod.POST })
	public Object addBoxEquipmeng(BoxEquipment boxEquipment) {
		if (null == boxEquipment) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("request add cabinet param: [BoxEquipmeng: {null}]");
			}
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "新增机柜和设备关联关系入参不能为空");
		}
		try {
			if (StringUtil.isEmpty(boxEquipment.getCabinetId())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机柜ID不能为空");
			}
			if (StringUtil.isEmpty(boxEquipment.getEquipmentId())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "设备ID不能为空");
			}
			if (StringUtil.isEmpty(boxEquipment.getPos())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "位置信息不能为空");
			}
			if (StringUtil.isEmpty(boxEquipment.getConfirmed())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "上架信息不能为空");
			}
			BoxEquipmentExample example = new BoxEquipmentExample();
			BoxEquipmentExample.Criteria criteria = example.createCriteria();
			criteria.andCabinetIdEqualTo(boxEquipment.getCabinetId());
			criteria.andEquipmentIdEqualTo(boxEquipment.getEquipmentId());
			int exCount = boxEquipmentService.getCount(example);
			if (exCount > 0) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "对应关系已存在");
			}
			boxEquipment.setId(UUIDGenerator.generatorRandomUUID());
			boxEquipmentService.save(boxEquipment);
			operLogService.addBusinessLog("", OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.cabinet,
					JSON.toJSONString(boxEquipment));
			return AllResult.okJSON(boxEquipment);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog("", OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.cabinet,
					JSON.toJSONString(boxEquipment), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "保存失败");
		}
	}

	/**
	 * 根据机柜id获取单个机柜信息
	 * 
	 * @param form
	 */
	@RequestMapping(value = "/getCabinetResultById", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getCabinetResultById(CabinetForm form) {
		if (null == form) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("request cabinet getCabinetResultById param: [CabinetForm: {null}]");
			}
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "入参不能为空");
		}
		if (StringUtil.isEmpty(form.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机柜ID不能为空");
		}
		try {
			CabinetResult result = cabinetService.selectResultById(form.getId());
			return AllResult.okJSON(result);
		} catch (Exception e) {
			e.printStackTrace();
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "查询失败");
		}
	}

	/**
	 * 根据机柜资源信息获取坐标和机房等信息
	 *
	 */
	@RequestMapping(value = "/getPositionByResourceCode", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getPositionByResourceCode(CabinetForm form) {
		if (null == form) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("request cabinet getPositionByResourceCode param: [CabinetForm: {null}]");
			}
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "入参不能为空");
		}
		if (StringUtil.isEmpty(form.getResourceCode())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机柜资源编码不能为空");
		}
		try {
			CabinetExample example = new CabinetExample();
			CabinetExample.Criteria criteria = example.createCriteria();
			criteria.andResourceCodeEqualTo(form.getResourceCode());
			List<Cabinet> cabinets = cabinetService.getByExample(example);
			if (cabinets.size() != 1) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "查不到相应的机柜信息");
			}
			Cabinet cabinet = cabinets.get(0);
			Room room = roomService.getById(cabinet.getRoomId());
			if (null == room || StringUtil.isEmpty(room.getResourceCode())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "无法找到对应机房信息");
			}
			List<RoomIcngph> list = roomIcngphService.getAll();
			RoomIcngph roomIcngph = null;
			for (RoomIcngph icngph : list) {
				try {
					Map map = YmlUtil.getYmlString(icngph.getYmlRealPath());
					if (null != map.get(room.getResourceCode())) {
						roomIcngph = icngph;
						break;
					}
				} catch (IOException ioe) {
					continue;
				}
			}
			if (null == roomIcngph) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "无法找到对应机房平面图信息");
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("cabinet", cabinet);
			resultMap.put("roomId", cabinet.getRoomId());
			resultMap.put("roomIcngphId", roomIcngph.getId());
			return AllResult.okJSON(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "查询失败");
		}
	}
}
