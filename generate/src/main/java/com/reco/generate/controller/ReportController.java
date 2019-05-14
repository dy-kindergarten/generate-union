package com.reco.generate.controller;

import com.google.common.collect.Lists;
import com.reco.generate.bo.StatisticsBo;
import com.reco.generate.bo.PurchaseRecordBo;
import com.reco.generate.core.BaseController;
import com.reco.generate.entity.Activity;
import com.reco.generate.entity.DailyFlow24;
import com.reco.generate.entity.DailyFlow24Example;
import com.reco.generate.service.ActivityService;
import com.reco.generate.service.DailyFlow24Service;
import com.reco.generate.service.UserPayHistoryService;
import com.reco.generate.utils.ExcelUtils;
import com.reco.generate.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "report")
public class ReportController extends BaseController<DailyFlow24, Integer, DailyFlow24Example, DailyFlow24Service> {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private DailyFlow24Service dailyFlow24Service;
    @Autowired
    private UserPayHistoryService userPayHistoryService;

    @Autowired
    @Override
    protected void setService(DailyFlow24Service dailyFlow24Service) {
        this.service = dailyFlow24Service;
    }

    /**
     * 导出报表
     *
     * @param response
     * @param activityIds
     * @param from
     * @param to
     */
    @PostMapping(value = "exportReport")
    public void exportReport(HttpServletResponse response, String activityIds, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to) throws IOException {
        XSSFWorkbook xssfWorkbook = ExcelUtils.initXSSFWorkbook(0, Lists.newArrayList("专题名称", "点击量", "订购数量"));
        XSSFSheet xssfSheet = xssfWorkbook.createSheet();

        List<Activity> activityList = Lists.newArrayList();
        if (StringUtils.isNotBlank(activityIds)) {
            List<String> activityIdList = Lists.newArrayList(activityIds.split(","));
            for (String activityId : activityIdList) {
                if (StringUtils.isNotBlank(activityId)) {
                    activityList.add(activityService.selectByPrimaryKey(Integer.valueOf(activityId)));
                }
            }
        } else {
            activityList.addAll(activityService.findRecommend());
        }

        // 专题统计数据
        StatisticsBo statisticsBo = null;
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            XSSFRow row = xssfSheet.createRow(i + 1);
            // 专题名称
            XSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(activity.getCname());
            ExcelUtils.setStyle(cell1);

            // 点击量、统计时段
            statisticsBo = dailyFlow24Service.compileByActivityName(activity.getSocnew(), from, to);
            XSSFCell cell2 = row.createCell(1);
            cell2.setCellValue(statisticsBo.getTotal());
            ExcelUtils.setStyle(cell2);

            // 歌曲订购量
            List<PurchaseRecordBo> recordList = userPayHistoryService.findBySongIds(FileUtils.findSongIds("prod", activity.getUrl()), from, to);
            StringBuilder purchase = new StringBuilder();
            for (int j = 0; j < recordList.size(); j++) {
                PurchaseRecordBo record = recordList.get(j);
                if (StringUtils.isNotBlank(record.getSongName()) && record.getPurchaseCount() > 0L) {
                    purchase.append(record.getSongName()).append(": ").append(record.getPurchaseCount());
                    if (j != recordList.size() - 1) {
                        purchase.append("\n");
                    }
                }
            }
            XSSFCell cell3 = row.createCell(2);
            cell3.setCellValue(purchase.toString());
            ExcelUtils.setStyle(cell3);
        }

        // 导出文件
        ExcelUtils.writeToResponse(xssfWorkbook, response, "专题统计.xlsx");
    }
}
