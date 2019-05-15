package com.reco.generate.controller;

import com.reco.generate.core.AjaxResult;
import com.reco.generate.core.BaseController;
import com.reco.generate.entity.Activity;
import com.reco.generate.entity.UiPub;
import com.reco.generate.entity.UiPubExample;
import com.reco.generate.service.ActivityService;
import com.reco.generate.service.UiPubService;
import com.reco.generate.utils.Constant;
import com.reco.generate.utils.DateUtils;
import com.reco.generate.utils.SSHUtils;
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
        if (null != uiPub && null != activity && StringUtils.isNotBlank(activity.getUrl())) {
            String url = activity.getUrl();
            String shortName = url.split("_")[1];
            uiPub.setCurl(url);
            uiPub.setPic(shortName.replaceAll("\\.jsp", ".png"));
            UiPubExample example = new UiPubExample();
            example.createCriteria().andPidEqualTo(uiPub.getPid()).andNidEqualTo(uiPub.getNid());
            int count = this.service.updateByExampleSelective(uiPub, example);

            // 上传文件
            Boolean putSuccess = SSHUtils.putFile(Constant.getTempResourcePath() + DateUtils.getDate("yyyy-MM") + activity.getCname() + "\\" + activity.getSocnew() + ".png", Constant.getRemoteActIconPath());
            Boolean execSuccess = false;
            if ((uiPub.getPid() == 30000 && uiPub.getNid() == 3) || (uiPub.getPid() == 30002 && uiPub.getNid() == 19)) {
                execSuccess = SSHUtils.putFile(Constant.getTempResourcePath() + DateUtils.getDate("yyyy-MM") + activity.getCname() + "\\f_" + activity.getSocnew() + ".png", Constant.getRemoteActFiconPath());
            } else {
                // 执行命令
                String command = "cp " + Constant.getRemoteActFiconPath() + "f_" + uiPub.getPic() + " " + Constant.getRemoteActFiconPath() + "f_" + activity.getSocnew() + ".png ";
                execSuccess = SSHUtils.execCommand(command);
            }
            if (putSuccess && execSuccess && count > 0) {
                result.setCode(200);
                result.setMsg("更新成功");
            }
        }
        return result;
    }
}
