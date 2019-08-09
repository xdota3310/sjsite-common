package com.lr.common.Excel;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * excel列
 *
 * @date 2019-3-7 09:16
 */
public class Column {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 此列对应的属性名称
     */
    private String valueName;
    /**
     * 单元格宽度(默认18个字符)
     */
    private Integer cellWidth = 18 * 250;
    /**
     * 单元格格式
     */
    private CellStyle cellStyle;

    private Integer cellType;

    /**
     * 注释
     */
    private String comment;

    public Column() {
    }

    public Column(String columnName, String valueName) {
        this.columnName = columnName;
        this.valueName = valueName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Integer getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(Integer cellWidth) {
        this.cellWidth = cellWidth;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public Integer getCellType() {
        return cellType;
    }

    public void setCellType(Integer cellType) {
        this.cellType = cellType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
