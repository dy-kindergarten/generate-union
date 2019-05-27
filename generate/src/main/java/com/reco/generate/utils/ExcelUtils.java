package com.reco.generate.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtils {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 打开Excel
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static XSSFWorkbook openExcel(String path) throws IOException {
        return new XSSFWorkbook(new FileInputStream(path));
    }

    /**
     * 初始化
     *
     * @param titles
     * @return
     */
    public static XSSFWorkbook initXSSFWorkbook(int rownum, List<String> titles) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet();
        // 标题
        XSSFRow titleRow = xssfSheet.createRow(rownum);
        for (int i = 0; i < titles.size(); i++) {
            XSSFCell cell = titleRow.createCell(i);
            setStyle(cell);
            cell.setCellValue(titles.get(i));
        }
        return xssfWorkbook;
    }

    /**
     * 设置样式
     *
     * @param cell
     */
    public static void setStyle(XSSFCell cell) {
        CellStyle style = cell.getSheet().getWorkbook().createCellStyle();
        Font font = cell.getSheet().getWorkbook().createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 9);
        style.setFont(font);
        cell.setCellStyle(style);
    }

    /**
     * 输出表格到文件
     *
     * @param xssfWorkbook
     * @param outPath
     */
    public static void writeToFile(XSSFWorkbook xssfWorkbook, File outPath) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outPath);
            xssfWorkbook.write(outputStream);
        } catch (FileNotFoundException e) {
            logger.error("文件未找到，" + e.getMessage());
        } catch (IOException e) {
            logger.error("I/O异常，" + e.getMessage());
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("关闭流I/O异常，" + e.getMessage());
                }
            }
            if (null != xssfWorkbook) {
                try {
                    xssfWorkbook.close();
                } catch (IOException e) {
                    logger.error("关闭数据表格I/O异常，" + e.getMessage());
                }
            }
        }
    }

    /**
     * 输出表格到浏览器
     *
     * @param xssfWorkbook
     * @param response
     */
    public static void writeToResponse(XSSFWorkbook xssfWorkbook, HttpServletResponse response, String fileName) {
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            xssfWorkbook.write(outputStream);
        } catch (FileNotFoundException e) {
            logger.error("文件未找到，" + e.getMessage());
        } catch (IOException e) {
            logger.error("I/O异常，" + e.getMessage());
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("关闭流I/O异常，" + e.getMessage());
                }
            }
            if (null != xssfWorkbook) {
                try {
                    xssfWorkbook.close();
                } catch (IOException e) {
                    logger.error("关闭数据表格I/O异常，" + e.getMessage());
                }
            }
        }
    }
}
