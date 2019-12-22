package com.lr.common.base;

/**
 * 文章列表初始化分页类
 *
 * 接口参数
 *
 * @author shijie.xu
 * @since 2019年01月04日
 */
public class PageVO {
    private Integer pageNum;
    private Integer pageSize;
    /**
     * 查询条件
     */
    private String query;

    private Integer type;

    private String timeString;


    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
