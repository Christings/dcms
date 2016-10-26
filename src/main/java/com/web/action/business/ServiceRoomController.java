package com.web.action.business;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.web.bean.form.ServiceRoomForm;
import com.web.bean.util.FileUtilBean;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.ServiceRoom;
import com.web.example.ServiceRoomExample;
import com.web.service.ServiceRoomService;
import com.web.util.AllResult;
import com.web.util.FileUtil;
import com.web.util.StringUtil;
import com.web.util.UUIDGenerator;
import com.web.util.fastjson.FastjsonUtils;

/**
 * 机房管理控制器
 *
 * @author 田军兴
 * @date 2016-07-30
 */
@RestController
@RequestMapping("/serviceRoom")
public class ServiceRoomController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRoomController.class);

	@Autowired
	private ServiceRoomService serviceRoomService;

	/**
	 * 新增机房
	 *
	 * @param serviceRoom
	 * @param request
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object save(ServiceRoom serviceRoom, MultipartHttpServletRequest request) {
		try {
			if (null == serviceRoom) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request add serviceRoom param: [serviceRoom: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "新增机房入参为空");
			}
			ArrayList<FileUtilBean> files = FileUtil.uploadFiles(request, "upload/serviceRoom", false);// 上传文件
			if (files.size() > 0) {
				serviceRoom.setImageUrl(files.get(0).getFileRealPath());
			}
			serviceRoom.setId(UUIDGenerator.generatorRandomUUID());
			if (serviceRoomService.save(serviceRoom) > 0) {
				// 增加日志
				operLogService.addBusinessLog(serviceRoom.getName(), OperLog.operTypeEnum.insert,
						OperLog.actionBusinessEnum.serviceRoom, JSON.toJSONString(serviceRoom));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("机房新增成功", JSON.toJSONString(serviceRoom));
				}
				return AllResult.okJSON(serviceRoom);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房新增失败:数据未能持久化");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("机房新增失败,后台报错", JSON.toJSONString(serviceRoom));
			operLogService.addBusinessLog(serviceRoom.getName(), OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.serviceRoom,
					JSON.toJSONString(serviceRoom), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房新增失败:后台报错");
		}
	}

	/**
	 * 编辑机房信息
	 *
	 * @param serviceRoom
	 * @param request
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(ServiceRoom serviceRoom, MultipartHttpServletRequest request) {
		try {
			if (null == serviceRoom) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request edit serviceRoom param: [serviceRoom: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "修改机房入参为空");
			}
			if (StringUtil.isEmpty(serviceRoom.getId())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "入参ID不能为空");
			}
			ArrayList<FileUtilBean> files = FileUtil.uploadFiles(request, "upload/serviceRoom", false);// 上传文件
			if (files.size() > 0) {
				serviceRoom.setImageUrl(files.get(0).getFileRealPath());
			}
			if (serviceRoomService.updateById(serviceRoom) > 0) {
				// 增加日志
				operLogService.addBusinessLog(serviceRoom.getName(), OperLog.operTypeEnum.update,
						OperLog.actionBusinessEnum.serviceRoom, JSON.toJSONString(serviceRoom));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("机房修改成功", JSON.toJSONString(serviceRoom));
				}
				return AllResult.okJSON(serviceRoom);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房修改失败:数据未能持久化");
			}
		} catch (Exception e) {
			LOGGER.error("机房修改失败,后台报错", JSON.toJSONString(serviceRoom));
			operLogService.addBusinessLog(serviceRoom.getName(), OperLog.operTypeEnum.update, OperLog.actionBusinessEnum.serviceRoom,
					null, OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房修改失败:后台报错");
		}
	}

	/**
	 * 删除词典数据
	 *
	 * @param serviceRoom
	 * @param request
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(ServiceRoom serviceRoom, HttpServletRequest request) {
		try {
			if (null == serviceRoom) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request delete serviceRoom param: [serviceRoom: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房入参为空");
			}
			if (StringUtil.isEmpty(serviceRoom.getId())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房ID不能为空");
			}
			if (serviceRoomService.deleteById(serviceRoom.getId()) > 0) {
				// 增加日志
				operLogService.addBusinessLog(serviceRoom.getName(), OperLog.operTypeEnum.delete,
						OperLog.actionBusinessEnum.serviceRoom, JSON.toJSONString(serviceRoom));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("机房删除成功", JSON.toJSONString(serviceRoom));
				}
				return AllResult.okJSON(serviceRoom);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房删除失败:删除条数0");
			}
		} catch (Exception e) {
			LOGGER.error("机房删除失败,后台报错", JSON.toJSONString(serviceRoom));
			operLogService.addBusinessLog(serviceRoom.getName(), OperLog.operTypeEnum.delete, OperLog.actionBusinessEnum.serviceRoom,
					JSON.toJSONString(serviceRoom), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房删除失败:后台报错");
		}
	}

	/**
	 * 根据ID查询机房信息
	 *
	 * @param serviceRoom
	 * @param request
	 */
	@RequestMapping(value = "/selectById", method = { RequestMethod.GET, RequestMethod.POST })
	public Object selectById(ServiceRoom serviceRoom, HttpServletRequest request) {
		try {
			if (null == serviceRoom) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request select serviceRoom param: [serviceRoom: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "入参不能为空");
			}
			if (StringUtil.isEmpty(serviceRoom.getId())) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request select serviceRoom param: [serviceRoom.id: {null}]");
				}
				operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoom, null);
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房ID不能为空");
			}
			return AllResult.okJSON(serviceRoomService.getById(serviceRoom.getId()));
		} catch (Exception e) {
			LOGGER.error("机房查询失败,后台报错", JSON.toJSONString(serviceRoom));
			operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoom, null,
					OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "后台出错");
		}
	}

	/**
	 * 分页查询词典数据
	 * 
	 * @param form
	 * @param request
	 */
	@RequestMapping(value = "/datagrid", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getPageData(ServiceRoomForm form, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", form.getPageNum(), form.getPageSize());
		}
		// 校验参数
		if (form.getPageNum() < 1 || form.getPageSize() < 1) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}
		try {
			ServiceRoomExample example = new ServiceRoomExample();
			ServiceRoomExample.Criteria criteria = example.createCriteria();
			if (StringUtil.isNotEmpty(form.getName())) {
				criteria.andNameLike("%" + form.getName() + "%");
			}
			if (StringUtil.isNotEmpty(form.getPosition())) {
				criteria.andPositionLike("%" + form.getPosition() + "%");
			}
			// 排序设置
			example.setOrderByClause("create_date desc");
			Page<ServiceRoom> queryResult = serviceRoomService.getByPage(form.getPageNum(), form.getPageSize(), example);
			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateCreate", "createName", "createDate"));

			operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoom, null);
			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("get serviceRoom data error. page: {}, count: {}", form.getPageNum(), e);
		}
		operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoom, null,
				OperLog.logLevelEnum.error);
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}
}
