package com.web.core.util.page;

import org.apache.ibatis.session.RowBounds;

/**
 * Created by tians on 2016/6/18.
 */
public class PageBounds extends RowBounds {
    //总记录数
    private int total;
    //查询起始位置
    private int offset;

    //查询条数
    private  int limit;

    public PageBounds(){
        this.offset = NO_ROW_OFFSET;
        this.limit  = NO_ROW_LIMIT;
    }

    public PageBounds(int offset, int limit){
        this.offset = offset;
        this.limit  = limit;
    }
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setMeToDefault() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public int getSelectCount() {
        return limit + offset;
    }

}
