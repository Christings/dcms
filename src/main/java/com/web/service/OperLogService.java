package com.web.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.web.core.util.page.QueryResult;
import com.web.entity.OperLog;
import com.web.example.OperLogExample;

/**
 * 业务日志service
 *
 * @author 田军兴
 * @date 2016-06-29
 */
public interface OperLogService {
	/**
	 * @param operType
	 *            操作类型(增删改查)
	 * @param systemAction
	 *            动作对象
	 * @param operProp
	 *            操作的属性（JSON字符串格式）
	 */
	public int addSystemLog(OperLog.operTypeEnum operType, OperLog.actionSystemEnum systemAction, String operProp);

	/**
	 * @param operType
	 *            操作类型(增删改查)
	 * @param systemAction
	 *            动作对象
	 * @param operProp
	 *            操作的属性（JSON字符串格式）
	 * @param logLevel
	 *            日记级别 成功或失败
	 */
	public int addSystemLog(OperLog.operTypeEnum operType, OperLog.actionSystemEnum systemAction, String operProp,
			OperLog.logLevelEnum logLevel);

	/**
	 * @param deviceName
	 *            设备名称
	 * @param operType
	 *            操作类型(增删改查)
	 * @param busiAction
	 *            动作对象
	 * @param operProp
	 *            操作的属性（JSON字符串格式）
	 */
	public int addBusinessLog(String deviceName, OperLog.operTypeEnum operType, OperLog.actionBusinessEnum busiAction,
			String operProp);

	/**
	 * @param deviceName
	 *            设备名称
	 * @param operType
	 *            操作类型(增删改查)
	 * @param busiAction
	 *            动作对象
	 * @param operProp
	 *            操作的属性（JSON字符串格式）
	 * @param logLevel
	 *            日志级别 成功或失败
	 */
	public int addBusinessLog(String deviceName, OperLog.operTypeEnum operType, OperLog.actionBusinessEnum busiAction, String operProp,
			OperLog.logLevelEnum logLevel);

	/**
	 * 分页查询日志
	 */
	public QueryResult<OperLog> getPageData(int pagination, int maxResult, OperLogExample operLogExample);

}
