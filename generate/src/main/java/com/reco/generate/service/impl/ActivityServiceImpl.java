package com.reco.generate.service.impl;

import com.reco.generate.core.BaseServiceImpl;
import com.reco.generate.entity.Activity;
import com.reco.generate.entity.ActivityExample;
import com.reco.generate.repository.ActivityMapper;
import com.reco.generate.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "activityService")
public class ActivityServiceImpl extends BaseServiceImpl<Activity, Integer, ActivityExample, ActivityMapper> implements ActivityService {

    @Autowired
    @Override
    public void setDao(ActivityMapper activityMapper) {
        this.dao = activityMapper;
    }

    @Override
    public Activity findByUrl(String url) {
        ActivityExample example = new ActivityExample();
        example.createCriteria().andUrlEqualTo(url);
        List<Activity> list = this.dao.selectByExample(example);
        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public Integer getMaxId() {
        Integer maxId = this.dao.getMaxId();
        return null == maxId ? 0 : maxId;
    }

    @Override
    public Integer getMaxCsort(Integer ctype) {
        Integer maxCsort = this.dao.getMaxCsort(ctype);
        return null == maxCsort ? 0 : maxCsort;
    }
}
