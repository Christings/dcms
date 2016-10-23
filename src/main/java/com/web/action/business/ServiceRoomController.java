package com.web.action.business;

import com.alibaba.fastjson.JSON;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.ServiceRoom;
import com.web.example.ServiceRoomExample;
import com.web.service.ServiceRoomService;
import com.web.util.AllResult;
import com.web.util.DateUtil;
import com.web.util.StringUtil;
import com.web.util.fastjson.FastjsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.Iterator;

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
	public Object save(ServiceRoom serviceRoom, HttpServletRequest request) {
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {// 判断是否含有需要上传的文件
				MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;// 转换成对象
				Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
				while (iterator.hasNext()) {
					MultipartFile file = multipartHttpServletRequest.getFile(iterator.next());
					if (null != file) {
						String path = request.getSession().getServletContext().getRealPath("/upload");// 获取路径
						String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());// 获取后缀名
						String fileName = String.valueOf(DateUtil.getMillis(new Date())) + "." + ext;// 新的文件名
						File lFile = new File(path + "/" + fileName);
						file.transferTo(lFile);// 转存到本地
						serviceRoom.setImageUrl(fileName);
					}
				}
			}
			if (null == serviceRoom) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request add serviceRoom param: [serviceRoom: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "新增机房入参为空");
			}
			if (serviceRoomService.save(serviceRoom) > 0) {
				// 增加日志
				operLogService.addBusinessLog("", OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.serviceRoom,
						JSON.toJSONString(serviceRoom));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("机房新增成功", JSON.toJSONString(serviceRoom));
				}
				return AllResult.okJSON(serviceRoom);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房新增失败:数据未能持久化");
			}
		} catch (Exception e) {
			LOGGER.error("机房新增失败,后台报错", JSON.toJSONString(serviceRoom));
			operLogService.addBusinessLog("", OperLog.operTypeEnum.insert, OperLog.actionBusinessEnum.serviceRoom,
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
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public Object update(ServiceRoom serviceRoom, HttpServletRequest request) {
		try {
			if (null == serviceRoom) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("request edit serviceRoom param: [serviceRoom: {null}]");
				}
				return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "新增机房入参为空");
			}
			if (serviceRoomService.updateById(serviceRoom) > 0) {
				// 增加日志
				operLogService.addBusinessLog("", OperLog.operTypeEnum.update, OperLog.actionBusinessEnum.serviceRoom,
						JSON.toJSONString(serviceRoom));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("机房修改成功", JSON.toJSONString(serviceRoom));
				}
				return AllResult.okJSON(serviceRoom);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房修改失败:数据未能持久化");
			}
		} catch (Exception e) {
			LOGGER.error("机房修改失败,后台报错", JSON.toJSONString(serviceRoom));
			operLogService.addBusinessLog("", OperLog.operTypeEnum.update, OperLog.actionBusinessEnum.serviceRoom, null,
					OperLog.logLevelEnum.error);
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
				operLogService.addBusinessLog("", OperLog.operTypeEnum.delete, OperLog.actionBusinessEnum.serviceRoom,
						JSON.toJSONString(serviceRoom));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("机房删除成功", JSON.toJSONString(serviceRoom));
				}
				return AllResult.okJSON(serviceRoom);
			} else {
				return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房删除失败:删除条数0");
			}
		} catch (Exception e) {
			LOGGER.error("机房删除失败,后台报错", JSON.toJSONString(serviceRoom));
			operLogService.addBusinessLog("", OperLog.operTypeEnum.delete, OperLog.actionBusinessEnum.serviceRoom,
					JSON.toJSONString(serviceRoom), OperLog.logLevelEnum.error);
			return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "机房删除失败:后台报错");
		}
	}

	/**
	 * 根据ID查询数据词典
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
	 * @param page
	 *            当前页
	 * @param count
	 *            每页条数
	 * @param request
	 */
	@RequestMapping(value = "/getPage", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getPageData(@RequestParam(value = "page") int page, @RequestParam(value = "count") int count,
			HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", page, count);
		}
		// 校验参数
		if (page < 1 || count < 1) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}
		try {
			ServiceRoomExample example = new ServiceRoomExample();
			// 排序设置
			example.setOrderByClause("SORT asc");
			ServiceRoomExample.Criteria criteria = example.createCriteria();
			Page<ServiceRoom> queryResult = serviceRoomService.getByPage(page, count, example);
			// 去除不需要的字段
			String jsonStr = JSON.toJSONString(queryResult,
					FastjsonUtils.newIgnorePropertyFilter("updateName", "updateCreate", "createName", "createDate"));

			operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoom, null);
			return AllResult.okJSON(JSON.parse(jsonStr));
		} catch (Exception e) {
			LOGGER.error("get serviceRoom data error. page: {}, count: {}", page, e);
		}
		operLogService.addBusinessLog("", OperLog.operTypeEnum.select, OperLog.actionBusinessEnum.serviceRoom, null,
				OperLog.logLevelEnum.error);
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}
}
