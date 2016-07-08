package com.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.core.util.page.QueryResult;
import com.web.entity.OperLog;
import com.web.entity.User;
import com.web.example.OperLogExample;
import com.web.mappers.OperLogMapper;
import com.web.service.OperLogService;
import com.web.util.UUIDGenerator;
import com.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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
	public int addOperLog(String deviceName, String operType, int logLevel, int action, String operProp, String userId,
			String userName, Date operDate, String comment) {
		try {
			String id = UUIDGenerator.generatorRandomUUID();
			OperLog log = new OperLog();
			log.setId(id);
			log.setDeviceName(deviceName);
			log.setAction(action);
			log.setLogLevel(logLevel);
			log.setOperProp(operProp);
            log.setOperType(operType);
			log.setOperUserId(userId);
			log.setOperUserName(userName);
			log.setOperDate(operDate);
			log.setComments(comment);
			log.setLogType(OperLogService.LOG_TYPE_BUSINESS);
			return operLogMapper.insert(log);
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.error("记录日志失败！");
			}
            e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int addOperLog(String deviceName, String operType, int logLevel, int action, String operProp,
			String comment) {
		try {
			User user = (User) session.getAttribute(WebUtils.SESSION_KEY_USER);
			String id = UUIDGenerator.generatorRandomUUID();
			OperLog operLog = new OperLog();
			operLog.setId(id);
			operLog.setDeviceName(deviceName);
			operLog.setLogLevel(logLevel);
			operLog.setAction(action);
			operLog.setOperProp(operProp);
			operLog.setOperUserId(user.getId());
			operLog.setOperUserName(user.getUserName());
			operLog.setOperDate(new Date());
			operLog.setComments(comment);
			operLog.setLogType(OperLogService.LOG_TYPE_BUSINESS);
			return operLogMapper.insert(operLog);
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.error("记录日志失败！");
			}
			return 0;
		}
	}

	@Override
	public int addSystemLog(String operType, int logLevel, int action, String operProp, String comment) {
		try {
			User user =(User)session.getAttribute(WebUtils.SESSION_KEY_USER);
			String id = UUIDGenerator.generatorRandomUUID();
			OperLog operLog = new OperLog();
			operLog.setId(id);
			operLog.setLogLevel(logLevel);
			operLog.setAction(action);
			operLog.setOperProp(operProp);
			operLog.setOperUserId(user.getId());
			operLog.setOperUserName(user.getUserName());
			operLog.setOperDate(new Date());
			operLog.setComments(comment);
			operLog.setLogType(OperLogService.LOG_TYPE_SYSTEM);
			return operLogMapper.insert(operLog);
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.error("记录日志失败！");
			}
			return 0;
		}
	}

	@Override
	public int save(OperLog entity) {
		return operLogMapper.insert(entity);
	}

	@Override
	public int updateById(OperLog entity) {
		return operLogMapper.updateByPrimaryKey(entity);
	}

	@Override
	public int deleteById(String key) {
		return operLogMapper.deleteByPrimaryKey(key);
	}

	@Override
	public OperLog getById(String key) {
		return operLogMapper.selectByPrimaryKey(key);
	}

	@Override
	public List<OperLog> getAll() {
		return null;
	}

	@Override
	public QueryResult<OperLog> getPageData(int pagination, int maxResult, OperLogExample operLogExample) {
		// 分页
		PageHelper.startPage(pagination, maxResult);
		// 查询数据
		List<OperLog> operLogs = operLogMapper.selectByExample(operLogExample);
		PageInfo<OperLog> pageInfo = new PageInfo<>(operLogs);
		QueryResult<OperLog> queryResult = new QueryResult<>(pageInfo.getList(), pageInfo.getTotal());
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("get operLog pageData object count: {}", queryResult.getTotalRecord());
		}
		return queryResult;
	}

}
