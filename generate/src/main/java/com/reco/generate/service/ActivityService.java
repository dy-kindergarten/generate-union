package com.reco.generate.service;

import com.reco.generate.core.BaseService;
import com.reco.generate.entity.Activity;
import com.reco.generate.entity.ActivityExample;

public interface ActivityService extends BaseService<Activity, Integer, ActivityExample> {

    Activity findByUrl(String url);

    Integer getMaxId();

    Integer getMaxCsort(Integer ctype);
}
