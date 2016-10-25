package com.web.action.xtgl;

import com.web.bean.form.OperLogForm;
import com.web.core.action.BaseController;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.example.OperLogExample;
import com.web.util.AllResult;
import com.web.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 业务日志控制器
 *
 * @author 田军兴
 * @date 2016-06-29
 */
@RestController
@RequestMapping("/operLog")
public class OperLogController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperLogController.class);

	/**
	 * 分页查询日志相关信息
	 */
	@RequestMapping(value = "/datagrid", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getScroll(HttpServletRequest request, OperLogForm operLogForm) {
		try {
			operLogForm.setPageNum(1);
			operLogForm.setPageSize(10);
			OperLogExample operLogExample = new OperLogExample();
			OperLogExample.Criteria criteria = operLogExample.createCriteria();
			if (StringUtil.isNotEmpty(operLogForm.getDeviceName())) {
				criteria.andDeviceNameLike("%" + operLogForm.getDeviceName() + "%");
			}
			if (null != operLogForm.getBeginOperDate() && null != operLogForm.getEndOperDate()) {
				criteria.andOperDateBetween(operLogForm.getBeginOperDate(), operLogForm.getEndOperDate());
			} else {
				if (null != operLogForm.getBeginOperDate()) {
					criteria.andOperDateGreaterThanOrEqualTo(operLogForm.getBeginOperDate());
				}
				if (null != operLogForm.getEndOperDate()) {
					criteria.andOperDateLessThanOrEqualTo(operLogForm.getEndOperDate());
				}
			}
			if (StringUtil.isNotEmpty(operLogForm.getOperType())) {
				criteria.andOperTypeEqualTo(operLogForm.getOperType());
			}
			if (null != operLogForm.getLogLevel()) {
				criteria.andLogLevelEqualTo(operLogForm.getLogLevel());
			}
			if (StringUtil.isNotEmpty(operLogForm.getActionType())) {
				criteria.andActionTypeEqualTo(operLogForm.getActionType());
			}
			if (StringUtil.isNotEmpty(operLogForm.getOperUserName())) {
				criteria.andOperUserNameLike("%" + operLogForm.getOperUserName() + "%");
			}
			if (StringUtil.isNotEmpty(operLogForm.getComments())) {
				criteria.andOperUserNameLike("%" + operLogForm.getComments() + "%");
			}
			if (null != operLogForm.getLogType()) {
				criteria.andLogTypeEqualTo(operLogForm.getLogType());
			}
			if (StringUtil.isNotEmpty(operLogForm.getOperDateSort())) {
				operLogExample.setOrderByClause(
						"identificationNo " + ("asc".equalsIgnoreCase(operLogForm.getOperDateSort()) ? "asc" : "desc"));
			}
			Page<OperLog> queryResult = operLogService.getPageData(operLogForm.getPageNum(), operLogForm.getPageSize(),
					operLogExample);
			return AllResult.okJSON(queryResult);
		} catch (Exception e) {
			LOGGER.error("get scroll data error. page: {}, count: {}", operLogForm.getPageNum(), operLogForm.getPageSize(), e);
		}
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}
}
