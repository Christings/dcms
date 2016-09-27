package com.web.bean.form;

/**
 * 公共（接收前端传递参数)  TODO... 有其他的话在添加
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class CommonForm {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 每页显示数量
     */
    private int pageSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
