package com.web.core.util.page;

/**
 * 说明： 分页功能
 * 
 * @author QGL
 * @version 创建时间：Oct 18, 2010 1:33:10 PM
 */
public class Page {

	public final int DEFAULT_PAGESIZE = 10; // 每页记录数
	public final int DEFAULT_PAGE = 1; // 默认显示第几页

	// =========================================================================================
	protected int count; // 总的记录数
	protected int pageSize; // 每页记录数
	protected int pageCount; // 总的页数
	protected int pageNo; // 本页页号
	protected int start; // 起始记录下标(MySql从0开始,Oracle从1开始,syBase从0开始)
	private boolean isOracle = true; // (MySql从0开始,Oracle从1开始,syBase从0开始)

	// =========================================================================================

	protected String sortname;// 排序字段名
	protected String sortorder;// 排序方向

	/**
	 * 构造方法 ,默认每页20条记录,显示第一页
	 * 
	 */
	public Page() {
		pageSize = DEFAULT_PAGESIZE; // 每页大小
		pageNo = DEFAULT_PAGE; // 本页页号
	}

	/**
	 * 构造方法
	 * 
	 * @param nPageSize
	 *            每页记录数
	 * @param nPage
	 *            本页页号
	 */
	public Page(int nPageSize, int nPage) {
		pageSize = nPageSize; // 每页大小
		pageNo = nPage; // 本页页号
	}

	/**
	 * 分页初始化
	 * 
	 * @param nCount
	 *            总的记录数
	 */
	public void init(int nCount) {
		init(nCount, pageSize, pageNo);
	}

	/**
	 * 分页初始化；记录总记录数，每页记录数，当前页，并计算总页数、本页大小和检测当前页是否有效
	 * 
	 * @param nCount
	 *            总的记录数
	 * @param nPageSize
	 *            每页记录数
	 * @param nPage
	 *            本页页号
	 */
	public void init(int nCount, int nPageSize, int nPage) {
		count = nCount; // 总的记录数
		pageNo = nPage; // 本页页号
		pageSize = nPageSize; // 每页大小
		if (0 >= pageSize) {
			pageSize = DEFAULT_PAGESIZE;
		}
		pageCount = (nCount + pageSize - 1) / pageSize; // 计算总的页数

		// 防止 Page 超范围并计算当前页大小
		if (pageNo > pageCount) {
			pageNo = pageCount;
		}
		if (pageNo < 1) {
			pageNo = DEFAULT_PAGE;
		}
	}

	public String toString() {
		final StringBuffer sbf = new StringBuffer();
		sbf.append(" 总的记录数:" + count);
		sbf.append(" 每页记录数:" + pageSize);
		sbf.append(" 总的页数:" + pageCount);
		sbf.append(" 本页页号:" + pageNo);
		sbf.append(" 起始记录下标:" + start);
		sbf.append(" 排序字段：" + sortname);
		sbf.append(" 排序方向：" + sortorder);
		return sbf.toString();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getStart() {
		if (isOracle) {
			start = (this.getPageNo() - 1) * this.getPageSize() + 1;
		} else {
			start = (this.getPageNo() - 1) * this.getPageSize();
		}
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public boolean isOracle() {
		return isOracle;
	}

	public void setOracle(boolean isOracle) {
		this.isOracle = isOracle;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

}
