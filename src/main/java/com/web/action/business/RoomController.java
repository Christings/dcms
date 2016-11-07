package com.web.action.business;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.entity.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.web.bean.form.RoomForm;
import com.web.bean.util.FileUtilBean;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.RoomUserRel;
import com.web.example.RoomExample;
import com.web.service.RoomService;
import com.web.service.RoomUserRelService;
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
public class RoomController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);

	@Autowired
	private RoomService roomService;
	@Autowired
	private RoomUserRelService roomUserRelService;

	/**
	 * 新增机房
	 *
	 * @param room
	 * @param request
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object save(Room room, MultipartHttpServletRequest request) {
		try {
			if (null == room) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request add room param: [room: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "新增机房入参为空");
			}
			RoomExample example = new RoomExample();
			RoomExample.Criteria criteria = example.createCriteria();
			criteria.andResourceCodeEqualTo(room.getResourceCode());
			List<Room> list = roomService.getByExample(example);
			if (list.size() > 0) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "资源编码已存在，请检查");
			}
			ArrayList<FileUtilBean> files = FileUtil.uploadFiles(request, "room", false);// 上传文件
			if (files.size() > 0) {
				if (!"png".equalsIgnoreCase(files.get(0).getFileExt()) && !"jpg".equalsIgnoreCase(files.get(0).getFileExt())
						&& !"jpeg".equalsIgnoreCase(files.get(0).getFileExt())) {
					FileUtil.deleteFiles(files);
					return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请上传正确的图片文件(PNG/JPG/JPEG)");
				}
				room.setImageUrl(files.get(0).getFileRealPath());
			}
			room.setId(UUIDGenerator.generatorRandomUUID());
			if (roomService.save(room) > 0) {
				// 增加日志
				operLogService.addBusinessLog(room.getName(), OperLog.operTypeEnum.insert,
						OperLog.actionBusinessEnum.serviceRoom, JSON.toJSONString(room));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("机房新增成功", JSON.toJSONString(room));
				}
				return AllResult.okJSON(room);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房新增失败:数据未能持久化");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("机房新增失败,后台报错", JSON.toJSONString(room));
			operLogService.addBusinessLog(room.getName(), OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.serviceRoom,
					JSON.toJSONString(room), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房新增失败:后台报错");
		}
	}

	/**
	 * 编辑机房信息
	 *
	 * @param room
	 * @param request
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(Room room, MultipartHttpServletRequest request) {
		try {
			if (null == room) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request edit room param: [room: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "修改机房入参为空");
			}
			if (StringUtil.isEmpty(room.getId())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "入参ID不能为空");
			}

			RoomExample example = new RoomExample();
			RoomExample.Criteria criteria = example.createCriteria();
			criteria.andResourceCodeEqualTo(room.getResourceCode());
			criteria.andIdNotEqualTo(room.getId());
			List<Room> list = roomService.getByExample(example);
			if (list.size() > 0) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "资源编码已存在，请检查");
			}

			ArrayList<FileUtilBean> files = FileUtil.uploadFiles(request, "room", false);// 上传文件
			if (files.size() > 0) {
				if (!"jpg".equalsIgnoreCase(files.get(0).getFileExt()) && !"png".equalsIgnoreCase(files.get(0).getFileExt())
						&& !"jpeg".equalsIgnoreCase(files.get(0).getFileExt())) {
					FileUtil.deleteFiles(files);
					return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "请上传正确的图片文件(PNG/JPG/JPEG)");
				}
				room.setImageUrl(files.get(0).getFileRealPath());
			}
			if (roomService.updateById(room) > 0) {
				// 增加日志
				operLogService.addBusinessLog(room.getName(), OperLog.operTypeEnum.update,
						OperLog.actionBusinessEnum.serviceRoom, JSON.toJSONString(room));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("机房修改成功", JSON.toJSONString(room));
				}
				return AllResult.okJSON(room);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房修改失败:数据未能持久化");
			}
		} catch (Exception e) {
			LOGGER.error("机房修改失败,后台报错", JSON.toJSONString(room));
			operLogService.addBusinessLog(room.getName(), OperLog.operTypeEnum.update, OperLog.actionBusinessEnum.serviceRoom,
					null, OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房修改失败:后台报错");
		}
	}

	/**
	 * 删除机房信息
	 *
	 * @param room
	 * @param request
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(Room room, HttpServletRequest request) {
		try {
			if (null == room) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request delete room param: [room: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房入参为空");
			}
			if (StringUtil.isEmpty(room.getId())) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房ID不能为空");
			}
			if (roomService.deleteById(room.getId()) > 0) {
				// 增加日志
				operLogService.addBusinessLog(room.getName(), OperLog.operTypeEnum.delete,
						OperLog.actionBusinessEnum.serviceRoom, JSON.toJSONString(room));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("机房删除成功", JSON.toJSONString(room));
				}
				return AllResult.okJSON(room);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房删除失败:删除条数0");
			}
		} catch (Exception e) {
			LOGGER.error("机房删除失败,后台报错", JSON.toJSONString(room));
			operLogService.addBusinessLog(room.getName(), OperLog.operTypeEnum.delete, OperLog.actionBusinessEnum.serviceRoom,
					JSON.toJSONString(room), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房删除失败:后台报错");
		}
	}

	/**
	 * 根据ID查询机房信息
	 *
	 * @param room
	 * @param request
	 */
	@RequestMapping(value = "/selectById", method = { RequestMethod.GET, RequestMethod.POST })
	public Object selectById(Room room, HttpServletRequest request) {
		try {
			if (null == room) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request select room param: [room: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "入参不能为空");
			}
			if (StringUtil.isEmpty(room.getId())) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request select room param: [room.id: {null}]");
				}
				operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoom, null);
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房ID不能为空");
			}
			return AllResult.okJSON(roomService.getById(room.getId()));
		} catch (Exception e) {
			LOGGER.error("机房查询失败,后台报错", JSON.toJSONString(room));
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
	public Object getPageData(RoomForm form, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", form.getPageNum(), form.getPageSize());
		}
		// 校验参数
		if (form.getPageNum() < 1 || form.getPageSize() < 1) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}
		try {
			RoomExample example = new RoomExample();
			RoomExample.Criteria criteria = example.createCriteria();
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
			Page<Room> queryResult = roomService.getByPage(form.getPageNum(), form.getPageSize(), example);
			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateCreate", "createName", "createDate"));

			operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoom, null);
			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("get room data error. page: {}, count: {}", form.getPageNum(), e);
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
		Room room = roomService.getById(serviceRoomId);
		String serviceRoomName = "";
		if (null != room) {
			serviceRoomName = room.getName();
		}
		try {
			String[] userIdArr = userIds.split(",");
			if (userIdArr.length < 1) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "用户ID不能为空");
			}
			int result = roomUserRelService.batchSave(serviceRoomId, userIdArr);
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
	 * @param roomUserRel
	 *            机房ID
	 */
	@RequestMapping(value = "/getServiceRoomUserRels", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getServiceRoomUserRels(RoomUserRel roomUserRel) {
		if (StringUtil.isEmpty(roomUserRel.getServiceRoomId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房ID不能为空");
		}
		Room room = roomService.getById(roomUserRel.getServiceRoomId());
		String serviceRoomName = "";
		if (null != room) {
			serviceRoomName = room.getName();
		}
		try {
			List<RoomUserRel> list = roomUserRelService.selectByServiceRoomId(roomUserRel);
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
	public Object getImage(Room room, HttpServletResponse response) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [Room getImage: {}]", JSON.toJSONString(room));
		}
		if (StringUtil.isEmpty(room.getId())) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "机房ID不能为空");
		}
		try {
			room = roomService.getById(room.getId());
			boolean isExist = FileUtil.checkFileExist(room.getImageUrl());
			if (!isExist) {
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "文件不存在");
			}
			ImageUtil.getImage(room.getImageUrl(), FileUtil.getFilename(room.getImageUrl()), response);
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
		Room room = roomService.getById(serviceRoomId);
		String serviceRoomName = "";
		if (null != room) {
			serviceRoomName = room.getName();
		}
		try {
			String[] userIdArr = userIds.split(",");
			roomUserRelService.deleteByServiceRoomId(serviceRoomId);// 删除旧的关联关系
			int result = roomUserRelService.batchSave(serviceRoomId, userIdArr);
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
