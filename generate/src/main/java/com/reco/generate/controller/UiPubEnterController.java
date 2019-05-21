package com.reco.generate.controller;

import com.reco.generate.core.AjaxResult;
import com.reco.generate.core.BaseController;
import com.reco.generate.entity.UiPubEnter;
import com.reco.generate.entity.UiPubEnterExample;
import com.reco.generate.service.UiPubEnterService;
import com.reco.generate.utils.Constant;
import com.reco.generate.utils.SSHUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/5/20 18:37
 * @Description: 双入口管理
 */
@RestController
@RequestMapping(value = "uiEnter")
public class UiPubEnterController extends BaseController<UiPubEnter, Integer, UiPubEnterExample, UiPubEnterService> {

    @Autowired
    @Override
    protected void setService(UiPubEnterService uiPubEnterService) {
        this.service = uiPubEnterService;
    }

    @ResponseBody
    @PostMapping(value = "updateUiEnter")
    public AjaxResult updateUI(UiPubEnter uiPubEnter, Boolean isUpload) {
        AjaxResult result = new AjaxResult(201, "更新失败");
        UiPubEnter old = this.service.findByPos(uiPubEnter.getPos());
        if (null != uiPubEnter && null != old) {
            UiPubEnterExample example = new UiPubEnterExample();
            example.createCriteria().andPosEqualTo(uiPubEnter.getPos());
            int count = this.service.updateByExampleSelective(uiPubEnter, example);
            Boolean putSuccess = true;
            // 上传文件
            if (count > 0 && isUpload) {
                File file = new File(Constant.getTempResourcePath() + "百灵/" + old.getPic());
                if (file.exists()) {
                    putSuccess = SSHUtils.putFile(Constant.getTempResourcePath() + "百灵/" + old.getPic(), Constant.getRemoteEnterIconPath());
                } else {
                    putSuccess = false;
                }
                if (putSuccess) {
                    result.setCode(200);
                    result.setMsg("更新成功");
                } else {
                    result.setCode(400);
                    result.setMsg("更新成功，图片上传失败");
                }
            }
        }
        return result;
    }
}
