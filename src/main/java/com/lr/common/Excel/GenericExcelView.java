//package com.lr.common.Excel;
//
//import com.alibaba.fastjson.JSON;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//import org.springframework.web.servlet.view.document.AbstractExcelView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.text.NumberFormat;
//import java.util.*;
//
///**
// * 通用excel视图
// *
// * @date 2019-3-7 09:16
// */
//@Slf4j
//@Service
//public class GenericExcelView<T> extends AbstractExcelView {
//
//    /**
//     * 日期格式化map
//     */
//    private Map<String, String> dateFormatMap = new HashMap<>();
//
//    /**
//     * 数字格式化map
//     */
//    private Map<String,.annotation.NumberFormat> numberFormatMap = new HashMap<>();
//
//    /**
//     * 字典数据
//     */
//    private Map<String, String> dictMap = new HashMap<>();
//    private List<String> dictNameList = new ArrayList<>();
//
//    /**
//     * 方法Map
//     */
//    private Map<String, Method> methodMap = new HashMap<>();
//
//    @Autowired
//    private DictDetailService dictDetailService;
//
//    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
//
//    /**
//     * 构建excel文档
//     *
//     * @param map      对应ModelAndView中的model
//     * @param workbook 工作簿
//     * @param request
//     * @param response
//     */
//    @Override
//    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) {
//        // 1. 获取导出上下文
//        ExportContext exportContext = (ExportContext) map.get(ExportContext.KEY_EXPORT_CONTEXT);
//        if (exportContext == null) {
//            exportContext = new ExportContext();
//        }
//        initContext(exportContext);
//
//        // 2. 获取导出数据
//        @SuppressWarnings("unchecked") List<T> list = (List<T>) map.get(ExportContext.KEY_LIST);
//        if (CollectionUtils.isNotEmpty(list)) {
//            // 读取字典配置
//            readDictInfo();
//        }
//
//        numberFormat.setMaximumFractionDigits(8);
//
//        try {
//            // 3. 创建excel
//            createExcel(workbook, list, response, map, exportContext);
//            // 4. 执行结束方法
//            endMethod();
//        } catch (Exception e) {
//            log.error("excel导出异常", e);
//            response.setContentType("application/json; charset=utf-8");
//            try (PrintWriter out = response.getWriter()) {
//                out.append(JSON.toJSONString(Result.getBusinessException(ResultCodeEnum.EXPORT_EXCEL_ERROR.getMsg(), ResultCodeEnum.EXPORT_EXCEL_ERROR.getCode())));
//            } catch (IOException e1) {
//                log.error("json转换异常", e1);
//            }
//        }
//    }
//
//    /**
//     * 初始化环境
//     *
//     * @param exportContext 导出上下文
//     */
//    protected void initContext(ExportContext exportContext) {
//    }
//
//    /**
//     * 设置各列
//     * eg: 	cv.nextColumn("合同编号","code", 18*250);
//     *
//     * @param workbook
//     * @param map
//     * @param exportContext 导出上下文
//     */
//    protected void setColumn(HSSFWorkbook workbook, Map<String, Object> map, ExportContext exportContext) {
//    }
//
//    /**
//     * Description:自定义valueMap钩子方法
//     *
//     * @param obj      导出数据对象
//     * @param valueMap 各列值map @eg： id:1
//     * @param map      对应model
//     */
//    protected void setSelfDefineValueMap(T obj, Map<String, Object> valueMap, Map<String, Object> map) {
//    }
//
//    /**
//     * 字典设置
//     */
//    protected void setDictMap(Map<String, Integer> dictIdMap) {
//    }
//
//    /**
//     * 读取字典信息
//     */
//    private void readDictInfo() {
//        Map<String, Integer> dictIdMap = new HashMap<>();
//        setDictMap(dictIdMap);
//        if (dictIdMap.isEmpty()) {
//            return;
//        }
//
//        for (Map.Entry<String, Integer> entry : dictIdMap.entrySet()) {
//            List<SysDictionarydetailEntity> list;
//            try {
//                list = dictDetailService.queryListByDictId(String.valueOf(entry.getValue()));
//            } catch (Exception e) {
//                logger.error("获取字典：" + entry.getKey() + ":" + entry.getValue() + "发生异常", e);
//                continue;
//            }
//            if (CollectionUtils.isEmpty(list)) {
//                continue;
//            }
//            for (SysDictionarydetailEntity dict : list) {
//                dictMap.put(entry.getKey() + ":" + dict.getIntro(), dict.getTitle());
//            }
//            dictNameList.add(entry.getKey());
//        }
//    }
//
//    /**
//     * Description: 导出结束执行钩子方法
//     */
//    protected void endMethod() {
//    }
//
//    /**
//     * Description:组装excel
//     *
//     * @param workbook      工作簿
//     * @param list          数据列表
//     * @param map           对应controller层设置的model
//     * @param exportContext 导出上下文
//     * @throws Exception
//     */
//    private void createExcel(HSSFWorkbook workbook, List<T> list, HttpServletResponse response, Map<String, Object> map, ExportContext exportContext) throws Exception {
//        Assert.notNull(exportContext.getFileName(), "文件名不能为空");
//
//        // 1. 添加column到导出上下文中
//        setColumn(workbook, map, exportContext);
//        Assert.notEmpty(exportContext.getColumnList(), "列表项不能为空");
//
//        // 2. 如果人为为sheetSize赋值则进行页数计算
//        HSSFSheet sheet;
//        int sheetPage = 1;
//        Integer sheetSize = exportContext.getSheetSize();
//        if (list != null && sheetSize != null) {
//            int listSize = list.size();
//            if (listSize % sheetSize == 0) {
//                sheetPage = listSize / sheetSize;
//            } else {
//                sheetPage = listSize / sheetSize + 1;
//            }
//        }
//        //title行的样式公用，防止分sheet较多时超过style的最大值
//        CellStyle titleStyle = getTitleStyle(workbook);
//        // 如果sheetPage>1则进行分页
//        List<Column> columnList = exportContext.getColumnList();
//
//        //3. 创建sheet
//        if (sheetPage > 1) {
//            for (int i = 1; i <= sheetPage; i++) {
//                sheet = workbook.createSheet(exportContext.getTitleName() + i);
//                List<T> subList;
//                if (i == sheetPage) {
//                    subList = list.subList(sheetSize * (i - 1), list.size());
//                } else {
//                    subList = list.subList(sheetSize * (i - 1), sheetSize * i);
//                }
//                this.createSheet(workbook, sheet, columnList, subList, map, titleStyle, exportContext);
//            }
//        } else {
//            sheet = workbook.createSheet(exportContext.getTitleName());
//            this.createSheet(workbook, sheet, columnList, list, map, titleStyle, exportContext);
//        }
//
//        // 4. response设置
//        this.createExcelContentType(workbook, response, exportContext);
//    }
//
//    /**
//     * Description:创建sheet
//     *
//     * @param workbook
//     * @param sheet
//     * @param columnList
//     * @param list
//     */
//    private void createSheet(HSSFWorkbook workbook, HSSFSheet sheet, List<Column> columnList, List<T> list, Map<String, Object> map, CellStyle titleStyle, ExportContext exportContext) {
//        int j = 0;
//
//        //1. 创建excel标题
//        if (exportContext.isTitleFlag()) {
//            Row rowTitle = sheet.createRow(j++);
//            Cell cellTitle = rowTitle.createCell(0);
//            cellTitle.setCellValue(exportContext.getTitleName());
//            cellTitle.setCellStyle(getHeadlineStyle(workbook));
//            rowTitle.setHeight((short) 500);
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (columnList.size() - 1)));
//        }
//
//        //2. 创建列标题
//        //各列所指的属性
//        List<String> valueNameList = new ArrayList<>();
//        Row row1 = sheet.createRow(j++);
//        for (int i = 0; i < columnList.size(); i++) {
//            Column myTitle = columnList.get(i);
//            Cell cell = row1.createCell(i);
//            cell.setCellValue(myTitle.getColumnName());
//            cell.setCellStyle(titleStyle);
//            setComment(sheet, cell, myTitle);
//            sheet.setColumnWidth(i, myTitle.getCellWidth());
//            valueNameList.add(myTitle.getValueName());
//        }
//
//        //3. 展示记录
//        if (CollectionUtils.isEmpty(list) || list.size() > exportContext.getMaxSheetSize()) {
//            // 无数据或数据条数过多
//            Row row = sheet.createRow(j);
//            Cell cell = row.createCell(0);
//            if (CollectionUtils.isEmpty(list)) {
//                cell.setCellValue("无数据!");
//            } else {
//                cell.setCellValue("数据量不能超过" + exportContext.getMaxSheetSize() + "条,请缩小查询范围!");
//            }
//            sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, valueNameList.size() - 1));
//            return;
//        }
//        Class clazz = list.get(0).getClass();
//        setMethodMap(clazz, valueNameList);
//        setFormatMap(clazz, valueNameList);
//
//        for (T obj : list) {
//            Row row = sheet.createRow(j++);
//            Map<String, Object> valueMap = this.setValueMap(obj, map, valueNameList);
//            for (int i = 0; i < columnList.size(); i++) {
//                Column column = columnList.get(i);
//                Cell cell = row.createCell(i);
//                this.setCellValue(cell, valueMap, column);
//            }
//        }
//    }
//
//    /**
//     * Description:自动封装valueMap
//     */
//    private Map<String, Object> setValueMap(T obj, Map<String, Object> map, List<String> valueNameList) {
//        valueNameList.removeAll(Collections.singleton(null));
//        Map<String, Object> valueMap = new HashMap<>(valueNameList.size());
//        // 1. 加载自定义valueMap
//        setSelfDefineValueMap(obj, valueMap, map);
//
//        // 2. 封装valueMap
//        for (String valueName : valueNameList) {
//            // 2.1 自定义赋值
//            if (valueMap.containsKey(valueName)) {
//                continue;
//            }
//
//            //拼装方法名
//            Object value;
//            try {
//                value = methodMap.get(valueName).invoke(obj);
//            } catch (Exception e) {
//                log.error("方法调用 {} : {} 失败", methodMap.get(valueName), obj);
//                continue;
//            }
//
//            if (value == null) {
//                valueMap.put(valueName, "");
//                continue;
//            }
//
//            if (dictNameList.contains(valueName)) {
//                // 从字典读取
//                valueMap.put(valueName, dictMap.get(valueName + ":" + value));
//                continue;
//            }
//
//            if (StringUtils.isNotBlank(dateFormatMap.get(valueName))) {
//                valueMap.put(valueName, DateUtil.format((Date) value, dateFormatMap.get(valueName)));
//            } else if (numberFormatMap.containsKey(valueName)) {
//                valueMap.put(valueName, numberFormat.format(value));
//            } else {
//                valueMap.put(valueName, value);
//            }
//        }
//        return valueMap;
//    }
//
//    /**
//     * 设置方法map
//     */
//    private void setMethodMap(Class<T> clazz, List<String> valueNameList) {
//        for (String valueName : valueNameList) {
//            //拼装方法名
//            String methodName = "get" + valueName.substring(0, 1).toUpperCase() + valueName.substring(1);
//            Method m;
//            try {
//                m = clazz.getMethod(methodName);
//                methodMap.put(valueName, m);
//            } catch (Exception e) {
//                log.error("反射获取方法 {} 失败", methodName);
//            }
//        }
//    }
//
//    /**
//     * 获取字段注解
//     */
//    private void setFormatMap(Class<T> clazz, List<String> valueNameList) {
//        // 获取所有字段
//        Class temp = clazz;
//        List<Field> fieldList = new ArrayList<>();
//        while (temp != null) {
//            fieldList.addAll(Arrays.asList(temp.getDeclaredFields()));
//            temp = temp.getSuperclass();
//        }
//
//        // 转换为map
//        Map<String, Field> fieldMap = new HashMap<>(fieldList.size());
//        for (Field field : fieldList) {
//            fieldMap.put(field.getName(), field);
//        }
//
//        // 格式化注解
//        setDateFormatMap(valueNameList, fieldMap, clazz);
//        setNumberFormatMap(valueNameList, fieldMap, clazz);
//    }
//
//    /**
//     * 日期格式化
//     */
//    private void setDateFormatMap(List<String> valueNameList, Map<String, Field> fieldMap, Class<T> clazz) {
//        for (String valueName : valueNameList) {
//            Field field = fieldMap.get(valueName);
//            if (field == null) {
//                log.error(clazz.getName() + "不存在" + valueName + "字段");
//                continue;
//            }
//            if (field.getType() != Date.class) {
//                continue;
//            }
//            JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
//            if (jsonFormat == null) {
//                continue;
//            }
//            dateFormatMap.put(valueName, jsonFormat.pattern());
//        }
//    }
//
//    /**
//     * 数字格式化
//     */
//    private void setNumberFormatMap(List<String> valueNameList, Map<String, Field> fieldMap, Class<T> clazz) {
//        for (String valueName : valueNameList) {
//            Field field = fieldMap.get(valueName);
//            if (field == null) {
//                log.error(clazz.getName() + "不存在" + valueName + "字段");
//                continue;
//            }
//            if (!Number.class.isAssignableFrom(field.getType())) {
//                continue;
//            }
//            annotation.NumberFormat numberFormat = field.getAnnotation(annotation.NumberFormat.class);
//            if (numberFormat == null) {
//                continue;
//            }
//            numberFormatMap.put(valueName, numberFormat);
//        }
//    }
//
//    /**
//     * Description: 为单元格赋值
//     */
//    private void setCellValue(Cell cell, Map<String, Object> map, Column column) {
//        Object value = map.get(column.getValueName());
//        if (value instanceof String) {
//            cell.setCellValue((String) value);
//        } else if (value instanceof Double) {
//            cell.setCellValue(numberFormat.format(value));
//        } else if (value instanceof Float) {
//            cell.setCellValue(numberFormat.format(value));
//        } else if (value instanceof BigDecimal) {
//            cell.setCellValue(numberFormat.format(value));
//        } else if (value instanceof Integer) {
//            cell.setCellValue((Integer) value);
//        } else if (value instanceof Date) {
//            cell.setCellValue((Date) value);
//        } else if (value instanceof RichTextString) {
//            cell.setCellValue((RichTextString) value);
//        } else if (value instanceof Calendar) {
//            cell.setCellValue((Calendar) value);
//        } else if (value != null) {
//            cell.setCellValue(value.toString());
//        }
//        if (column.getCellStyle() != null) {
//            cell.setCellStyle(column.getCellStyle());
//        } else if (column.getCellType() != null) {
//            cell.setCellType(column.getCellType());
//        }
//    }
//
//    /**
//     * Description: response设置
//     *
//     * @param workbook
//     * @param response
//     * @throws IOException
//     */
//    private void createExcelContentType(HSSFWorkbook workbook, HttpServletResponse response, ExportContext exportContext) throws IOException {
//        response.setHeader("Content-disposition", "attachment;filename=" + new String((exportContext.getFileName() + ".xls").getBytes("gb2312"), "iso8859-1"));
//        response.setContentType("application/vnd.ms-excel");
//        OutputStream outputStream = response.getOutputStream();
//        workbook.write(outputStream);
//        outputStream.flush();
//        outputStream.close();
//    }
//
//    /**
//     * Description:添加单元格注释
//     *
//     * @param sheet
//     * @param cell
//     * @param myTitle 注释的大小默认为宽为1-2列宽度和，高为3-6行高度和（1-2行高一般动态这里不用他们的高度）
//     *                如不使用默认注释大小可重写此方法
//     */
//    private void setComment(HSSFSheet sheet, Cell cell, Column myTitle) {
//        if (myTitle.getComment() != null) {
//            //创建绘图对象
//            HSSFPatriarch p = sheet.createDrawingPatriarch();
//            //获取批注对象
//            //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
//            //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
//            Comment comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 0, 2, (short) 2, 6));
//            //输入批注信息
//            comment.setString(new HSSFRichTextString(myTitle.getComment()));
//            //添加作者
//            comment.setAuthor("PMS");
//            //将批注添加到单元格对象中
//            cell.setCellComment(comment);
//        }
//    }
//
//    public static HSSFCellStyle getTitleStyle(HSSFWorkbook wookbook) {
//        HSSFCellStyle colorStyle = wookbook.createCellStyle();
//        colorStyle.setBorderLeft(BorderStyle.THIN);
//        colorStyle.setBorderTop(BorderStyle.THIN);
//        colorStyle.setBorderRight(BorderStyle.THIN);
//        colorStyle.setBorderBottom(BorderStyle.THIN);
//        colorStyle.setFillPattern(FillPatternType.BRICKS);
//        colorStyle.setAlignment(HorizontalAlignment.CENTER);
//        colorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        colorStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
//        colorStyle.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.index);
//        return colorStyle;
//    }
//
//    public static HSSFCellStyle getHeadlineStyle(HSSFWorkbook wookbook) {
//        HSSFCellStyle normalStyle = wookbook.createCellStyle();
//        normalStyle.setBorderLeft(BorderStyle.THIN);
//        normalStyle.setBorderTop(BorderStyle.THIN);
//        normalStyle.setBorderRight(BorderStyle.THIN);
//        normalStyle.setBorderBottom(BorderStyle.THIN);
//        normalStyle.setAlignment(HorizontalAlignment.CENTER);
//        normalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        normalStyle.setWrapText(true);
//        normalStyle.setFont(getFont(wookbook));
//        return normalStyle;
//    }
//
//    public static HSSFFont getFont(HSSFWorkbook wookbook) {
//        HSSFFont fontStyle = wookbook.createFont();
//        fontStyle.setFontName("Arial");
//        fontStyle.setFontHeightInPoints((short) 16);
//        fontStyle.setBold(true);
//        return fontStyle;
//    }
//}
