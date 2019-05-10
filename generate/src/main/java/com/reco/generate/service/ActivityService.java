package com.reco.generate.service;

import com.reco.generate.core.BaseService;
import com.reco.generate.entity.Activity;
import com.reco.generate.entity.ActivityExample;

import java.util.List;

public interface ActivityService extends BaseService<Activity, Integer, ActivityExample> {

    Activity findByUrl(String url);

    List<Activity> findByCname(String cname);
}
