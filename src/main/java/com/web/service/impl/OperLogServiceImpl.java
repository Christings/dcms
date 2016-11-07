package com.web.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.web.core.util.page.Page;
import com.web.entity.OperLog;
import com.web.entity.User;
import com.web.example.OperLogExample;
import com.web.mappers.OperLogMapper;
import com.web.service.OperLogService;
import com.web.util.DateUtil;
import com.web.util.UUIDGenerator;
import com.web.util.WebUtils;

/**
 * 业务日志service
 *
 * @author 田军兴
 * @date 2016-06-29
 */
@Service("operLogService")
@Transactional
public class OperLogServiceImpl implements OperLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	private OperLogMapper operLogMapper;
	@Autowired
	private HttpSession session;

	@Override
	public int addSystemLog(OperLog.operTypeEnum operType, OperLog.actionSystemEnum systemAction, String operProp) {
		try {
			OperLog operLog = new OperLog();
			User user = getCurrentUser();
			operLog.setId(UUIDGenerator.generatorRandomUUID());
			operLog.setOperType(getOperType(operType));
			operLog.setActionType(getActionSystem(operType, systemAction));
			if (!OperLog.operTypeEnum.select.equals(operType)) {// 如果是查询，不存储属性
				operLog.setOperProp(operProp);
			}
			operLog.setOperUserId(user.getId());
			operLog.setOperUserName(user.getUsername());
			operLog.setOperDate(new Timestamp(DateUtil.getMillis(new Date())));
			operLog.setLogLevel(getLogLevel(OperLog.logLevelEnum.success));// 默认成功
			operLog.setLogType(0);
			return operLogMapper.insert(operLog);
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.error("OperLogService.addSystemLog出错！");
			}
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int addSystemLog(OperLog.operTypeEnum operType, OperLog.actionSystemEnum systemAction, String operProp,
			OperLog.logLevelEnum logLevel) {
		try {
			OperLog operLog = new OperLog();
			User user = getCurrentUser();
			operLog.setId(UUIDGenerator.generatorRandomUUID());
			if (!OperLog.operTypeEnum.select.equals(operType)) {// 如果是查询，不存储属性
				operLog.setOperProp(operProp);
			}
			operLog.setActionType(getActionSystem(operType, systemAction));
			operLog.setOperType(getOperType(operType));
			operLog.setOperUserId(user.getId());
			operLog.setOperUserName(user.getUsername());
			operLog.setOperDate(new Timestamp(DateUtil.getMillis(new Date())));
			operLog.setLogLevel(getLogLevel(logLevel));// 默认成功
			operLog.setLogType(0);
			return operLogMapper.insert(operLog);
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.error("OperLogService.addSystemLog出错！");
			}
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int addBusinessLog(String deviceName, OperLog.operTypeEnum operType, OperLog.actionBusinessEnum busiAction,
			String operProp) {
		try {
			OperLog operLog = new OperLog();
			User user = getCurrentUser();
			operLog.setId(UUIDGenerator.generatorRandomUUID());
			operLog.setDeviceName(deviceName);
			if (!OperLog.operTypeEnum.select.equals(operType)) {// 如果是查询，不存储属性
				operLog.setOperProp(operProp);
			}
			operLog.setActionType(getActionBusiness(operType, busiAction));
			operLog.setOperType(getOperType(operType));
			operLog.setOperUserId(user.getId());
			operLog.setOperUserName(user.getUsername());
			operLog.setOperDate(new Timestamp(DateUtil.getMillis(new Date())));
			operLog.setLogLevel(getLogLevel(OperLog.logLevelEnum.success));// 默认成功
			operLog.setLogType(1);
			return operLogMapper.insert(operLog);
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.error("OperLogService.addSystemLog出错！");
			}
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int addBusinessLog(String deviceName, OperLog.operTypeEnum operType, OperLog.actionBusinessEnum busiAction, String operProp,
			OperLog.logLevelEnum logLevel) {
		try {
			OperLog operLog = new OperLog();
			User user = getCurrentUser();
			operLog.setId(UUIDGenerator.generatorRandomUUID());
			operLog.setDeviceName(deviceName);
			if (!OperLog.operTypeEnum.select.equals(operType)) {// 如果是查询，不存储属性
				operLog.setOperProp(operProp);
			}
			operLog.setActionType(getActionBusiness(operType, busiAction));
			operLog.setOperType(getOperType(operType));
			operLog.setOperUserId(user.getId());
			operLog.setOperUserName(user.getUsername());
			operLog.setOperDate(new Timestamp(DateUtil.getMillis(new Date())));
			operLog.setLogLevel(getLogLevel(logLevel));
			operLog.setLogType(1);
			return operLogMapper.insert(operLog);
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.error("OperLogService.addSystemLog出错！");
			}
			// e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Page<OperLog> getPageData(int pagination, int maxResult, OperLogExample operLogExample) {
		// 分页
		PageHelper.startPage(pagination, maxResult);
		// 查询数据
		List<OperLog> operLogs = operLogMapper.selectByExample(operLogExample);
		Page<OperLog> pageInfo = new Page<>(operLogs);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get operLog pageData object count: {}", pageInfo.getRecords().size());
		}
		return pageInfo;
	}

	private String getOperType(OperLog.operTypeEnum operTypeEnum) {
		switch (operTypeEnum) {
		case select:
			return "select";
		case insert:
			return "insert";
		case update:
			return "update";
		case delete:
			return "delete";
		}
		return "";
	}

	private String getActionSystem(OperLog.operTypeEnum operTypeEnum, OperLog.actionSystemEnum systemAction) {
		String operType = getOperTypeCn(operTypeEnum);
		switch (systemAction) {
		case user:
			return operType + "用户";
		case menu:
			return operType + "菜单";
		case role:
			return operType + "角色";
		case dataDic:
			return operType + "数据词典";
		case login:
			return "登陆系统";
		case logout:
			return "登出系统";
		case log:
			return operType + "日志";
		}
		return "";
	}

	private String getActionBusiness(OperLog.operTypeEnum operTypeEnum, OperLog.actionBusinessEnum businessAction) {
		String operType = getOperTypeCn(operTypeEnum);
		switch (businessAction) {
		case cabinet:
			return operType + "机柜";
		case device:
			return operType + "设备";
		case jumper:
			return operType + "跳线";
		case cabinetPro:
			return operType + "机柜属性";
		case devicePro:
			return operType + "设备属性";
		case roomIcn:
			return operType + "机房平面图";
		case room:
			return operType + "机房";
		}
		return "";
	}

	private String getOperTypeCn(OperLog.operTypeEnum operTypeEnum) {
		String operType = "";
		switch (operTypeEnum) {
		case select:
			operType = "查询";
			break;
		case insert:
			operType = "新建";
			break;
		case update:
			operType = "修改";
			break;
		case delete:
			operType = "删除";
			break;
		}
		return operType;
	}

	private int getLogLevel(OperLog.logLevelEnum logLevelEnum) {
		int logLevel = 0;
		switch (logLevelEnum) {
		case success:
			logLevel = 0;
		case error:
			logLevel = 1;
		}
		return logLevel;
	}

	private User getCurrentUser() {
		return (User) session.getAttribute(WebUtils.SESSION_KEY_USER);
	}
}
