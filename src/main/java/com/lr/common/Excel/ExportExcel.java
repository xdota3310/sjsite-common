package com.lr.common.Excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel导出注解
 *
 * @date 2019-03-15 11:27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportExcel {

    /**
     * excel解析视图，默认为{@link GenericExcelView}
     * 最好指定，否则可能有问题，尽量不要用默认的view
     */
    Class excelView() default GenericExcelView.class;
}
