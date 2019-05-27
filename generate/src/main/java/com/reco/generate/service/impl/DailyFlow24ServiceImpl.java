package com.reco.generate.service.impl;

import com.reco.generate.bo.StatisticsBo;
import com.reco.generate.core.BaseServiceImpl;
import com.reco.generate.entity.DailyFlow24;
import com.reco.generate.entity.DailyFlow24Example;
import com.reco.generate.repository.DailyFlow24Mapper;
import com.reco.generate.service.DailyFlow24Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service(value = "dailyFlow24Service")
public class DailyFlow24ServiceImpl extends BaseServiceImpl<DailyFlow24, Integer, DailyFlow24Example, DailyFlow24Mapper> implements DailyFlow24Service {

    @Autowired
    @Override
    public void setDao(DailyFlow24Mapper dailyFlow24Mapper) {
        this.dao = dailyFlow24Mapper;
    }

    @Override
    public List<StatisticsBo> compileActivity(List<Integer> activityIdList, Date to) {
        return this.dao.compileActivity(activityIdList, to);
    }
}
