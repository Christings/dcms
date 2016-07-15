package com.web.core.util.page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 分页功能
 * 
 * @author 杜延雷
 * @date 2016-07-15
 */
public class Page<T> implements Serializable {

	private int pageNum; //当前页
	private int pageSize; //每页的数量
	private int size; //当前页的数量
	private long count; //总记录数
	private int pageCount; //总页数
	private List<T> records; //结果集


	public Page() {
	}
	/**
	 * 封装数据对象
	 *
	 * @param list
	 */
	public Page(List<T> list) {
		if (list instanceof com.github.pagehelper.Page) {
			com.github.pagehelper.Page page = (com.github.pagehelper.Page) list;
			this.pageNum = page.getPageNum();
			this.pageSize = page.getPageSize();

			this.pageCount = page.getPages();
			this.records = page;
			this.size = page.size();
			this.count = page.getTotal();

		} else if (list instanceof Collection) {
			this.pageNum = 1;
			this.pageSize = list.size();

			this.pageCount = 1;
			this.records = list;
			this.size = list.size();
			this.count = list.size();
		}
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PageInfo{");
		sb.append("pageNum=").append(pageNum);
		sb.append(", pageSize=").append(pageSize);
		sb.append(", size=").append(size);
		sb.append(", count=").append(count);
		sb.append(", pageCount=").append(pageCount);
		sb.append(", records=");
		if (records == null) sb.append("null");
		else {
			sb.append('[');
			for (int i = 0; i < records.size(); ++i)
				sb.append(i == 0 ? "" : ", ").append(records.get(i));
			sb.append(']');
		}
		sb.append('}');
		return sb.toString();
	}
}
