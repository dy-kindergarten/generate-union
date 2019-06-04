package com.reco.generate.service.impl;

import com.reco.generate.core.BaseServiceImpl;
import com.reco.generate.core.easyui.PageModel;
import com.reco.generate.entity.Activity;
import com.reco.generate.entity.ActivityExample;
import com.reco.generate.repository.ActivityMapper;
import com.reco.generate.service.ActivityService;
import com.reco.generate.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
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
    public PageModel<Activity> findByPage(Activity activity, PageModel<Activity> page) {
        page.setTotal(this.dao.getPageTotal(activity));
        List<Activity> list = this.dao.findByPage(activity);
        for (Activity entity : list) {
            entity.setCreateTime(DateUtils.getDate(entity.getCtime(), "yyyy-MM-dd HH:mm:ss"));
        }
        page.setRows(list);
        return page;
    }

    @Override
    public Activity findByUrl(String url) {
        ActivityExample example = new ActivityExample();
        example.createCriteria().andUrlEqualTo(url);
        List<Activity> list = this.dao.selectByExample(example);
        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<Activity> findByCname(String cname) {
        ActivityExample example = new ActivityExample();
        if (StringUtils.isNotBlank(cname)) {
            example.createCriteria().andCnameLike(cname + '%');
        }
        example.setOrderByClause("id desc");
        return this.dao.selectByExample(example);
    }

    @Override
    public List<Activity> findRecommend() {
        return this.dao.findRecommend();
    }
}
