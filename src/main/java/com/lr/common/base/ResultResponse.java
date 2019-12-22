package com.lr.common.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口公共返回类
 *
 * @author shijie.xu
 * @since 2019年01月03日
 */
public class ResultResponse<T> implements Serializable {

    /**
     * 成功
     */
    public static final String SUCCESS = "0";

    /**
     * 业务异常
     */
    public static final String BUISSNESSFAIL = "-1";

    /**
     * 程序异常
     */
    public static final String EXCEPTIONFAIL = "-2";

    private String code;

    private String message;

    private T data;

    protected ResultResponse() {
    }

    protected ResultResponse(String code) {
        this.code = code;
    }

    protected ResultResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    protected ResultResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }

    protected ResultResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code.equals(SUCCESS);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    /**
     *      * 正确的返回类型
     *      * @param <T>
     *      * @return
     *      
     */
    public static <T> ResultResponse<T> createBySuccess() {
        return new ResultResponse<T>(SUCCESS, "success");
    }

    public static <T> ResultResponse<T> createBySuccess(T data) {
        return new ResultResponse<T>(SUCCESS, "success", data);
    }

    /**
     *      * 错误的返回类型
     *      * @param <T>
     *      * @return
     *      
     */
    public static <T> ResultResponse<T> createBackError(T data) {
        return new ResultResponse<T>(BUISSNESSFAIL, "fail", data);
    }

    /**
     *      * 错误的返回类型
     *      
     */
    public static <T> ResultResponse<T> createByError(String errorCode, String... args) {
        return new ResultResponse<T>(errorCode, args.toString());
    }

    public static <T> ResultResponse<T> createByError(String errorCode, String msg) {
        return new ResultResponse<T>(errorCode, msg);
    }

    /**
     *      * 如果结果只有一个值，用此方法
     *      * @param key
     *      * @param obj
     *      * @param <T>
     *      * @return
     *      
     */
    public static <T> ResultResponse<T> createBySuccessKey(String key, Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, obj);
        return createBySuccess((T) map);
    }

    public static <T> ResultResponse<T> createBySuccessMap(Map map) {
        return createBySuccess((T) map);
    }

}
