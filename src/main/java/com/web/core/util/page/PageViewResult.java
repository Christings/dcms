package com.web.core.util.page;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * PageView返回json数据
 * @author 杜延雷
 * @date 2016-06-22
 */
public class PageViewResult<T> {
    /**
     * 分页数据
     */
    @JSONField(name = "lists")
    private List<T> records;

    /**
     * 总页数
     */
    private long totalPage = 1;
    /**
     * 每页显示记录数，不进行序列化
     */
    @JSONField(name = "count")
    private int maxResult = 12;
    /**
     * 当前页
     */
    @JSONField(name = "page")
    private int pagination = 1;
    /**
     * 总记录数
     */
    private long totalRecord;

    /**
     * 新PageViewResult对象
     * @param maxResult 每页显示的最大记录数
     * @param pagination 当前页码
     */
    public PageViewResult(int maxResult, int pagination) {
        this.maxResult = maxResult;
        this.pagination = pagination;
    }

    /**
     * 设置分页查询结果集
     * @param result 查询结果集
     * @param totalRecord 总记录数量
     */
    public void setQueryResult(List<T> result, long totalRecord) {
        setTotalRecord(totalRecord);
        setRecords(result);
    }

    /**
     * 设置分页查询结果集
     * @param queryResult 查询结果集
     */
    public void setQueryResult(QueryResult<T> queryResult) {
        setTotalRecord(queryResult.getTotalRecord());
        setRecords(queryResult.getResultList());
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
        setTotalPage(this.totalRecord % this.maxResult == 0 ? this.totalRecord / this.maxResult : this.totalRecord / this.maxResult + 1);
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }


    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public int getPagination() {
        return pagination;
    }
}
