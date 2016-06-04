package com.web.core.util.page;

import javax.servlet.http.HttpServletRequest;

import com.web.core.util.ContextHolderUtils;

/**
 * 说明： 分页功能
 * 
 * @author QGL
 * @version 创建时间：Oct 18, 2010 1:33:05 PM
 */
public class WebPage extends Page {

	private HttpServletRequest request;

	public WebPage() {
		this.request = ContextHolderUtils.getRequest();
		String s_rp = request.getParameter("rows");
		String s_pn = request.getParameter("page");

		sortname = request.getParameter("sort");
		sortorder = request.getParameter("order");
		try {
			pageSize = Integer.valueOf(s_rp);
		} catch (Exception e) {
			pageSize = super.DEFAULT_PAGESIZE; // 每页大小
		}
		try {
			pageNo = Integer.valueOf(s_pn);
		} catch (Exception e) {
			pageNo = super.DEFAULT_PAGE;// 本页页号
		}
	}

	public WebPage(int size) {
		this.request = ContextHolderUtils.getRequest();
		String s_pn = request.getParameter("page");
		try {
			pageSize = size;
		} catch (Exception e) {
			pageSize = super.DEFAULT_PAGESIZE; // 每页大小
		}
		try {
			pageNo = Integer.valueOf(s_pn);
		} catch (Exception e) {
			pageNo = super.DEFAULT_PAGE;// 本页页号
		}
	}
}
