package com.lr.common.Excel;


import java.util.ArrayList;
import java.util.List;

/**
 * 导出上下文
 *
 * @date 2019-3-7 10:16
 */

public class ExportContext {

    /**
     * 从map中获取需要展示数据list的key
     */
    public static final String KEY_LIST = "list";

    /**
     * 从map中获取需要前端传过来的上下文的key
     */
    public static final String KEY_EXPORT_CONTEXT = "exportContext";

    /**
     * 文件名（此字段不能为空，默认为‘数据导出’）
     */
    private String fileName = "数据导出";
    /**
     * excel标题名称（此字段可以不赋值，默认为‘标题’）
     */
    private String titleName = "标题";
    /**
     * sheet大小,用于分页时，设置每页的大小
     */
    private Integer sheetSize = 50000;
    /**
     * sheet最大记录条数，默认50000
     */
    private Integer maxSheetSize = 50000;

    /**
     * 配置列list
     */
    private List<Column> columnList = new ArrayList<>();
    /**
     * 是否添加标题,默认为添加
     */
    private boolean titleFlag = true;

    public static String getKeyList() {
        return KEY_LIST;
    }

    public static String getKeyExportContext() {
        return KEY_EXPORT_CONTEXT;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public Integer getSheetSize() {
        return sheetSize;
    }

    public void setSheetSize(Integer sheetSize) {
        this.sheetSize = sheetSize;
    }

    public Integer getMaxSheetSize() {
        return maxSheetSize;
    }

    public void setMaxSheetSize(Integer maxSheetSize) {
        this.maxSheetSize = maxSheetSize;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public boolean isTitleFlag() {
        return titleFlag;
    }

    public void setTitleFlag(boolean titleFlag) {
        this.titleFlag = titleFlag;
    }
}
