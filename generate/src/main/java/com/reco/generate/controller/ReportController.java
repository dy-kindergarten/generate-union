package com.reco.generate.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.reco.generate.bo.StatisticsBo;
import com.reco.generate.bo.PurchaseRecordBo;
import com.reco.generate.core.BaseController;
import com.reco.generate.entity.Activity;
import com.reco.generate.entity.DailyFlow24;
import com.reco.generate.entity.DailyFlow24Example;
import com.reco.generate.service.ActivityService;
import com.reco.generate.service.DailyFlow24Service;
import com.reco.generate.service.UserPayHistoryService;
import com.reco.generate.utils.DateUtils;
import com.reco.generate.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * @param request
     * @param response
     * @param activityIds
     * @param from
     * @param to
     */
    @GetMapping(value = "exportReport")
    public void exportReport(HttpServletRequest request, HttpServletResponse response, String activityIds, Date from, Date to) {
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

        // 各专题统计数据
        List<Map<String, Object>> dataList = Lists.newArrayList();
        Map<String, Object> data = null;
        StatisticsBo statisticsBo = null;
        String dateSolt = "";
        for (Activity activity : activityList) {
            data = Maps.newLinkedHashMap();
            // 专题名称
            data.put("activityName", activity.getCname());
            // 点击量、统计时段
            statisticsBo = dailyFlow24Service.compileByActivityName(activity.getSocnew(), from, to);
            data.put("clickTotal", statisticsBo.getTotal());
            if(null != statisticsBo.getStartDate() && null != statisticsBo.getEndDate()) {
                dateSolt = DateUtils.getDate(statisticsBo.getStartDate(), "yyyy/mm/DD") + " - " + DateUtils.getDate(statisticsBo.getEndDate(), "yyyy/mm/DD");
            }
            data.put("dateSolt", dateSolt);
            // 歌曲订购量
            List<PurchaseRecordBo> recordList = userPayHistoryService.findBySongIds(FileUtils.findSongIds(activity.getUrl()), from, to);
            StringBuilder purchase = new StringBuilder();
            for (int i = 0; i < recordList.size(); i++) {
                purchase.append(recordList.get(i).getSongName()).append(": ").append(recordList.get(i).getPurchaseCount());
                if(i != recordList.size() - 1) {
                    purchase.append("\n");
                }
            }
            data.put("purchase", purchase.toString());
            dataList.add(data);
        }
        System.out.println(dataList);
    }
}
