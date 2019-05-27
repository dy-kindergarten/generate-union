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
     * @param to
     */
    @PostMapping(value = "exportReport")
    public void exportReport(HttpServletResponse response, String activityIds, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to) throws IOException {
        XSSFWorkbook xssfWorkbook = ExcelUtils.openExcel("C:\\Users\\dell\\Desktop\\陕西彩虹音乐专题更新情况表.xlsx");
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        List<Integer> activityIdList = Lists.newArrayList();
        if (StringUtils.isNotBlank(activityIds)) {
            List<String> idStrList = Lists.newArrayList(activityIds.split(","));
            for (String idStr : idStrList) {
                if (StringUtils.isNotBlank(idStr)) {
                    activityIdList.add(Integer.valueOf(idStr));
                }
            }
        }

        // 专题统计数据
        List<StatisticsBo> list = dailyFlow24Service.compileActivity(activityIdList, to);
        for (int i = 0; i < list.size(); i++) {
            StatisticsBo statisticsBo = list.get(i);
            XSSFRow row = xssfSheet.getRow(4 + i);
            // 专题名称
            XSSFCell cell1 = row.getCell(0);
            cell1.setCellValue(statisticsBo.getActivityName());
            ExcelUtils.setStyle(cell1);
            // 点击量
            XSSFCell cell2 = row.getCell(1);
            cell2.setCellValue(statisticsBo.getClickNum());
            ExcelUtils.setStyle(cell2);

            // 歌曲订购量
            List<PurchaseRecordBo> recordList = userPayHistoryService.findBySongIds(FileUtils.findSongIds("prod", statisticsBo.getUrl()), statisticsBo.getCreateTime(), to);
            XSSFCell cell3 = row.getCell(2);
            if(null == recordList) {
                cell3.setCellValue(0);
            } else {
                cell3.setCellValue(recordList.size());
                StringBuilder purchase = new StringBuilder();
                for (int j = 0; j < recordList.size(); j++) {
                    PurchaseRecordBo record = recordList.get(j);
                    purchase.append("歌名：").append(record.getSongName()).append(",歌曲id： ").append(record.getSongId()).append(",用户id:").append(record.getUserId());
                    if (j != recordList.size() - 1) {
                        purchase.append("\n");
                    }
                }
                XSSFCell cell4 = row.getCell(3);
                cell4.setCellValue(purchase.toString());
                ExcelUtils.setStyle(cell4);
            }
            ExcelUtils.setStyle(cell3);


        }

        // 导出文件
        ExcelUtils.writeToResponse(xssfWorkbook, response, "专题统计.xlsx");
    }
}
