package com.reco.generate.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.reco.generate.bo.PageNodeStyle;
import com.reco.generate.core.AjaxResult;
import com.reco.generate.entity.Activity;
import com.reco.generate.service.ActivityService;
import com.reco.generate.utils.DateUtils;
import com.reco.generate.utils.JspUtils;
import com.reco.generate.utils.SSHUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/autoDeploy")
public class AutoDeployController {

    @Autowired
    private ActivityService activityService;

    /**
     * 生成jsp并上传到服务器
     *
     * @param title
     * @param titleAbbr
     * @param active
     * @param ctype
     * @param songs
     * @param btns
     * @return
     */
    @ResponseBody
    @PostMapping(value = "start")
    public AjaxResult start(String title, String titleAbbr, String active, Integer ctype, String songs, String btns) {
        AjaxResult result = new AjaxResult();
        // jsp文件名称
        String tempFileName = "spe" + DateUtils.getDate("yyyyMMdd") + "_" + titleAbbr + ".jsp";
        // 歌曲(id、收费类型、节点坐标信息)
        Map<String, Integer> songMap = Maps.newLinkedHashMap();
        // 歌曲及按钮节点样式
        List<PageNodeStyle> nodeStyleList = Lists.newArrayList();
        if (StringUtils.isNotBlank(songs)) {
            JSONArray jsonArray = JSONArray.parseArray(songs);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                songMap.put(item.get("songId").toString(), item.getInteger("isFree"));
                nodeStyleList.add(JSONObject.parseObject(item.getJSONObject("nodeInfo").toJSONString(), PageNodeStyle.class));
            }
        }

        // 按钮及其顺序
        Map<String, Integer> btnNodeMap = Maps.newHashMap();
        if (StringUtils.isNotBlank(btns)) {
            JSONArray jsonArray = JSONArray.parseArray(btns);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                btnNodeMap.put(item.get("nodeType").toString(), item.getInteger("index"));
                nodeStyleList.add(JSONObject.parseObject(item.getJSONObject("nodeInfo").toJSONString(), PageNodeStyle.class));
            }
        }

        // 生成jsp文件
        JspUtils.createLocalTempFile(tempFileName, title, songMap, btnNodeMap, nodeStyleList, active);
        // 上传到服务器
        Boolean putResult = SSHUtils.putFile(tempFileName, 1);


        // 新增活动
        Activity activity = activityService.findByUrl(tempFileName);
        if(null == activity) {
            activity = new Activity();
            activity.setId(activityService.getMaxId() + 1);
            activity.setUrl(tempFileName);
            activity.setCname(title);
            activity.setPic(tempFileName.replaceAll("\\.jsp", "\\.png"));
            activity.setCtype(ctype);
            if(ctype != 0) {
                activity.setCsort(activityService.getMaxCsort(ctype) + 1);
            } else {
                activity.setCsort(0);
            }
            activity.setSocnew(titleAbbr);
            activity.setPos(3);
            activity.setCline(1);
            activity.setCtime(new Date());

            int affected = activityService.insert(activity);
            if (affected <= 0) {
                putResult = false;
            }
        }
        if (putResult) {
            result.setCode(200);
            result.setMsg("操作成功!");
        } else {
            result.setCode(201);
            result.setMsg("操作失败!");
        }

        return result;
    }
}
