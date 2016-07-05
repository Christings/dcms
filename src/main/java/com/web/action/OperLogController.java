package com.web.action;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.core.action.BaseController;
import com.web.core.util.page.PageViewResult;
import com.web.core.util.page.QueryResult;
import com.web.entity.OperLog;
import com.web.example.OperLogExample;
import com.web.util.AllResult;

/**
 * 业务日志控制器
 *
 * @author 田军兴
 * @date 2016-06-29
 */
@Controller
@RequestMapping("/operLog")
public class OperLogController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

	/**
	 * 分页获取菜单信息
	 *
	 * @param page
	 *            当前页
	 * @param count
	 *            显示多少行
	 */
	@RequestMapping(value = "/scroll", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getScroll(@RequestParam(value = "page") int page, @RequestParam(value = "count") int count,
			HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request param: [page: {}, count: {}]", page, count);
		}

		// 校验参数
		if (page < 1 || count < 1) {
			return AllResult.buildJSON(HttpStatus.BAD_REQUEST.value(), "参数异常");
		}

		try {

			OperLogExample operLogExample = new OperLogExample();
			// 排序设置
			// example.setOrderByClause("UPDATE_DATETIME DESC");
			OperLogExample.Criteria criteria = operLogExample.createCriteria();
			// 条件设置
			// criteria.andIconIdIsNull();

			QueryResult<OperLog> queryResult = operLogService.getPageData(page, count, operLogExample);
			PageViewResult<OperLog> pageViewResult = new PageViewResult<>(count, page);
			pageViewResult.setQueryResult(queryResult);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("queryResult record count: {}", queryResult.getResultList().size());
			}
			return AllResult.okJSON(pageViewResult);
		} catch (Exception e) {
			LOGGER.error("get scroll data error. page: {}, count: {}", page, count, e);
		}
		return AllResult.buildJSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误");
	}
}
