package com.web.action.business;

import static com.web.util.AllResult.buildJSON;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.entity.RoomIcngph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web.bean.form.RoomIcngphForm;
import com.web.bean.util.FileUtilBean;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.Room;
import com.web.example.RoomIcngphExample;
import com.web.service.RoomIcngphService;
import com.web.util.*;
import com.web.util.fastjson.FastjsonUtils;
import com.web.util.validation.GroupBuilder;
import com.web.util.validation.ValidationHelper;

/**
 * 机房平面图管理控制器
 *
 * @author 田军兴
 * @date 2016-10-22
 */
@RestController
@RequestMapping("/serviceRoomIcngph")
public class RoomIcngphController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomIcngphController.class);
	@Autowired
	private RoomIcngphService roomIcngphService;

	/**
	 * 新增机房平面图
	 *
	 * @param icngph
	 * @param request
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public Object add(RoomIcngph icngph, MultipartHttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [RoomIcngph: {}]", JSON.toJSONString(icngph));
		}
		try {
			// 验证名称不能重复
			if (StringUtil.isNotEmpty(icngph.getFloorName())) {
				RoomIcngphExample example = new RoomIcngphExample();
				RoomIcngphExample.Criteria criteria = example.createCriteria();
				criteria.andFloorNameEqualTo(icngph.getFloorName());
				List<RoomIcngph> icngphs = roomIcngphService.getByExample(example);
				if (icngphs.size() > 0) {
					return buildJSON(HttpStatus.BAD_REQUEST.value(), "楼层名称已存在，请重新输入");
				}
			} else {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "楼层名称不能为空");
			}

			ArrayList<FileUtilBean> beans = FileUtil.uploadFiles(request, "roomIcngph", true);
			if (beans.size() != 3) {
				FileUtil.deleteFiles(beans);
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "上传文件错误，请检查ZIP压缩文件是否只含有YML、JSON和PNG三个文件");
			}
			for (FileUtilBean bean : beans) {
				if ("yml".equalsIgnoreCase(bean.getFileExt())) {
					icngph.setYmlName(bean.getFileName());
					icngph.setYmlRealPath(bean.getFileRealPath());
				} else if ("json".equalsIgnoreCase(bean.getFileExt())) {
					icngph.setJsonName(bean.getFileName());
					icngph.setJsonRealPath(bean.getFileRealPath());
				} else if ("png".equalsIgnoreCase(bean.getFileExt())) {
					icngph.setImageName(bean.getFileName());
					icngph.setImageRealPath(bean.getFileRealPath());
				}
			}
			String checkResult = this.checkFile(icngph);
			RoomIcngphExample example = new RoomIcngphExample();
			example.or().andJsonNameLike("%" + icngph.getJsonName() + "%");
			example.or().andYmlNameLike("%" + icngph.getYmlName() + "%");
			example.or().andImageNameLike("%" + icngph.getImageName() + "%");
			List<RoomIcngph> list = roomIcngphService.getByExample(example);
			if (StringUtil.isNotEmpty(checkResult)) {
				FileUtil.deleteFiles(beans);
				return buildJSON(HttpStatus.BAD_REQUEST.value(), checkResult);
			}
			if (list.size() > 0) {
				FileUtil.deleteFiles(beans);
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "文件名称和已上传文件冲突，请检查");
			}
			icngph.setId(UUIDGenerator.generatorRandomUUID());
			roomIcngphService.save(icngph);
			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(icngph,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
			// 记录日志
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.insert,
					OperLog.actionBusinessEnum.roomIcn, jsonStr);
			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save RoomIcngph fail:", e.getMessage());
			String jsonStr = JSON.toJSONString(icngph,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.insert,
					OperLog.actionBusinessEnum.roomIcn, jsonStr, OperLog.logLevelEnum.error);
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,添加机房平面图失败");
		}
	}

	/**
	 * 修改机房平面图
	 *
	 * @param icngph
	 * @param request
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public Object edit(RoomIcngph icngph, MultipartHttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [RoomIcngph: {}]", JSON.toJSONString(icngph));
		}
		try {
			if (StringUtil.isEmpty(icngph.getId())) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "更新失败，入参ID不能为空");
			}
			List<RoomIcngph> list = new ArrayList<RoomIcngph>();
			if (StringUtil.isNotEmpty(icngph.getFloorName())) {
				RoomIcngphExample example = new RoomIcngphExample();
				RoomIcngphExample.Criteria criteria = example.createCriteria();
				criteria.andIdNotEqualTo(icngph.getId());
				criteria.andFloorNameEqualTo(icngph.getFloorName());
				list = roomIcngphService.getByExample(example);
			}
			if (list.size() > 0) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "更新失败，楼层名称已经被占用，请修改");
			}
			ArrayList<FileUtilBean> files = FileUtil.uploadFiles(request, "roomIcngph", true);
			if (files.size() != 3) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "上传文件错误，请检查ZIP压缩文件是否只含有YML、JSON和PNG三个文件");
			}
			for (FileUtilBean file : files) {
				if ("yml".equalsIgnoreCase(file.getFileExt())) {
					icngph.setYmlName(file.getFileName());
					icngph.setYmlRealPath(file.getFileRealPath());
				} else if ("png".equalsIgnoreCase(file.getFileExt())) {
					icngph.setImageName(file.getFileName());
					icngph.setImageRealPath(file.getFileRealPath());
				} else if ("json".equalsIgnoreCase(file.getFileExt())) {
					icngph.setJsonName(file.getFileName());
					icngph.setJsonRealPath(file.getFileRealPath());
				}
			}
			String checkResult = this.checkFile(icngph);
			if (StringUtil.isNotEmpty(checkResult)) {
				FileUtil.deleteFiles(files);
				return buildJSON(HttpStatus.BAD_REQUEST.value(), checkResult);
			}
			RoomIcngphExample example = new RoomIcngphExample();
			example.or().andJsonNameLike("%" + icngph.getJsonName() + "%");
			example.or().andYmlNameLike("%" + icngph.getYmlName() + "%");
			example.or().andImageNameLike("%" + icngph.getImageName() + "%");
			list = roomIcngphService.getByExample(example);
			if (list.size() > 0) {
				FileUtil.deleteFiles(files);
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "文件名称和已上传文件冲突，请检查");
			}
			int result = roomIcngphService.updateById(icngph);
			if (result < 1) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "更新失败");
			}
			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(icngph,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
			// 记录日志
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.update,
					OperLog.actionBusinessEnum.roomIcn, jsonStr);
			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("update RoomIcngph fail:", e.getMessage());
			String jsonStr = JSON.toJSONString(icngph,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.update,
					OperLog.actionBusinessEnum.roomIcn, jsonStr, OperLog.logLevelEnum.error);
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误,修改机房平面图失败");
		}
	}

	/**
	 * 删除机房平面图
	 *
	 * @param icngph
	 * @param request
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public Object delete(RoomIcngph icngph, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [RoomIcngph: {}]", JSON.toJSONString(icngph));
		}
		if (StringUtil.isEmpty(icngph.getId())) {
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请求异常，入参ID不能为空");
		}
		try {
			icngph = roomIcngphService.getById(icngph.getId());
			// 删除文件
			String path = request.getSession().getServletContext().getRealPath("/");
			if (StringUtil.isNotEmpty(icngph.getImageRealPath())) {
				File file = new File(path + icngph.getImageRealPath());
				file.delete();
			}
			if (StringUtil.isNotEmpty(icngph.getJsonRealPath())) {
				File file = new File(path + icngph.getJsonRealPath());
				file.delete();
			}
			if (StringUtil.isNotEmpty(icngph.getYmlRealPath())) {
				File file = new File(path + icngph.getYmlRealPath());
				file.delete();
			}
			if (roomIcngphService.deleteById(icngph.getId()) > 0) {
				operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.delete,
						OperLog.actionBusinessEnum.roomIcn, JSONUtil.object2Json(icngph));
			}
			return AllResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("delete roomIcngph fail:", e.getMessage());
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.delete,
					OperLog.actionBusinessEnum.roomIcn, JSONUtil.object2Json(icngph), OperLog.logLevelEnum.error);
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误, 机房平面图删除失败");
		}
	}

	/**
	 * 分页获取用户信息
	 */
	@RequestMapping(value = "/datagrid", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getDataGrid(RoomIcngphForm form, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [datagrid RoomIcngph: {}]", JSON.toJSONString(form));
		}
		String path = request.getSession().getServletContext().getRealPath("/");
		// 1.验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(form.getPageNum()).notNull().minValue(1), "页码必须从1开始")
				.addGroup(GroupBuilder.build(form.getPageSize()).notNull().minValue(1), "每页记录数量最少1条").validate();

		if (!StringUtils.isEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {

			RoomIcngphExample example = new RoomIcngphExample();
			RoomIcngphExample.Criteria criteria = example.createCriteria();
			// 条件设置
			if (!StringUtils.isEmpty(form.getFloorName())) {
				criteria.andFloorNameLike("%" + form.getFloorName().trim() + "%");
			}
			if (!StringUtils.isEmpty(form.getFileName())) {
				example.or().andJsonNameLike("%" + form.getFileName() + "%");
				example.or().andYmlNameLike("%" + form.getFileName() + "%");
				example.or().andImageNameLike("%" + form.getFileName() + "%");
			}

			// 设置排序条件
			StringBuffer orderBy = new StringBuffer();
			if (StringUtil.isNotEmpty(form.getSortName())) {
				if ("id".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("id " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("floorName".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("floor_name " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("imageName".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("image_name " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("jsonName".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("json_name " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				} else if ("ymlName".equalsIgnoreCase(form.getSortName())) {
					orderBy.append("yml_name " + ("asc".equalsIgnoreCase(form.getSortDesc()) ? "asc" : "desc") + ",");
				}
			}
			orderBy.append("create_date desc");
			example.setOrderByClause(orderBy.toString());

			Page<RoomIcngph> queryResult = roomIcngphService.getScrollData(form.getPageNum(), form.getPageSize(),
					example);

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("password", "updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.roomIcn, "");
			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get datagrid data error. page: {}, count: {}", form.getPageNum(), form.getPageSize(), e);
			operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.roomIcn, "",
					OperLog.logLevelEnum.error);
		}

		return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}

	/**
	 * 下载附件
	 * 
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/downloadFile", method = { RequestMethod.POST, RequestMethod.GET })
	public Object downloadFile(RoomIcngphForm form, HttpServletRequest request, HttpServletResponse response) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [download RoomIcngph: {}]", JSON.toJSONString(form));
		}

		if (StringUtil.isEmpty(form.getFileName())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "文件名不能为空");
		}
		try {
			RoomIcngph icngph = roomIcngphService.getById(form.getId());
			String ext = form.getFileName().substring(form.getFileName().lastIndexOf(".") + 1, form.getFileName().length());
			String name = "";
			String path = "";
			if ("yml".equalsIgnoreCase(ext)) {
				path = icngph.getYmlRealPath();
				name = icngph.getYmlName();
			}
			if ("json".equalsIgnoreCase(ext)) {
				path = icngph.getJsonRealPath();
				name = icngph.getJsonName();
			}
			if ("png".equalsIgnoreCase(ext)) {
				path = icngph.getImageRealPath();
				name = icngph.getImageName();
			}
			if (StringUtil.isEmpty(name)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "找不到该文件");
			}
			boolean result = FileUtil.downloadFile(response, path, name);
			if (result) {
				return AllResult.ok();
			} else {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "文件下载失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "文件下载失败！");
		}
	}

	@RequestMapping(value = "/getYml", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getYml(RoomIcngphForm form, HttpServletRequest request) {
		if (StringUtil.isEmpty(form.getId())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "入参ID不能为空");
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [RoomIcngph getYml: {}]", JSON.toJSONString(form));
		}
		try {
			RoomIcngph icngph = roomIcngphService.getById(form.getId());
			Map result = YmlUtil.getYmlString(icngph.getYmlRealPath());
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.select,
					OperLog.actionBusinessEnum.roomIcn, "");
			return AllResult.okJSON(result);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(form.getFileName(), OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.roomIcn,
					"", OperLog.logLevelEnum.error);
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取数据失败");
		}
	}

	@RequestMapping(value = "/getJson", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getJson(RoomIcngphForm form, HttpServletRequest request) {
		if (StringUtil.isEmpty(form.getId())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "入参ID不能为空");
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [RoomIcngph getYml: {}]", JSON.toJSONString(form));
		}
		try {
			RoomIcngph icngph = roomIcngphService.getById(form.getId());
			String jsonStr = JSONUtil.readJsonFile(icngph.getJsonRealPath());
			Object result = JSON.parse(jsonStr);
			if (null == result) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "解析JSON失败");
			}
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.select,
					OperLog.actionBusinessEnum.roomIcn, "");
			return AllResult.okJSON(result);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(form.getFileName(), OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.roomIcn,
					"", OperLog.logLevelEnum.error);
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取数据失败");
		}
	}

	@RequestMapping(value = "/getImage", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getImage(RoomIcngphForm form, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtil.isEmpty(form.getId())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "入参ID不能为空");
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [RoomIcngph getImage: {}]", JSON.toJSONString(form));
		}
		try {
			RoomIcngph icngph = roomIcngphService.getById(form.getId());
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.select,
					OperLog.actionBusinessEnum.roomIcn, "");
			ImageUtil.getImage(icngph.getImageRealPath(), icngph.getImageName(), response);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(form.getFileName(), OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.roomIcn,
					"", OperLog.logLevelEnum.error);
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取数据失败");
		}
	}

	/**
	 * 检查下载文件是否存在
	 */
	@RequestMapping(value = "/checkFileIsExist", method = { RequestMethod.POST, RequestMethod.GET })
	public Object checkFileIsExist(RoomIcngphForm form) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [download RoomIcngph: {}]", JSON.toJSONString(form));
		}

		if (StringUtil.isEmpty(form.getFileName())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "文件名不能为空");
		}

		RoomIcngph icngph = roomIcngphService.getById(form.getId());
		String ext = form.getFileName().substring(form.getFileName().lastIndexOf(".") + 1, form.getFileName().length());
		String name = "";
		String path = "";
		if ("yml".equalsIgnoreCase(ext)) {
			path = icngph.getYmlRealPath();
			name = icngph.getYmlName();
		}
		if ("json".equalsIgnoreCase(ext)) {
			path = icngph.getJsonRealPath();
			name = icngph.getJsonName();
		}
		if ("png".equalsIgnoreCase(ext)) {
			path = icngph.getImageRealPath();
			name = icngph.getImageName();
		}
		if (StringUtil.isEmpty(name)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "文件不存在");
		}

		if (!name.equals(form.getFileName())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "文件不存在");
		}

		boolean isExist = FileUtil.checkFileExist(path);
		if (!isExist) {
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "文件不存在");
		}
		return AllResult.okJSON("success");
	}

	/**
	 * 根据机房资源编码定位机房
	 * 
	 * @param room
	 *            机房资源编码
	 */
	@RequestMapping(value = "/locationServiceRoomByName", method = { RequestMethod.POST, RequestMethod.GET })
	public Object locationServiceRoomByName(Room room) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [download RoomIcngph: {}]", JSONUtil.object2Json(room));
		}
		try {
			if (StringUtil.isEmpty(room.getResourceCode())) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "机房资源编码不能为空");
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
			if (StringUtil.isEmpty(roomIcngph.getId())) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "找不到此机房的相关信息");
			} else {
				operLogService.addBusinessLog(roomIcngph.getFloorName(), OperLog.operTypeEnum.select,
						OperLog.actionBusinessEnum.roomIcn, "");
				return AllResult.okJSON(roomIcngph.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(room.getResourceCode(), OperLog.operTypeEnum.select,
					OperLog.actionBusinessEnum.roomIcn, "", OperLog.logLevelEnum.error);
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取数据失败");
		}
	}

	/**
	 * 校验上传文件是否正确
	 */
	private String checkFile(RoomIcngph icngph) {
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isEmpty(icngph.getJsonRealPath())) {
			sb.append("缺少JSON文件、");
		}
		if (StringUtil.isEmpty(icngph.getYmlRealPath())) {
			sb.append("缺少YML文件、");
		}
		if (StringUtil.isEmpty(icngph.getImageRealPath())) {
			sb.append("缺少PNG文件、");
		}
		if (StringUtil.isNotEmpty(sb.toString())) {
			return sb.toString().substring(0, sb.toString().length() - 1);
		}
		String fileName = icngph.getImageName().substring(0, icngph.getImageName().lastIndexOf("."));
		if (!fileName.equals(icngph.getJsonName().substring(0, icngph.getJsonName().lastIndexOf(".")))
				|| !fileName.equals(icngph.getYmlName().substring(0, icngph.getYmlName().lastIndexOf(".")))) {
			sb.append("请检查zip内的文件名是否一致、");
			if (StringUtil.isNotEmpty(sb.toString())) {
				return sb.toString().substring(0, sb.toString().length() - 1);
			}
		}
		return null;
	}
}
