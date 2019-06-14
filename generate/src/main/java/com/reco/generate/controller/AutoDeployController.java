package com.reco.generate.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.reco.generate.bo.PageNodeStyle;
import com.reco.generate.core.AjaxResult;
import com.reco.generate.entity.Activity;
import com.reco.generate.entity.Song;
import com.reco.generate.service.ActivityService;
import com.reco.generate.service.SongService;
import com.reco.generate.utils.Constant;
import com.reco.generate.utils.DateUtils;
import com.reco.generate.utils.JspUtils;
import com.reco.generate.utils.SSHUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("autoDeploy")
public class AutoDeployController {

    @Value("${spring.profiles.active}")
    private String active;

    @Autowired
    private ActivityService activityService;
    @Autowired
    private SongService songService;

    /**
     * 生成jsp并上传到服务器
     *
     * @param title
     * @param titleAbbr
     * @param active
     * @param ctype
     * @param csort
     * @param songs
     * @param btns
     * @param isp,      1-电信，2-广电
     * @return
     */
    @ResponseBody
    @PostMapping(value = "start")
    public AjaxResult start(String title, String titleAbbr, String active, Integer ctype, Integer csort, String songs, String btns, Integer isp) {
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
                btnNodeMap.put(item.get("nodeType").toString(), songMap.size() + i + 1);
                nodeStyleList.add(JSONObject.parseObject(item.getJSONObject("nodeInfo").toJSONString(), PageNodeStyle.class));
            }
        }

        // 生成jsp
        JspUtils.createLocalTempFile(tempFileName, title, songMap, btnNodeMap, nodeStyleList, active, isp);
        String localFilePath = Constant.getTempJspPath(active) + tempFileName;
        // 上传jsp
        Boolean putResult = SSHUtils.putFile(localFilePath, Constant.getRemoteJspPath());

        String localPath = Constant.getTempResourcePath() + DateUtils.getDate("yyyy-MM") + "\\" + title + "\\";
        // 上传图片
        Boolean putImgResult = SSHUtils.mkdirAndPutFile(localPath, Lists.newArrayList(getImgNames(nodeStyleList)), Constant.getRemoteActImgPath(), tempFileName.replaceAll("\\.jsp", ""));

        // 新增活动
        Activity activity = activityService.findByUrl(tempFileName);
        if (null == activity) {
            activity = new Activity();
            activity.setId(activityService.getMaxId() + 1);
            activity.setUrl(tempFileName);
            activity.setCname(title);
            activity.setPic(tempFileName.replaceAll("\\.jsp", "\\.png"));
            activity.setCtype(ctype);
            activity.setCsort(csort);
            activity.setSocnew(titleAbbr);
            activity.setPos(3);
            activity.setCline(1);
            activity.setCtime(new Date());

            int affected = activityService.insert(activity);
            if (affected <= 0) {
                putResult = false;
            }
        }
        if (putResult && putImgResult) {
            result.setCode(200);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("activityName", title);
            jsonObject.put("recommendUrl", Constant.getRecommendUrl() + titleAbbr);
            result.setData(jsonObject);
            result.setMsg("操作成功!");
        } else {
            result.setCode(201);
            result.setMsg("操作失败!");
        }
        return result;
    }

    /**
     * 新增歌曲测试页面
     *
     * @param ids
     * @param fees
     * @return
     */
    @ResponseBody
    @PostMapping(value = "songTest")
    public AjaxResult songTest(String ids, String fees) {
        if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(fees)) {
            List<String> idList = Lists.newArrayList(ids.split(","));
            List<String> feeList = Lists.newArrayList(fees.split(","));
            Map<String, String> songMap = Maps.newLinkedHashMap();
            int i = 0;
            for (String id : idList) {
                Song song = songService.selectByPrimaryKey(Integer.valueOf(id));
                songMap.put(id, song.getCname() + " - " + song.getCartist() + (1 == Integer.valueOf(feeList.get(i)).intValue() ? "" : "    免费"));
                i++;
            }
            // jsp文件名称
            String tempFileName = "";
            if (StringUtils.containsIgnoreCase(active, "sxgd")) {
                tempFileName = "songTest.jsp";
            } else {
                tempFileName = "songTest_" + DateUtils.getDate("yyyyMMdd") + ".jsp";
            }
            String localFilePath = Constant.getSongTestPath() + tempFileName;
            // 生成jsp
            JspUtils.createSongTestFile(localFilePath, ids, fees, songMap);
            // 上传jsp
            Boolean putResult = SSHUtils.putFile(localFilePath, Constant.getRemoteJspPath());

            // 新增活动
            Activity activity = activityService.selectByPrimaryKey(999);
            if (null == activity) {
                activity = new Activity();
                activity.setId(999);
                activity.setUrl(tempFileName);
                activity.setCname("歌曲测试");
                activity.setCtype(1);
                activity.setCsort(0);
                activity.setSocnew("songTest");
                activity.setPos(3);
                activity.setCline(1);
                activity.setCtime(new Date());
                activityService.insert(activity);
            } else {
                activity.setUrl(tempFileName);
                activity.setCtime(new Date());
                activityService.updateByPrimaryKeySelective(activity);
            }

            if (putResult) {
                return new AjaxResult(200, "操作成功!");
            }
        }
        return new AjaxResult(201, "操作失败!");
    }

    /**
     * 图片名称
     *
     * @param nodeStyleList
     * @return
     */
    private Set<String> getImgNames(List<PageNodeStyle> nodeStyleList) {
        Set<String> imgNames = Sets.newHashSet();
        imgNames.add("bj.jpg");
        for (PageNodeStyle style : nodeStyleList) {
            if (StringUtils.isNotBlank(style.getImgName())) {
                imgNames.add(style.getImgName());
            }
        }
        return imgNames;
    }
}
