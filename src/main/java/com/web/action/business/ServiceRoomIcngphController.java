package com.web.action.business;

import static com.web.util.AllResult.buildJSON;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web.bean.form.ServiceRoomIcngphForm;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.ServiceRoomIcngph;
import com.web.example.ServiceRoomIcngphExample;
import com.web.service.ServiceRoomIcngphService;
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
public class ServiceRoomIcngphController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRoomIcngphController.class);
	@Autowired
	private ServiceRoomIcngphService serviceRoomIcngphService;

	/**
	 * 新增机房平面图
	 *
	 * @param icngph
	 * @param request
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public Object add(ServiceRoomIcngph icngph, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [ServiceRoomIcngph: {}]", JSON.toJSONString(icngph));
		}
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (null == this.handleUploadFiles(icngph, request)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "上传文件错误，请检查ZIP压缩文件");
			}
			//验证名称不能重复
			ServiceRoomIcngphExample example = new ServiceRoomIcngphExample();
			ServiceRoomIcngphExample.Criteria criteria = example.createCriteria();
			criteria.andFloorNameEqualTo(icngph.getFloorName());
			List<ServiceRoomIcngph> icngphs = serviceRoomIcngphService.getByExample(example);
			if(icngphs.size() > 0){
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "楼层名称已存在，请重新输入");
			}
			icngph.setId(UUIDGenerator.generatorRandomUUID());
			serviceRoomIcngphService.save(icngph);
			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(icngph,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
			// 记录日志
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.insert,
					OperLog.actionBusinessEnum.serviceRoomIcn, jsonStr);
			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save ServiceRoomIcngph fail:", e.getMessage());
			String jsonStr = JSON.toJSONString(icngph,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.insert,
					OperLog.actionBusinessEnum.serviceRoomIcn, jsonStr, OperLog.logLevelEnum.error);
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
	public Object edit(ServiceRoomIcngph icngph, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [ServiceRoomIcngph: {}]", JSON.toJSONString(icngph));
		}
		try {
			if (null == this.handleUploadFiles(icngph, request)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "上传文件错误，请检查ZIP压缩文件");
			}
			icngph.setId(UUIDGenerator.generatorRandomUUID());
			serviceRoomIcngphService.updateById(icngph);
			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(icngph,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
			// 记录日志
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.update,
					OperLog.actionBusinessEnum.serviceRoomIcn, jsonStr);
			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("update ServiceRoomIcngph fail:", e.getMessage());
			String jsonStr = JSON.toJSONString(icngph,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.update,
					OperLog.actionBusinessEnum.serviceRoomIcn, jsonStr, OperLog.logLevelEnum.error);
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
	public Object delete(ServiceRoomIcngph icngph, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [ServiceRoomIcngph: {}]", JSON.toJSONString(icngph));
		}
		if (StringUtil.isEmpty(icngph.getId())) {
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请求异常，入参ID不能为空");
		}
		try {
			icngph = serviceRoomIcngphService.getById(icngph.getId());
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
			if (serviceRoomIcngphService.deleteById(icngph.getId()) > 0) {
				operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.delete,
						OperLog.actionBusinessEnum.serviceRoomIcn, JSONUtil.object2Json(icngph));
			}
			return AllResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("delete serviceRoomIcngph fail:", e.getMessage());
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.delete,
					OperLog.actionBusinessEnum.serviceRoomIcn, JSONUtil.object2Json(icngph), OperLog.logLevelEnum.error);
			return buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误, 机房平面图删除失败");
		}
	}

	/**
	 * 分页获取用户信息
	 */
	@RequestMapping(value = "/datagrid", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getDataGrid(ServiceRoomIcngphForm form, HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [datagrid ServiceRoomIcngph: {}]", JSON.toJSONString(form));
		}
		// 1.验证参数
		String errorTip = ValidationHelper.build()
				// 必输条件验证
				.addGroup(GroupBuilder.build(form.getPageNum()).notNull().minValue(1), "页码必须从1开始")
				.addGroup(GroupBuilder.build(form.getPageSize()).notNull().minValue(1), "每页记录数量最少1条").validate();

		if (!StringUtils.isEmpty(errorTip)) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), errorTip);
		}

		try {

			ServiceRoomIcngphExample example = new ServiceRoomIcngphExample();
			ServiceRoomIcngphExample.Criteria criteria = example.createCriteria();
			ServiceRoomIcngphExample.Criteria criteria2 = example.createCriteria();
			// 条件设置
			if (!StringUtils.isEmpty(form.getFloorName())) {
				criteria.andFloorNameLike("%" + form.getFileName().trim() + "%");
			}
			if (!StringUtils.isEmpty(form.getFileName())) {
				criteria2.andJsonNameLike("%" + form.getFileName() + "%");
				criteria2.andYmlNameLike("%" + form.getFileName() + "%");
				criteria2.andImageNameLike("%" + form.getFileName() + "%");
				example.or(criteria2);
			}

			// 设置排序条件
			StringBuffer orderBy = new StringBuffer("");
			orderBy.append("create_date desc,id asc");
			example.setOrderByClause(orderBy.toString());

			Page<ServiceRoomIcngph> queryResult = serviceRoomIcngphService.getScrollData(form.getPageNum(), form.getPageSize(),
					example);

			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("password", "updateName", "updateDate", "createName", "createDate"),
					SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);

			operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoomIcn, "");
			return AllResult.okJSON(JSON.parse(jsonStr));

		} catch (Exception e) {
			LOGGER.error("get datagrid data error. page: {}, count: {}", form.getPageNum(), form.getPageSize(), e);
			operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoomIcn, "",
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
	public Object downloadFile(ServiceRoomIcngphForm form, HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + form.getFileName());
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [download ServiceRoomIcngph: {}]", JSON.toJSONString(form));
		}
		String path = request.getSession().getServletContext().getRealPath("/");
		if (StringUtil.isEmpty(form.getFileName())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "文件名不能为空");
		}
		try {
			ServiceRoomIcngph icngph = serviceRoomIcngphService.getById(form.getId());
			String ext = form.getFileName().substring(form.getFileName().lastIndexOf(".") + 1, form.getFileName().length());
			String name = "";
			if ("yml".equalsIgnoreCase(ext)) {
				name = icngph.getYmlRealPath();
			}
			if ("json".equalsIgnoreCase(ext)) {
				name = icngph.getJsonRealPath();
			}
			if ("png".equalsIgnoreCase(ext)) {
				name = icngph.getImageRealPath();
			}
			if (StringUtil.isEmpty(name)) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "找不到该文件");
			}
			InputStream inputStream = new FileInputStream(new File(path + name));
			OutputStream outputStream = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int lenth;
			while ((lenth = inputStream.read(bytes)) > 0) {
				outputStream.write(bytes, 0, lenth);
			}
			outputStream.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "文件下载失败！");
		}
		return null;
	}

	@RequestMapping(value = "/getYml", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getYml(ServiceRoomIcngphForm form, HttpServletRequest request) {
		if (StringUtil.isEmpty(form.getId())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "入参ID不能为空");
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [ServiceRoomIcngph getYml: {}]", JSON.toJSONString(form));
		}
		try {
			ServiceRoomIcngph icngph = serviceRoomIcngphService.getById(form.getId());
			String path = request.getSession().getServletContext().getRealPath("/");
			Map result = YmlUtil.getYmlString(path + icngph.getYmlRealPath());
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.select,
					OperLog.actionBusinessEnum.serviceRoomIcn, "");
			return AllResult.okJSON(result);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(form.getFileName(), OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoomIcn,
					"", OperLog.logLevelEnum.error);
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "获取数据失败");
		}
	}

	@RequestMapping(value = "/getJson", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getJson(ServiceRoomIcngphForm form, HttpServletRequest request) {
		if (StringUtil.isEmpty(form.getId())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "入参ID不能为空");
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [ServiceRoomIcngph getYml: {}]", JSON.toJSONString(form));
		}
		try {
			ServiceRoomIcngph icngph = serviceRoomIcngphService.getById(form.getId());
			String path = request.getSession().getServletContext().getRealPath("/");
			String jsonStr = JSONUtil.readJsonFile(path + icngph.getJsonRealPath());
			Object result = JSON.parse(jsonStr);
			if (null == result) {
				return buildJSON(HttpStatus.BAD_REQUEST.value(), "解析JSON失败");
			}
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.select,
					OperLog.actionBusinessEnum.serviceRoomIcn, "");
			return AllResult.okJSON(result);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(form.getFileName(), OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoomIcn,
					"", OperLog.logLevelEnum.error);
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "获取数据失败");
		}
	}

	@RequestMapping(value = "/getImagePath", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getImagePath(ServiceRoomIcngphForm form, HttpServletRequest request) {
		if (StringUtil.isEmpty(form.getId())) {
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "入参ID不能为空");
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [ServiceRoomIcngph getImagePath: {}]", JSON.toJSONString(form));
		}
		try {
			ServiceRoomIcngph icngph = serviceRoomIcngphService.getById(form.getId());
			operLogService.addBusinessLog(icngph.getFloorName(), OperLog.operTypeEnum.select,
					OperLog.actionBusinessEnum.serviceRoomIcn, "");
			return AllResult.okJSON(icngph);
		} catch (Exception e) {
			e.printStackTrace();
			operLogService.addBusinessLog(form.getFileName(), OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoomIcn,
					"", OperLog.logLevelEnum.error);
			return buildJSON(HttpStatus.BAD_REQUEST.value(), "获取数据失败");
		}
	}

	private ServiceRoomIcngph handleUploadFiles(ServiceRoomIcngph icngph, HttpServletRequest request) throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {// 判断是否含有需要上传的文件
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;// 转换成对象
			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
			while (iterator.hasNext()) {
				MultipartFile file = multipartHttpServletRequest.getFile(iterator.next());
				String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
				if (!"zip".equalsIgnoreCase(ext)) {
					return null;
				}
				if (null != file) {
					String path = request.getSession().getServletContext().getRealPath("/upload") + "serviceRoomIc/";// 获取路径
					File inFile = new File(path);
					if (!inFile.exists()) {
						inFile.mkdir();
					}
					inFile = new File(path + file.getName());
					file.transferTo(inFile);
					Map<String, File> files = ZIPUtil.unZipFile(inFile, path);
					Iterator it = files.keySet().iterator();
					while (it.hasNext()) {
						String key = (String) it.next();
						ext = key.substring(key.lastIndexOf(".") + 1, key.length());// 获取后缀名
						File newFile = (File) files.get(key);
						path = "upload/serviceRoomIc";
						if ("yml".equalsIgnoreCase(ext)) {
							icngph.setYmlName(key);
							icngph.setYmlRealPath(path + "/" + newFile.getName());
						}
						if ("json".equalsIgnoreCase(ext)) {
							icngph.setJsonName(key);
							icngph.setJsonRealPath(path + "/" + newFile.getName());
						}
						if ("png".equalsIgnoreCase(ext)) {
							icngph.setImageName(key);
							icngph.setImageRealPath(path + "/" + newFile.getName());
						}
					}
				}
			}
		}
		return icngph;
	}
}
