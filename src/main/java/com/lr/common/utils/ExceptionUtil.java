package com.lr.common.utils;

/**
 * 获取异常字符串
 *
 * @author shijie.xu
 * @since 2019年12月11日
 */
public class ExceptionUtil {
    public static String getErrorString(Throwable e) {
        if(e == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        StackTraceElement[] stackTraceElement = e.getStackTrace();
        stringBuilder.append(e.getMessage());
        for(int i = 0; i < 6; i++) {
            stringBuilder.append("\n");
            stringBuilder.append(stackTraceElement[i] == null ? "" : stackTraceElement[i].toString());
        }

        return stringBuilder.toString();
    }
}
