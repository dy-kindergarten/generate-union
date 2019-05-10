package com.reco.generate.controller;

import com.reco.generate.core.AjaxResult;
import com.reco.generate.core.BaseController;
import com.reco.generate.entity.Activity;
import com.reco.generate.entity.UiPub;
import com.reco.generate.entity.UiPubExample;
import com.reco.generate.service.ActivityService;
import com.reco.generate.service.UiPubService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "ui")
public class UiPubController extends BaseController<UiPub, Integer, UiPubExample, UiPubService> {

    @Autowired
    private ActivityService activityService;

    @Autowired
    @Override
    protected void setService(UiPubService uiPubService) {
        this.service = uiPubService;
    }

    @ResponseBody
    @PostMapping(value = "updateUI")
    public AjaxResult updateUI(Integer pid, Integer nid, Integer activityId) {
        AjaxResult result = new AjaxResult(201, "更新失败");
        UiPub uiPub = this.service.findByUnionId(pid, nid);
        Activity activity = activityService.selectByPrimaryKey(activityId);
        if(null != uiPub && null != activity && StringUtils.isNotBlank(activity.getUrl())) {
            String url = activity.getUrl();
            String shortName = url.split("_")[1];
            uiPub.setCurl(url);
            uiPub.setPic(shortName.replaceAll("\\.jsp", ".png"));
            int count = this.service.updateByPrimaryKeySelective(uiPub);
            if(count > 0) {
                result.setCode(200);
                result.setMsg("更新成功");
            }
        }
        return result;
    }
}
