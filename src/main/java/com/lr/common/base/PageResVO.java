package com.lr.common.base;

import java.util.List;

/**
 * 文章分页查询 返回类
 *
 * @author shijie.xu
 * @since 2019年01月04日
 */
public class PageResVO<T> {
    /**
     * 分页总数
     */
    private Integer sum;
    /**
     * 当前页的结果
     */
    private List<T> resList;

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public List<T> getResList() {
        return resList;
    }

    public void setResList(List<T> resList) {
        this.resList = resList;
    }

    @Override
    public String toString() {
        return "PageResVO{" + "sum=" + sum + ", resList=" + resList + '}';
    }
}
