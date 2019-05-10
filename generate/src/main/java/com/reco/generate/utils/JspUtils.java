package com.reco.generate.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.reco.generate.bo.HtmlTarget;
import com.reco.generate.bo.JavaTarget;
import com.reco.generate.bo.PageNodeStyle;
import com.reco.generate.bo.PageTarget;
import com.reco.generate.bo.enumEntity.EndTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class JspUtils {

    private static Logger logger = LoggerFactory.getLogger(JspUtils.class);

    /**
     * 创建JSP文件
     *
     * @param fileName
     * @param title
     * @param songMap
     * @param btnNodeMap
     * @param nodeStyleList
     * @param active
     */
    public static void createLocalTempFile(String fileName, String title, Map<String, Integer> songMap, Map<String, Integer> btnNodeMap, List<PageNodeStyle> nodeStyleList, String active) {
        String pageStr = autoGenerateJSP(title, songMap, btnNodeMap, nodeStyleList, active);
        File file = new File(Constant.getTempPath() + fileName);
        FileWriter fileWriter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file);
            fileWriter.append(pageStr);
            fileWriter.flush();
            logger.info("========= jsp自动生成完毕 ==========");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fileWriter) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成JSP文件内容
     *
     * @param title         jsp标题
     * @param songMap       歌曲
     * @param btnNodeMap    按钮节点
     * @param nodeStyleList 歌曲及按钮节点样式
     */
    private static String autoGenerateJSP(String title, Map<String, Integer> songMap, Map<String, Integer> btnNodeMap, List<PageNodeStyle> nodeStyleList, String active) {
        StringBuilder html = new StringBuilder();
        // 编译器指令
        JavaTarget compiler = getPageTarget();
        // 声明
        JavaTarget statement = getStateTarget();
        // 程序代码段
        JavaTarget javaCode = getJavaCodeTarget(title, songMap, btnNodeMap.values().size());
        // 页面代码
        HtmlTarget htmlContent = getHtmlRootTarget(title, btnNodeMap, nodeStyleList, active);
        html.append(compiler.toString()).append("\n");
        html.append(statement.toString()).append("\n");
        html.append(javaCode.toString()).append("\n");
        html.append(htmlContent.toString());
        return html.toString();
    }

    /**
     * 编译器指令
     *
     * @return JavaTarget
     */
    private static JavaTarget getPageTarget() {
        JavaTarget compiler = new JavaTarget();
        compiler.setName("@page");
        Map<String, String> compilerMap = Maps.newLinkedHashMap();
        compilerMap.put("language", "java");
        compilerMap.put("import", "java.util.*,com.rainbow.api.*,org.apache.commons.lang3.StringUtils");
        compilerMap.put("pageEncoding", "GBK");
        compiler.setAttributeMap(compilerMap);
        return compiler;
    }

    /**
     * 方法声明
     *
     * @return JavaTarget
     */
    private static JavaTarget getStateTarget() {
        JavaTarget state = new JavaTarget();
        state.setName("!");
        StringBuilder text = new StringBuilder();
        text.append("\n\tprivate List<Map<String, Object>> getSongByLast(int[] ids) {\n");
        text.append("\t\tString idt = \"\";\n");
        text.append("\t\tfor(int i = 0; i < ids.length; i++){\n");
        text.append("\t\t\tif(i == ids.length - 1) idt += ids[i];\n");
        text.append("\t\t\telse idt += ids[i] + \",\";\n");
        text.append("\t\t}\n");
        text.append("\t\tList<Map<String, Object>> li = null;\n");
        text.append("\t\tString sql = \"SELECT * FROM song WHERE id IN (\" + idt + \") ORDER BY FIND_IN_SET(id,'\" + idt + \"')\";\n");
        text.append("\t\tli = DB.query(sql, false);\n");
        text.append("\t\treturn li;\n");
        text.append("\t}\n");
        state.setText(text.toString());
        return state;
    }

    /**
     * java程序代码
     *
     * @param title        标题
     * @param songMap      歌曲及收费类型
     * @param btnNodeCount 按钮数量
     * @return JavaTarget
     */
    private static JavaTarget getJavaCodeTarget(String title, Map<String, Integer> songMap, Integer btnNodeCount) {
        JavaTarget javaCode = new JavaTarget();
        StringBuilder text = new StringBuilder();
        text.append("\n\t// 配置歌曲ID的地方和配置歌曲是否收费的地方 fee(1:收费点播按钮 0:免费点播按钮 -1:其它按钮)\n");
        text.append("\t// 需要修改的部分\n");
        StringBuilder ids = new StringBuilder();
        StringBuilder fees = new StringBuilder();
        for (String key : songMap.keySet()) {
            ids.append(key).append(",");
            fees.append(songMap.get(key)).append(",");
        }
        for (int i = 0; i < btnNodeCount; i++) {
            ids.append("0");
            fees.append("-1");
            if (i < btnNodeCount - 1) {
                ids.append(",");
                fees.append(",");
            }
        }
        text.append("\tint[] ids = {").append(ids).append("};\n");
        text.append("\tint[] fee = {").append(fees).append("};\n");
        text.append("\tString userid = DoParam.Analysis(\"globle\", \"userid\", request);\n");
        text.append("\tString uri = request.getRequestURI();\n");
        text.append("\turi = uri.substring(uri.lastIndexOf(\"/\") + 1);\n");
        text.append("\tString abbr = uri.replace(\".jsp\", \"\");\n");
        text.append("\tString speTime = abbr.split(\"_\")[0];\n");
        text.append("\tString speCont = abbr.split(\"_\")[1];\n");
        text.append("\tString isMobile = StringUtils.isBlank(DoParam.Analysis(\"globle\", \"equipment\", request)) ? \"tv\" : DoParam.Analysis(\"globle\", \"equipment\", request);\n");
        text.append("\tint platform = Integer.parseInt(StringUtils.isBlank(DoParam.Analysis(\"globle\", \"platform\", request)) ? \"0\" : DoParam.Analysis(\"globle\", \"platform\", request));\n");
        text.append("\tList<Map<String, Object>> li0 = getSongByLast(ids);\n");
        text.append("\tList<Map<String, Object>> li = new ArrayList<Map<String, Object>>();\n");
        text.append("\tint docNum = 0;\n");
        text.append("\tfor(int i = 0; i < fee.length; i++){\n");
        text.append("\t\tif(fee[i] == -1){\n");
        text.append("\t\t\tMap<String, Object> map = new HashMap<String, Object>();\n");
        text.append("\t\t\tmap.put(\"resid\", \"-\");\n");
        text.append("\t\t\tmap.put(\"csort\", \"0\");\n");
        text.append("\t\t\tmap.put(\"clanguage\", \"-\");\n");
        text.append("\t\t\tmap.put(\"cline\", \"0\");\n");
        text.append("\t\t\tmap.put(\"dtime\", \"-\");\n");
        text.append("\t\t\tmap.put(\"id_artist\", \"-\");\n");
        text.append("\t\t\tmap.put(\"cname_len\", \"-\");\n");
        text.append("\t\t\tmap.put(\"abbr\", \"-\");\n");
        text.append("\t\t\tmap.put(\"vtype\", \"-\");\n");
        text.append("\t\t\tmap.put(\"pic\", \"-\");\n");
        text.append("\t\t\tmap.put(\"cartist\", \"-\");\n");
        text.append("\t\t\tmap.put(\"id\", \"0\");\n");
        text.append("\t\t\tmap.put(\"cname\", \"---- 功能按钮占位\");\n");
        text.append("\t\t\tli.add(map);\n");
        text.append("\t\t} else {\n");
        text.append("\t\t\tMap<String, Object> map = li0.get(docNum);\n");
        text.append("\t\t\tli.add(map);\n");
        text.append("\t\t\tdocNum++;\n");
        text.append("\t\t}\n");
        text.append("\t}\n");
        text.append("\tString pageids_arr = \"\";\n");
        text.append("\tif(li0.size() > 0){\n");
        text.append("\t\tfor(int i = 0; i < li0.size(); i++){\n");
        text.append("\t\t\tif(i == li0.size() - 1) pageids_arr += li0.get(i).get(\"id\").toString();\n");
        text.append("\t\t\telse pageids_arr += li0.get(i).get(\"id\").toString() + \"|\";\n");
        text.append("\t\t}\n");
        text.append("\t}\n");
        text.append("\n");
        text.append("\tString spr = \">\";\n");
        text.append("\tString cname = \"专题_" + title + "\";\n");
        text.append("\tString fromPid = DoParam.Analysis(\"globle\", \"lastPage\", request);\n");
        text.append("\tString fromPage = fromPid;\n");
        text.append("\tif(!fromPage.contains(cname)) fromPage = fromPid + spr + cname;\n");
        javaCode.setText(text.toString());
        return javaCode;
    }

    /**
     * 构造Html根节点
     *
     * @param title         标题
     * @param btnNodeMap    按钮节点
     * @param nodeStyleList 节点样式
     * @return HtmlTarget
     */
    private static HtmlTarget getHtmlRootTarget(String title, Map<String, Integer> btnNodeMap, List<PageNodeStyle> nodeStyleList, String active) {
        HtmlTarget html = new HtmlTarget();
        html.setName("html");
        html.setLevel(0);
        List<PageTarget> children = Lists.newArrayList();
        children.add(getHeadTarget(title, nodeStyleList.size(), btnNodeMap, active));
        children.add(getBodyTarget(nodeStyleList));
        html.setChildrenList(children);
        html.setEndType(EndTypeEnum.END_WITH_TARGET);
        return html;
    }

    /**
     * 构造页面Head节点
     *
     * @param title        标题
     * @param allNodeCount 节点数量
     * @param btnNodeMap   按钮节点
     * @return HtmlTarget
     */
    private static HtmlTarget getHeadTarget(String title, Integer allNodeCount, Map<String, Integer> btnNodeMap, String active) {
        HtmlTarget head = new HtmlTarget();
        head.setName("head");
        head.setLevel(1);
        head.setEndType(EndTypeEnum.END_WITH_TARGET);
        List<PageTarget> children = Lists.newArrayList();

        // title
        HtmlTarget titleHtml = initSimpleHtmlTarget("title", 2, title, null, EndTypeEnum.END_WITH_TARGET, null);

        // meta
        Map<String, String> metaAttr = Maps.newHashMap();
        metaAttr.put("http-equiv", "Content-Type");
        metaAttr.put("content", "text/html; charset=gb2312");
        HtmlTarget meta1 = initSimpleHtmlTarget("meta", 2, null, metaAttr, EndTypeEnum.NO_END, null);

        metaAttr = Maps.newHashMap();
        metaAttr.put("name", "page-view-size");
        metaAttr.put("content", "1280*720");
        HtmlTarget meta2 = initSimpleHtmlTarget("meta", 2, null, metaAttr, EndTypeEnum.NO_END, null);

        metaAttr = Maps.newHashMap();
        metaAttr.put("http-equiv", "pragma");
        metaAttr.put("content", "no-cache");
        HtmlTarget meta3 = initSimpleHtmlTarget("meta", 2, null, metaAttr, EndTypeEnum.NO_END, null);

        metaAttr = Maps.newHashMap();
        metaAttr.put("http-equiv", "cache-control");
        metaAttr.put("content", "no-store,no-cache,must-revalidate");
        HtmlTarget meta4 = initSimpleHtmlTarget("meta", 2, null, metaAttr, EndTypeEnum.NO_END, null);

        metaAttr = Maps.newHashMap();
        metaAttr.put("http-equiv", "expires");
        metaAttr.put("content", "0");
        HtmlTarget meta5 = initSimpleHtmlTarget("meta", 2, null, metaAttr, EndTypeEnum.NO_END, null);

        // link
        Map<String, String> linkAttr = Maps.newHashMap();
        linkAttr.put("rel", "shortcut icon");
        linkAttr.put("href", initSimpleJavaTarget("=", "request.getContextPath()", null).toString() + "/images/HD/common/favicon.ico");
        linkAttr.put("type", "image/x-icon");
        HtmlTarget link = initSimpleHtmlTarget("link", 2, null, linkAttr, EndTypeEnum.NO_END, null);

        // style
        StringBuilder styleContent = new StringBuilder();
        Map<String, String> styleAttr = Maps.newHashMap();
        styleAttr.put("type", "text/css");
        styleContent.append("\n\t\t\t* {margin:0px;padding:0px;}\n");
        styleContent.append("\t\t\tbody {width:1280px;height:720px;margin-top:0px;margin-left:0px;font-size:20px}\n");
        HtmlTarget style = initSimpleHtmlTarget("style", 2, styleContent.toString(), styleAttr, EndTypeEnum.END_WITH_TARGET, 2);

        // script
        List<HtmlTarget> scriptList = Lists.newArrayList();
        List<String> jsList = Lists.newArrayList("common.js", "jsdata.js", "focus.js", "key.js", "clock.js");
        Map<String, String> scriptAttr = null;
        HtmlTarget script = null;
        for (String js : jsList) {
            scriptAttr = Maps.newLinkedHashMap();
            scriptAttr.put("type", "text/javascript");
            scriptAttr.put("src", "javascript/" + js + "?r=" + initSimpleJavaTarget("=", "Math.random()", null).toString());
            script = initSimpleHtmlTarget("script", 2, null, scriptAttr, EndTypeEnum.END_WITH_TARGET, null);
            scriptList.add(script);
        }

        scriptAttr = Maps.newLinkedHashMap();
        scriptAttr.put("type", "text/javascript");
        StringBuilder text = new StringBuilder();
        text.append("\n\t\t\tvar jsdata = \"order in <%=speTime %> as <%=speCont %>\"; // 订购来源详情\n");
        text.append("\t\t\tvar userid = \"<%=userid %>\"; // 用户ID\n");
        text.append("\t\t\tvar vtype = 1; // 歌曲的播放类型\n");
        text.append("\t\t\tvar nowAct = 0; // 0:基本按钮处理     其他:计费部分的按钮\n");
        text.append("\t\t\tvar needload = 1; // 需要loading图片\n");
        text.append("\t\t\tvar playType = 0; // 0:传统系列播放器  | 1:随机播放器\n");
        text.append("\t\t\tvar id_content = 0;\n");
        text.append("\t\t\t// 以上七个是每个计费活动必带上的全局变量\n");
        text.append("\t\t\tvar songIds = [ // 歌曲类型 | 歌曲id | 是否收费 <%for(int i = 0; i < li.size(); i++){int is1 = Integer.parseInt(li.get(i).get(\"csort\").toString());int is2 = Integer.parseInt(li.get(i).get(\"cline\").toString());%>\n");
        text.append("\t\t\t\t{'isline':'<%=(is1 > 0 && (is2 == platform || is2 == 3)) ? \"y\" : \"n\" %>', 'type':'<%=li.get(i).get(\"vtype\") %>', 'id':'<%=li.get(i).get(\"id\") %>', 'isfee':'<%=fee[i] == 1 ? \"y\" : \"n\" %>'}, // <%=li.get(i).get(\"cname\") %><%}%>\n");
        text.append("\t\t\t];\n");
        text.append("\t\t\tvar nowF = \"ele1\"; // 默认焦点\n");
        text.append("\t\t\tvar pageids_arr = \"<%=pageids_arr %>\"; // 歌曲ids\n");
        text.append("\t\t\tvar timeID; // 滚动计时器\n");
        text.append("\t\t\tvar loadStime; // 状态栏计时器\n");
        text.append("\n");
        text.append("\t\t\t// 按键事件\n");
        if (StringUtils.equalsIgnoreCase(active, "test")) {
            text.append("\t\t\twindow.document.onkeydown = document.onirkeypress = function(event) {\n");
        } else {
            text.append("\t\t\twindow.document.onkeypress = document.onirkeypress = function(event) {\n");
        }
        text.append("\t\t\t\tevent = event ? event : window.event;\n");
        text.append("\t\t\t\tvar keyCode = event.which ? event.which : event.keyCode;\n");
        text.append("\t\t\t\tvar keyEvent = get_key_event(keyCode);\n");
        text.append("\n");
        text.append("\t\t\t\tif(nowAct == 0){\n");
        text.append("\t\t\t\t\toperaClock();\n");
        text.append("\t\t\t\t\tif(keyCode == 8 || keyCode == 24 || keyCode == 32){\n");
        text.append("\t\t\t\t\t\tnowLoad();\n");
        text.append("\t\t\t\t\t\tsetTimeout(\"__return()\", 500);\n");
        text.append("\t\t\t\t\t\treturn false;\n");
        text.append("\t\t\t\t\t} else if(keyCode == 37){ // 左\n");
        text.append("\t\t\t\t\t\tmove_center('moveL');\n");
        text.append("\t\t\t\t\t\treturn false;\n");
        text.append("\t\t\t\t\t} else if(keyCode == 38){ // 上\n");
        text.append("\t\t\t\t\t\tmove_center('moveU');\n");
        text.append("\t\t\t\t\t\treturn false;\n");
        text.append("\t\t\t\t\t} else if(keyCode == 39){ // 右\n");
        text.append("\t\t\t\t\t\tmove_center('moveR');\n");
        text.append("\t\t\t\t\t\treturn false;\n");
        text.append("\t\t\t\t\t} else if(keyCode == 40){ // 下\n");
        text.append("\t\t\t\t\t\tmove_center('moveD');\n");
        text.append("\t\t\t\t\t\treturn false;\n");
        text.append("\t\t\t\t\t} else if(keyCode == 13){\n");
        text.append("\t\t\t\t\t\tmove_center('ok');\n");
        text.append("\t\t\t\t\t}\n");
        text.append("\t\t\t\t} else if(nowAct == 1){ // 计费按确认\n");
        text.append("\t\t\t\t\ttry{\n");
        text.append("\t\t\t\t\t\teval(getCurZone() + \"_move('\" + keyEvent + \"')\");\n");
        text.append("\t\t\t\t\t} catch(e){\n");
        text.append("\t\t\t\t\t\teval(\"__move('\" + keyEvent + \"')\");\n");
        text.append("\t\t\t\t\t}\n");
        text.append("\t\t\t\t}\n");
        text.append("\t\t\t};");
        text.append("\n");
        text.append("\n\t\t\t// 需要修改的部分\n");
        text.append("\t\t\tfunction move_center(dir){\n");
        text.append("\t\t\t\tif(dir == 'moveL'){\n");
        text.append(getMoveFunction(allNodeCount, "L"));
        text.append("\t\t\t\t} else if(dir == 'moveU'){\n");
        text.append(getMoveFunction(allNodeCount, "U"));
        text.append("\t\t\t\t} else if(dir == 'moveR'){\n");
        text.append(getMoveFunction(allNodeCount, "R"));
        text.append("\t\t\t\t} else if(dir == 'moveD'){\n");
        text.append(getMoveFunction(allNodeCount, "D"));
        text.append("\t\t\t\t} else if(dir == 'ok'){\n");
        text.append("\t\t\t\t\tdoclick();\n");
        text.append("\t\t\t\t}\n");
        text.append("\t\t\t}");
        text.append("\n");

        // doclick方法
        text.append(getClickFunction(btnNodeMap));

        text.append("\t\t\tfunction showStatus(stime){\n");
        text.append("\t\t\t\tif(loadStime >= 0){\n");
        text.append("\t\t\t\t\tclearTimeout(loadStime);\n");
        text.append("\t\t\t\t\tloadStime = -1;\n");
        text.append("\t\t\t\t}\n");
        text.append("\t\t\t\t$(\"status\").style.display = \"block\";\n");
        text.append("\t\t\t\t//延时关闭\n");
        text.append("\t\t\t\tif(!stime) stime = 5000;\n");
        text.append("\t\t\t\tif(!loadStime || loadStime < 0){\n");
        text.append("\t\t\t\t\tloadStime = setTimeout(function(){\n");
        text.append("\t\t\t\t\t\t$(\"status\").style.display = \"none\";\n");
        text.append("\t\t\t\t\t\tclearTimeout(loadStime);\n");
        text.append("\t\t\t\t\t\tloadStime = -1;\n");
        text.append("\t\t\t\t\t}, stime);\n");
        text.append("\t\t\t\t}\n");
        text.append("\t\t\t}\n");
        text.append("\n");
        text.append("\t\t\t// 处理去往别的页面时候返回到该页面的参数,从而选中对应按钮\n");
        text.append("\t\t\tfunction getInfo(){\n");
        text.append("\t\t\t\tvar nowInfo = '{\"back\":[{\"target\":\"<%=uri %>\"},{\"params\":\"0\"},{\"zone\":\"0\"},{\"node\":\"' + nowF + '\"}]}';\n");
        text.append("\t\t\treturn nowInfo;\n");
        text.append("\t\t\t}\n");
        text.append("\n");
        text.append("\t\t\tfunction start(){\n");
        text.append("\t\t\t\tif(getVal('state', 'cur_node')){\n");
        text.append("\t\t\t\t\tnowF = getVal('state', 'cur_node');\n");
        text.append("\t\t\t\t}\n");
        text.append("\t\t\t\t$(nowF).style.visibility = \"visible\";\n");
        text.append("\t\t\t}\n");

        text.append("\t\t\t<%if(isMobile.equals(\"mobile\")){%>\n");
        text.append("\t\t\tfunction displayFlag(){\n");
        text.append("\t\t\t\tif($('keyboard').style.display == 'block'){\n");
        text.append("\t\t\t\t\t$('keyboard').style.display = 'none';\n");
        text.append("\t\t\t\t\t$('flagKg').style.background = 'red';\n");
        text.append("\t\t\t\t} else{\n");
        text.append("\t\t\t\t\t$('keyboard').style.display = 'block';\n");
        text.append("\t\t\t\t\t$('flagKg').style.background = 'green';\n");
        text.append("\t\t\t\t}\n");
        text.append("\t\t\t}<%}%>\n");

        script = initSimpleHtmlTarget("script", 2, text.toString(), scriptAttr, EndTypeEnum.END_WITH_TARGET, 2);
        scriptList.add(script);

        children.add(titleHtml);
        children.add(meta1);
        children.add(meta2);
        children.add(meta3);
        children.add(meta4);
        children.add(meta5);
        children.add(link);
        children.add(style);
        children.addAll(scriptList);
        head.setChildrenList(children);
        head.setEndIndentCount(1);
        return head;
    }

    /**
     * 构造页面body节点
     *
     * @param nodeStyleList 节点样式
     * @return HtmlTarget
     */
    private static HtmlTarget getBodyTarget(List<PageNodeStyle> nodeStyleList) {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("onload", "start()");
        map.put("bgcolor", "transparent");
        HtmlTarget body = initSimpleHtmlTarget("body", 1, null, map, EndTypeEnum.END_WITH_TARGET, 1);

        List<PageTarget> children = Lists.newArrayList();
        map = Maps.newLinkedHashMap();
        map.put("id", "tips");
        map.put("style", "position:absolute;left:5px;top:-40px;width:1270px;height:40px;color:#000000;font-size:22px;background:url(images/HD/common/blank.png);text-align:center");
        HtmlTarget target = initSimpleHtmlTarget("div", 2, null, map, EndTypeEnum.END_WITH_TARGET, null);
        children.add(target);

        map = Maps.newLinkedHashMap();
        map.put("src", "images/HD/activities/<%=abbr %>/bj.jpg");
        map.put("style", "position:absolute;left:0px;top:0px;width:1280px;height:720px;z-index:-1");
        target = initSimpleHtmlTarget("img", 2, null, map, EndTypeEnum.NO_END, null);
        children.add(target);

        map = Maps.newLinkedHashMap();
        map.put("id", "status");
        map.put("src", "images/HD/common/eles/offline.png");
        map.put("style", "position:absolute;left:526px;top:560px;width:228px;height:50px;z-index:20;display:none");
        target = initSimpleHtmlTarget("img", 2, null, map, EndTypeEnum.NO_END, null);
        children.add(target);

        for (int i = 0; i < nodeStyleList.size(); i++) {
            map = Maps.newLinkedHashMap();
            map.put("id", "ele" + (i + 1));
            map.put("src", "images/HD/activities/<%=abbr %>/" + nodeStyleList.get(i).getImgName());
            map.put("style", "position:absolute;left:" + nodeStyleList.get(i).getLeft() + "px;top:" + nodeStyleList.get(i).getTop() + "px;width:" + nodeStyleList.get(i).getWidth() + "px;height:" + nodeStyleList.get(i).getHeight() + "px;z-index:3;visibility:hidden");
            target = initSimpleHtmlTarget("img", 2, null, map, EndTypeEnum.NO_END, null);
            children.add(target);
        }

        JavaTarget javaIfTarget = initSimpleJavaTarget("if", "(isMobile.equals(\"mobile\")){", 2);
        children.add(javaIfTarget);

        map = Maps.newLinkedHashMap();
        map.put("id", "flagKg");
        map.put("type", "button");
        map.put("onclick", "displayFlag()");
        map.put("style", "position:absolute;left:1230px;top:670px;width:40px;height:40px;z-index:10001;border:none;background:green");
        target = initSimpleHtmlTarget("input", 2, null, map, EndTypeEnum.END_WITH_TERMINATOR, null);
        children.add(target);

        map = Maps.newLinkedHashMap();
        map.put("id", "keyboard");
        map.put("style", "display:block");
        target = initSimpleHtmlTarget("div", 2, null, map, EndTypeEnum.END_WITH_TARGET, 2);
        List<PageTarget> secChildren = Lists.newArrayList();

        map = Maps.newLinkedHashMap();
        map.put("style", "position:absolute;left:10px;top:209.5px;width:288px;height:281px;z-index:9999;background:url(images/HD/common/admin/dt.png) no-repeat");
        HtmlTarget secChild = initSimpleHtmlTarget("div", 3, null, map, EndTypeEnum.END_WITH_TARGET, null);
        secChildren.add(secChild);

        map = Maps.newLinkedHashMap();
        map.put("type", "button");
        map.put("onclick", "operaClock();nowLoad();setTimeout('__return()', 500);");
        map.put("style", "position:absolute;left:1163px;top:306.5px;width:107px;height:107px;z-index:10000;border:none;background:url(images/HD/common/admin/rt.png) no-repeat");
        secChild = initSimpleHtmlTarget("input", 3, null, map, EndTypeEnum.END_WITH_TERMINATOR, null);
        secChildren.add(secChild);

        map = Maps.newLinkedHashMap();
        map.put("type", "button");
        map.put("onclick", "operaClock();move_center('moveL')");
        map.put("style", "position:absolute;left:29px;top:262.5px;width:77px;height:176px;z-index:10001;border:none;background:url(images/HD/common/admin/left.png) no-repeat");
        secChild = initSimpleHtmlTarget("input", 3, null, map, EndTypeEnum.END_WITH_TERMINATOR, null);
        secChildren.add(secChild);

        map = Maps.newLinkedHashMap();
        map.put("type", "button");
        map.put("onclick", "operaClock();move_center('moveU')");
        map.put("style", "position:absolute;left:68px;top:224.5px;width:175px;height:77px;z-index:10000;border:none;background:url(images/HD/common/admin/up.png) no-repeat");
        secChild = initSimpleHtmlTarget("input", 3, null, map, EndTypeEnum.END_WITH_TERMINATOR, null);
        secChildren.add(secChild);

        map = Maps.newLinkedHashMap();
        map.put("type", "button");
        map.put("onclick", "operaClock();move_center('moveR')");
        map.put("style", "position:absolute;left:205px;top:263.5px;width:76px;height:175px;z-index:10002;border:none;background:url(images/HD/common/admin/right.png) no-repeat");
        secChild = initSimpleHtmlTarget("input", 3, null, map, EndTypeEnum.END_WITH_TERMINATOR, null);
        secChildren.add(secChild);

        map = Maps.newLinkedHashMap();
        map.put("type", "button");
        map.put("onclick", "operaClock();move_center('moveD')");
        map.put("style", "position:absolute;left:68px;top:399.5px;width:174px;height:79px;z-index:10003;border:none;background:url(images/HD/common/admin/down.png) no-repeat");
        secChild = initSimpleHtmlTarget("input", 3, null, map, EndTypeEnum.END_WITH_TERMINATOR, null);
        secChildren.add(secChild);

        map = Maps.newLinkedHashMap();
        map.put("type", "button");
        map.put("onclick", "operaClock();move_center('ok')");
        map.put("style", "position:absolute;left:89px;top:285.5px;width:137px;height:136px;z-index:10004;border:none;background:url(images/HD/common/admin/ok.png) no-repeat");
        secChild = initSimpleHtmlTarget("input", 3, null, map, EndTypeEnum.END_WITH_TERMINATOR, null);
        secChildren.add(secChild);

        target.setChildrenList(secChildren);
        children.add(target);

        JavaTarget javaEndTarget = initSimpleJavaTarget(null, "}", 2);
        children.add(javaEndTarget);

        body.setChildrenList(children);
        return body;
    }

    /**
     * 构造简单HTML节点
     *
     * @param name  节点名称
     * @param level 级别
     * @param text  节点内容
     * @return HtmlTarget
     */
    private static HtmlTarget initSimpleHtmlTarget(String name, Integer level, String text, Map<String, String> map, EndTypeEnum endType, Integer indentCount) {
        HtmlTarget target = new HtmlTarget();
        target.setName(name);
        target.setLevel(level);
        target.setEndType(endType);
        if (StringUtils.isNotBlank(text)) {
            target.setText(text);
        }
        if (null != map) {
            target.setAttributeMap(map);
        }
        if (null != indentCount) {
            target.setEndIndentCount(indentCount);
        }
        return target;
    }

    /**
     * 构造Java节点
     *
     * @param name 节点名称
     * @param text 节点内容
     * @return JavaTarget
     */
    private static JavaTarget initSimpleJavaTarget(String name, String text, Integer level) {
        JavaTarget target = new JavaTarget();
        target.setName(name);
        target.setText(text);
        if (null != level) {
            target.setLevel(level);
        }
        return target;
    }

    /**
     * 构造move方法
     *
     * @param nodeCount 节点数量
     * @param moveType  移动类型
     * @return String
     */
    private static String getMoveFunction(Integer nodeCount, String moveType) {
        StringBuilder function = new StringBuilder();
        switch (moveType) {
            case "R":
            case "D":
                for (int i = 0; i < nodeCount; i++) {
                    if (i == 0) {
                        function.append("\t\t\t\t\tif(nowF == \"ele" + (i + 1) + "\") {\n" +
                                "\t\t\t\t\t\t$(\"ele" + (i + 1) + "\").style.visibility = \"hidden\";\n" +
                                "\t\t\t\t\t\t$(\"ele" + (i + 2) + "\").style.visibility = \"visible\";\n" +
                                "\t\t\t\t\t\tnowF = \"ele" + (i + 2) + "\";\n" +
                                "\t\t\t\t\t}");
                    } else if (i == nodeCount - 1) {
                        function.append(" else if(nowF == \"ele" + (i + 1) + "\") {\n" +
                                "\t\t\t\t\t\t$(\"ele" + (i + 1) + "\").style.visibility = \"hidden\";\n" +
                                "\t\t\t\t\t\t$(\"ele" + (nodeCount - i) + "\").style.visibility = \"visible\";\n" +
                                "\t\t\t\t\t\tnowF = \"ele" + (nodeCount - i) + "\";\n" +
                                "\t\t\t\t\t}\n");
                    } else {
                        function.append(" else if(nowF == \"ele" + (i + 1) + "\") {\n" +
                                "\t\t\t\t\t\t$(\"ele" + (i + 1) + "\").style.visibility = \"hidden\";\n" +
                                "\t\t\t\t\t\t$(\"ele" + (i + 2) + "\").style.visibility = \"visible\";\n" +
                                "\t\t\t\t\t\tnowF = \"ele" + (i + 2) + "\";\n" +
                                "\t\t\t\t\t}");
                    }
                }
                break;
            case "L":
            case "U":
                for (int i = 0; i < nodeCount; i++) {
                    if (i == 0) {
                        function.append("\t\t\t\t\tif(nowF == \"ele" + (i + 1) + "\") {\n" +
                                "\t\t\t\t\t\t$(\"ele" + (i + 1) + "\").style.visibility = \"hidden\";\n" +
                                "\t\t\t\t\t\t$(\"ele" + Math.abs(nodeCount - i) + "\").style.visibility = \"visible\";\n" +
                                "\t\t\t\t\t\tnowF = \"ele" + Math.abs(nodeCount - i) + "\";\n" +
                                "\t\t\t\t\t}");
                    } else if (i == nodeCount - 1) {
                        function.append(" else if(nowF == \"ele" + (i + 1) + "\") {\n" +
                                "\t\t\t\t\t\t$(\"ele" + (i + 1) + "\").style.visibility = \"hidden\";\n" +
                                "\t\t\t\t\t\t$(\"ele" + i + "\").style.visibility = \"visible\";\n" +
                                "\t\t\t\t\t\tnowF = \"ele" + i + "\";\n" +
                                "\t\t\t\t\t}\n");
                    } else {
                        function.append(" else if(nowF == \"ele" + (i + 1) + "\") {\n" +
                                "\t\t\t\t\t\t$(\"ele" + (i + 1) + "\").style.visibility = \"hidden\";\n" +
                                "\t\t\t\t\t\t$(\"ele" + i + "\").style.visibility = \"visible\";\n" +
                                "\t\t\t\t\t\tnowF = \"ele" + i + "\";\n" +
                                "\t\t\t\t\t}");
                    }
                }
                break;
            default:
                break;
        }
        return function.toString();
    }

    /**
     * 构造click方法
     *
     * @param btnNodeMap 按钮节点
     * @return String
     */
    private static String getClickFunction(Map<String, Integer> btnNodeMap) {
        StringBuilder function = new StringBuilder();
        List<Integer> values = Lists.newArrayList(btnNodeMap.values());
        function.append("\t\t\t// 需要修改的部分\n");
        function.append("\t\t\tfunction doclick(){\n");
        function.append("\t\t\t\tif(");
        for (int i = 0; i < values.size(); i++) {
            function.append("nowF != \"ele" + values.get(i) + "\"");
            if (i != values.size() - 1) {
                function.append(" && ");
            }
        }
        function.append("){\n");
        function.append("\t\t\t\t\tvar tempF = nowF.substr(3, nowF.length - 1);\n");
        function.append("\t\t\t\t\ttempF = tempF - 1;\n");
        function.append("\t\t\t\t\tvar isline = songIds[tempF].isline;\n");
        function.append("\t\t\t\t\tif(isline == \"y\"){ // 上线的歌曲\n");
        function.append("\t\t\t\t\t\tvar param = songIds[tempF].id;\n");
        function.append("\t\t\t\t\t\tvtype = songIds[tempF].type;\n");
        function.append("\t\t\t\t\t\tisfee = songIds[tempF].isfee;\n");
        function.append("\t\t\t\t\t\tnowLoad();\n");
        function.append("\t\t\t\t\t\tif(isfee == \"y\"){\n");
        function.append("\t\t\t\t\t\t\tjsdata = \"order with song \" + param + \" in <%=speTime %> as <%=speCont %>\";\n");
        function.append("\t\t\t\t\t\t\tsetTimeout(\"AJAX_addSong(\" + param + \")\", 1000);\n");
        function.append("\t\t\t\t\t\t} else{\n");
        function.append("\t\t\t\t\t\t\tvar jsonR = (getVal(\"globle\", \"return\") && getVal(\"globle\", \"return\") != \"\") ? eval('(' + getVal(\"globle\", \"return\") + ')') : new Array();\n");
        function.append("\t\t\t\t\t\t\tvar nowInfo = getInfo();\n");
        function.append("\t\t\t\t\t\t\tjsonR.push(eval('(' + nowInfo + ')'));\n");
        function.append("\t\t\t\t\t\t\tput(\"globle\", \"return\", jsonToStr(jsonR));\n");
        function.append("\t\t\t\t\t\t\tput(\"url\", \"params\", \"y=\" + param);\n");
        function.append("\t\t\t\t\t\t\tsetTimeout(\"round('playfree_\" + getVal(\"globle\", \"preferPlayer\") + \".jsp')\", 500);\n");
        function.append("\t\t\t\t\t\t}\n");
        function.append("\t\t\t\t\t} else{ // 专辑里的该歌曲已经被下线\n");
        function.append("\t\t\t\t\t\tshowStatus(2000);\n");
        function.append("\t\t\t\t\t}\n");
        function.append("\t\t\t\t} else {\n");
        function.append("\t\t\t\t\tif(nowF == \"ele" + btnNodeMap.get("backNode") + "\") { // 返回按钮\n");
        function.append("\t\t\t\t\t\tnowLoad();\n");
        function.append("\t\t\t\t\t\tsetTimeout(\"__return()\", 500);\n");
        if (null != btnNodeMap.get("playNode")) {
            function.append("\t\t\t\t\t} else if(nowF == \"ele" + btnNodeMap.get("playNode") + "\") { // 全部播放\n");
            function.append("\t\t\t\t\t\tnowLoad();\n");
            function.append("\t\t\t\t\t\tjsdata = \"order with all songs in <%=speTime %> as <%=speCont %>\";\n");
            function.append("\t\t\t\t\t\tvar r = new Date().getTime();\n");
            function.append("\t\t\t\t\t\tvar reqUrl = \"operaMount.jsp?u=\" + userid + \"&o=checked&p=\" + getVal('globle','platform') + \"&i=\" + pageids_arr + \"&r=\" + r;\n");
            function.append("\t\t\t\t\t\t$(\"opera\").src = reqUrl;\n");
            function.append("\t\t\t\t\t\treturn;\n");
            function.append("\t\t\t\t\t}");
        } else if (null != btnNodeMap.get("moreNode")) {
            function.append(" else if(nowF == \"ele" + btnNodeMap.get("moreNode") + "\") { // 更多精彩\n");
            function.append("\t\t\t\t\t\tput('state','cur_zone','index');\n");
            function.append("\t\t\t\t\t\tput('state','cur_node','index3');\n");
            function.append("\t\t\t\t\t\tput('url', 'params', 'i=1');\n");
            function.append("\t\t\t\t\t\tchange('main.jsp');");
            function.append("\t\t\t\t\t}");
        }
        function.append("\n");
        function.append("\t\t\t\t}\n");
        function.append("\t\t\t}\n\n");
        return function.toString();
    }
}
