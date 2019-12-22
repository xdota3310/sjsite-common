package com.lr.common.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章分页查询 返回类
 *
 * @author shijie.xu
 * @since 2019年01月04日
 */
public class PageResVO<T> extends ResultResponse {
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

    public PageResVO(String code, String message, Integer sum, List<T> resList) {
        super(code, message);
        this.sum = sum;
        this.resList = resList;
    }

    public PageResVO(String code, String message) {
        super(code, message);
        this.sum = 0;
        this.resList = new ArrayList<>();
    }


    public static <T> PageResVO<T> createError(String errorCode, String msg) {
        return new PageResVO<T>(errorCode, msg);
    }

    public static <T> PageResVO<T> createSuccess(String code, String message, Integer sum, List<T> resList) {
        return new PageResVO<T>(code, message, sum, resList);
    }

    @Override
    public String toString() {
        return "PageResVO{" + "sum=" + sum + ", resList=" + resList + '}';
    }
}
