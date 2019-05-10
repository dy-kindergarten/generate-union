package com.reco.generate.controller;

import com.google.common.collect.Lists;
import com.reco.generate.core.BaseController;
import com.reco.generate.core.easyui.Combobox;
import com.reco.generate.entity.Activity;
import com.reco.generate.entity.ActivityExample;
import com.reco.generate.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "activity")
public class ActivityController extends BaseController<Activity, Integer, ActivityExample, ActivityService> {

    @Autowired
    @Override
    protected void setService(ActivityService activityService) {
        this.service = activityService;
    }

    @GetMapping(value = "getComboboxData")
    public List<Combobox> getComboboxData(String cname) {
        List<Combobox> list = Lists.newArrayList();
        List<Activity> songList = this.service.findByCname(cname);
        Combobox combobox = new Combobox(-1, "请选择活动");
        list.add(combobox);
        for (Activity activity :songList) {
            combobox = new Combobox();
            combobox.setId(activity.getId());
            combobox.setText(activity.getCname());
            list.add(combobox);
        }
        return list;
    }
}
