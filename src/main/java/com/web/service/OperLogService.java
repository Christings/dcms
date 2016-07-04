package com.web.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.web.core.util.page.QueryResult;
import com.web.entity.OperLog;
import com.web.example.OperLogExample;

/**
 * Created by tians on 2016/6/29.
 */
public interface OperLogService extends IService<OperLog, String> {

	/**
	 * 数据操作类型 查询
	 */
	public static final String OPER_TYPE_SELECT = "select";
	/**
	 * 数据操作类型 插入
	 */
	public static final String OPER_TYPE_INSERT = "insert";
	/**
	 * 数据操作类型 修改
	 */
	public static final String OPER_TYPE_UPDATE = "update";
	/**
	 * 数据操作类型 删除
	 */
	public static final String OPER_TYPE_DELETE = "delete";

	/**
	 * 日志结果类型 成功
	 */
	public static final int LOG_LEVEL_SUCCESS = 0;
	/**
	 * 日志结果类型 失败
	 */
	public static final int LOG_LEVEL_ERROR = 1;

	/**
	 * 行为 创建机柜
	 */
	public static final int ACTION_ADD_CABINET = 1;
	/**
	 * 行为 创建设备
	 */
	public static final int ACTION_ADD_DEVICE = 2;
	/**
	 * 行为 创建跳线
	 */
	public static final int ACTION_ADD_JUMPER = 3;
	/**
	 * 行为 修改机柜属性
	 */
	public static final int ACTION_MODIFY_CABINET = 4;
	/**
	 * 行为 修改设备属性
	 */
	public static final int ACTION_MODIFY_DEVICE = 5;
	/**
	 * 行为 删除机柜
	 */
	public static final int ACTION_DELETE_CABINET = 6;
	/**
	 * 行为 删除设备
	 */
	public static final int ACTION_DELETE_DEVICE = 7;
	/**
	 * 行为 删除跳线
	 */
	public static final int ACTION_DELETE_JUMPER = 8;
	/**
	 * 行为 查询机柜
	 */
	public static final int ACTION_VIEW_CABINET = 9;
	/**
	 * 行为 查询设备
	 */
	public static final int ACTION_VIEW_DEVICE = 10;

	/**
	 * @param deviceName
	 *            设备名称
	 * @param operType
	 *            操作类型(增删改查)
	 * @param logLevel
	 *            日志级别
	 * @param action
	 *            动作
	 * @param operProp
	 *            操作的属性（JSON字符串格式）
	 * @param userId
	 *            操作人员ID
	 * @param userName
	 *            操作人姓名
	 * @param operDate
	 *            操作时间
	 * @param comment
	 *            备注
	 */
	public int addOperLog(String deviceName, String operType, int logLevel, int action, String operProp, String userId,
			String userName, Date operDate, String comment);

	public int addOperLog(HttpServletRequest request, String deviceName, String operType, int logLevel, int action, String operProp,
			String comment);

	/**
	 * 分页查询日志
	 */
	public QueryResult<OperLog> getPageData(int pagination, int maxResult, OperLogExample operLogExample);

}
