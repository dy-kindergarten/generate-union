package com.reco.generate.controller;

import com.google.common.collect.Lists;
import com.reco.generate.entity.UserPayHistory;
import com.reco.generate.entity.UserPayInfo;
import com.reco.generate.entity.UserPayVal;
import com.reco.generate.service.UserPayHistoryService;
import com.reco.generate.service.UserPayInfoService;
import com.reco.generate.service.UserPayValService;
import com.reco.generate.utils.DateUtils;
import com.reco.generate.utils.ExcelUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/5/31 10:37
 * @Description: 用户支付信息管理
 */
@RestController
@RequestMapping(value = "pay")
public class PaymentRecordController {

    @Autowired
    private UserPayValService userPayValService;
    @Autowired
    private UserPayInfoService userPayInfoService;
    @Autowired
    private UserPayHistoryService userPayHistoryService;

    /**
     * 导出报表
     *
     * @param response
     * @param userId
     */
    @PostMapping(value = "exportReport")
    public void exportReport(HttpServletResponse response, String userId) throws IllegalAccessException {
        List<String> titleList = Lists.newArrayList();
        XSSFWorkbook xssfWorkbook = null;
        List<UserPayVal> payVals = userPayValService.findByUserId(userId);
        if (null != payVals && payVals.size() > 0) {
            Field[] fields = UserPayVal.class.getDeclaredFields();
            titleList = this.createPayRecordTitle(fields);
            xssfWorkbook = ExcelUtils.initXSSFWorkbook(0, titleList);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            for (int i = 0; i < payVals.size(); i++) {
                XSSFRow row = xssfSheet.createRow(i + 1);
                for (int j = 0; j < fields.length; j++) {
                    fields[j].setAccessible(true);
                    XSSFCell cell = row.createCell(j);
                    Object value = fields[j].get(payVals.get(i));
                    if (value instanceof Date) {
                        cell.setCellValue(DateUtils.getDate((Date) value, "yyyy-MM-dd HH:mm:ss"));
                    } else {
                        cell.setCellValue(value.toString());
                    }
                }
            }

        } else {
            List<UserPayInfo> payInfos = userPayInfoService.findByUserId(userId);
            if (null != payInfos && payInfos.size() > 0) {
                Field[] fields = UserPayInfo.class.getDeclaredFields();
                titleList = this.createPayRecordTitle(fields);
                xssfWorkbook = ExcelUtils.initXSSFWorkbook(0, titleList);
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                for (int i = 0; i < payInfos.size(); i++) {
                    XSSFRow row = xssfSheet.createRow(i + 1);
                    for (int j = 0; j < fields.length; j++) {
                        fields[j].setAccessible(true);
                        XSSFCell cell = row.createCell(j);
                        Object value = fields[j].get(payInfos.get(i));
                        if (value instanceof Date) {
                            cell.setCellValue(DateUtils.getDate((Date) value, "yyyy-MM-dd HH:mm:ss"));
                        } else {
                            cell.setCellValue(value.toString());
                        }
                    }
                }
            } else {
                List<UserPayHistory> payHistories = userPayHistoryService.findByUserId(userId);
                if (null != payHistories && payHistories.size() > 0) {
                    Field[] fields = UserPayHistory.class.getDeclaredFields();
                    titleList = this.createPayRecordTitle(fields);
                    xssfWorkbook = ExcelUtils.initXSSFWorkbook(0, titleList);
                    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                    for (int i = 0; i < payHistories.size(); i++) {
                        XSSFRow row = xssfSheet.createRow(i + 1);
                        for (int j = 0; j < fields.length; j++) {
                            fields[j].setAccessible(true);
                            XSSFCell cell = row.createCell(j);
                            Object value = fields[j].get(payHistories.get(i));
                            if (value instanceof Date) {
                                cell.setCellValue(DateUtils.getDate((Date) value, "yyyy-MM-dd HH:mm:ss"));
                            } else {
                                cell.setCellValue(value.toString());
                            }
                        }
                    }
                }
            }
        }

        // 导出文件
        if (null != titleList && titleList.size() > 0) {
            ExcelUtils.writeToResponse(xssfWorkbook, response, userId + ".xlsx");
        }
    }

    /**
     * 构造标题行
     *
     * @return
     */
    private List<String> createPayRecordTitle(Field[] fields) {
        List<String> titleList = Lists.newArrayList();
        for (int i = 0; i < fields.length; i++) {
            titleList.add(fields[i].getName());
        }
        return titleList;
    }
}
