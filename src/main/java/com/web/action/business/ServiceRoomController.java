package com.web.action.business;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.web.entity.ServiceRoomUserRel;
import com.web.example.ServiceRoomExample;
import com.web.service.ServiceRoomService;
import com.web.service.ServiceRoomUserRelService;
import com.web.util.*;
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
	@Autowired
	private ServiceRoomUserRelService serviceRoomUserRelService;

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
			ServiceRoomExample example = new ServiceRoomExample();
			ServiceRoomExample.Criteria criteria = example.createCriteria();
			criteria.andResourceCodeEqualTo(serviceRoom.getResourceCode());
			List<ServiceRoom> list = serviceRoomService.getByExample(example);
			if (list.size() > 0) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "资源编码已存在，请检查");
			}
			ArrayList<FileUtilBean> files = FileUtil.uploadFiles(request, "serviceRoom", false);// 上传文件
			if (files.size() > 0) {
				if (!"png".equalsIgnoreCase(files.get(0).getFileExt()) && !"jpg".equalsIgnoreCase(files.get(0).getFileExt())
						&& !"jpeg".equalsIgnoreCase(files.get(0).getFileExt())) {
					FileUtil.deleteFiles(files);
					return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请上传正确的图片文件(PNG/JPG/JPEG)");
				}
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

			ServiceRoomExample example = new ServiceRoomExample();
			ServiceRoomExample.Criteria criteria = example.createCriteria();
			criteria.andResourceCodeEqualTo(serviceRoom.getResourceCode());
			criteria.andIdNotEqualTo(serviceRoom.getId());
			List<ServiceRoom> list = serviceRoomService.getByExample(example);
			if (list.size() > 0) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "资源编码已存在，请检查");
			}

			ArrayList<FileUtilBean> files = FileUtil.uploadFiles(request, "serviceRoom", false);// 上传文件
			if (files.size() > 0) {
				if (!"jpg".equalsIgnoreCase(files.get(0).getFileExt()) && !"png".equalsIgnoreCase(files.get(0).getFileExt())
						&& !"jpeg".equalsIgnoreCase(files.get(0).getFileExt())) {
					FileUtil.deleteFiles(files);
					return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请上传正确的图片文件(PNG/JPG/JPEG)");
				}
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
	 * 删除机房信息
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
	 * 分页查询机房信息
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
			StringBuffer orderBy = new StringBuffer();
			if (StringUtil.isNotEmpty(form.getSortName())) {
				if ("id".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("id " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("name".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("name " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("position".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("position " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("exterior".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("id " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("resourceCode".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("resource_code " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("address".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("address " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("area".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("area " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("comment".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("comment " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				}
			}
			orderBy.append("create_date desc");
			// 排序设置
			example.setOrderByClause(orderBy.toString());
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

	/**
	 * 新增机房和用户对应关系
	 * 
	 * @param serviceRoomId
	 *            机房ID
	 * @param userIds
	 *            用户ID
	 */
	@RequestMapping(value = "/addServiceRoomUserRel", method = { RequestMethod.GET, RequestMethod.POST })
	public Object addServiceRoomUserRel(String serviceRoomId, String userIds) {
		if (StringUtil.isEmpty(serviceRoomId)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房ID不能为空");
		}
		if (StringUtil.isEmpty(userIds)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID不能为空");
		}
		ServiceRoom serviceRoom = serviceRoomService.getById(serviceRoomId);
		String serviceRoomName = "";
		if (null != serviceRoom) {
			serviceRoomName = serviceRoom.getName();
		}
		try {
			String[] userIdArr = userIds.split(",");
			if (userIdArr.length < 1) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID不能为空");
			}
			int result = serviceRoomUserRelService.batchSave(serviceRoomId, userIdArr);
			operLogService.addBusinessLog(serviceRoomName, OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.serviceRoom,
					"{\"userIds\":\"" + userIds + "\"}");
			return AllResult.okJSON(result);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(serviceRoomName, OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.serviceRoom,
					"{\"userIds\":\"" + userIds + "\"}", OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
		}
	}

	/**
	 * 根据机房ID查询所有对应关系
	 * 
	 * @param serviceRoomUserRel
	 *            机房ID
	 */
	@RequestMapping(value = "/getServiceRoomUserRels", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getServiceRoomUserRels(ServiceRoomUserRel serviceRoomUserRel) {
		if (StringUtil.isEmpty(serviceRoomUserRel.getServiceRoomId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房ID不能为空");
		}
		ServiceRoom serviceRoom = serviceRoomService.getById(serviceRoomUserRel.getServiceRoomId());
		String serviceRoomName = "";
		if (null != serviceRoom) {
			serviceRoomName = serviceRoom.getName();
		}
		try {
			List<ServiceRoomUserRel> list = serviceRoomUserRelService.selectByServiceRoomId(serviceRoomUserRel);
			operLogService.addBusinessLog(serviceRoomName, OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoom, null);
			return AllResult.okJSON(list);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(serviceRoomName, OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoom, null,
					OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
		}
	}

	/**
	 * 获取前台显示图片
	 */
	@RequestMapping(value = "/getImage", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getImage(ServiceRoom serviceRoom, HttpServletResponse response) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [ServiceRoom getImage: {}]", JSON.toJSONString(serviceRoom));
		}
		if (StringUtil.isEmpty(serviceRoom.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房ID不能为空");
		}
		try {
			serviceRoom = serviceRoomService.getById(serviceRoom.getId());
			boolean isExist = FileUtil.checkFileExist(serviceRoom.getImageUrl());
			if (!isExist) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "文件不存在");
			}
			ImageUtil.getImage(serviceRoom.getImageUrl(), FileUtil.getFilename(serviceRoom.getImageUrl()), response);
		} catch (Exception e) {
			e.printStackTrace();
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
		}
		return null;
	}

	/**
	 * 修改机房和用户对应关系
	 * 
	 * @param serviceRoomId
	 *            机房ID
	 * @param userIds
	 *            用户ID
	 */
	@RequestMapping(value = "/updateServiceRoomUserRel", method = { RequestMethod.GET, RequestMethod.POST })
	public Object updateServiceRoomUserRel(String serviceRoomId, String userIds) {
		if (StringUtil.isEmpty(serviceRoomId)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房ID不能为空");
		}
		if (StringUtil.isEmpty(userIds)) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID不能为空");
		}
		ServiceRoom serviceRoom = serviceRoomService.getById(serviceRoomId);
		String serviceRoomName = "";
		if (null != serviceRoom) {
			serviceRoomName = serviceRoom.getName();
		}
		try {
			String[] userIdArr = userIds.split(",");
			serviceRoomUserRelService.deleteByServiceRoomId(serviceRoomId);// 删除旧的关联关系
			int result = serviceRoomUserRelService.batchSave(serviceRoomId, userIdArr);
			operLogService.addBusinessLog(serviceRoomName, OperLog.operTypeEnum.update, OperLog.actionBusinessEnum.serviceRoom,
					"{\"userIds\":\"" + userIds + "\"}");
			return AllResult.okJSON(result);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(serviceRoomName, OperLog.operTypeEnum.update, OperLog.actionBusinessEnum.serviceRoom,
					"{\"userIds\":\"" + userIds + "\"}", OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
		}
	}
}
