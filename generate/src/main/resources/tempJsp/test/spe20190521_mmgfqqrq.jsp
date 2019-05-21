<%@page language="java" import="java.util.*,com.rainbow.api.*,org.apache.commons.lang3.StringUtils" pageEncoding="GBK"%>
<%!
	private List<Map<String, Object>> getSongByLast(int[] ids) {
		String idt = "";
		for(int i = 0; i < ids.length; i++){
			if(i == ids.length - 1) idt += ids[i];
			else idt += ids[i] + ",";
		}
		List<Map<String, Object>> li = null;
		String sql = "SELECT * FROM song WHERE id IN (" + idt + ") ORDER BY FIND_IN_SET(id,'" + idt + "')";
		li = DB.query(sql, false);
		return li;
	}
 %>
<%
	// 配置歌曲ID的地方和配置歌曲是否收费的地方 fee(1:收费点播按钮 0:免费点播按钮 -1:其它按钮)
	// 需要修改的部分
	int[] ids = {20198,20531,20162,16287,19615,16542,18981,19720,0,0};
	int[] fee = {0,1,1,1,1,1,1,1,-1,-1};
	String userid = DoParam.Analysis("globle", "userid", request);
	String uri = request.getRequestURI();
	uri = uri.substring(uri.lastIndexOf("/") + 1);
	String abbr = uri.replace(".jsp", "");
	String speTime = abbr.split("_")[0];
	String speCont = abbr.split("_")[1];
	String isMobile = StringUtils.isBlank(DoParam.Analysis("globle", "equipment", request)) ? "tv" : DoParam.Analysis("globle", "equipment", request);
	int platform = Integer.parseInt(StringUtils.isBlank(DoParam.Analysis("globle", "platform", request)) ? "0" : DoParam.Analysis("globle", "platform", request));
	List<Map<String, Object>> li0 = getSongByLast(ids);
	List<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
	int docNum = 0;
	for(int i = 0; i < fee.length; i++){
		if(fee[i] == -1){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resid", "-");
			map.put("csort", "0");
			map.put("clanguage", "-");
			map.put("cline", "0");
			map.put("dtime", "-");
			map.put("id_artist", "-");
			map.put("cname_len", "-");
			map.put("abbr", "-");
			map.put("vtype", "-");
			map.put("pic", "-");
			map.put("cartist", "-");
			map.put("id", "0");
			map.put("cname", "---- 功能按钮占位");
			li.add(map);
		} else {
			Map<String, Object> map = li0.get(docNum);
			li.add(map);
			docNum++;
		}
	}
	String pageids_arr = "";
	if(li0.size() > 0){
		for(int i = 0; i < li0.size(); i++){
			if(i == li0.size() - 1) pageids_arr += li0.get(i).get("id").toString();
			else pageids_arr += li0.get(i).get("id").toString() + "|";
		}
	}

	String spr = ">";
	String cname = "专题_渺渺国风，缱绻柔情";
	String fromPid = DoParam.Analysis("globle", "lastPage", request);
	String fromPage = fromPid;
	if(!fromPage.contains(cname)) fromPage = fromPid + spr + cname;
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>渺渺国风，缱绻柔情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<meta name="page-view-size" content="1280*720">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-store,no-cache,must-revalidate">
		<meta http-equiv="expires" content="0">
		<link rel="shortcut icon" href="<%=request.getContextPath() %>/images/HD/common/favicon.ico" type="image/x-icon">
		<style type="text/css">
			* {margin:0px;padding:0px;}
			body {width:1280px;height:720px;margin-top:0px;margin-left:0px;font-size:20px}
		</style>
		<script type="text/javascript" src="javascript/common.js?r=<%=Math.random() %>"></script>
		<script type="text/javascript" src="javascript/jsdata.js?r=<%=Math.random() %>"></script>
		<script type="text/javascript" src="javascript/focus.js?r=<%=Math.random() %>"></script>
		<script type="text/javascript" src="javascript/key.js?r=<%=Math.random() %>"></script>
		<script type="text/javascript" src="javascript/clock.js?r=<%=Math.random() %>"></script>
		<script type="text/javascript">
			var jsdata = "order in <%=speTime %> as <%=speCont %>"; // 订购来源详情
			var userid = "<%=userid %>"; // 用户ID
			var vtype = 1; // 歌曲的播放类型
			var nowAct = 0; // 0:基本按钮处理     其他:计费部分的按钮
			var needload = 1; // 需要loading图片
			var playType = 0; // 0:传统系列播放器  | 1:随机播放器
			var id_content = 0;
			// 以上七个是每个计费活动必带上的全局变量
			var songIds = [ // 歌曲类型 | 歌曲id | 是否收费 <%for(int i = 0; i < li.size(); i++){int is1 = Integer.parseInt(li.get(i).get("csort").toString());int is2 = Integer.parseInt(li.get(i).get("cline").toString());%>
				{'isline':'<%=(is1 > 0 && (is2 == platform || is2 == 3)) ? "y" : "n" %>', 'type':'<%=li.get(i).get("vtype") %>', 'id':'<%=li.get(i).get("id") %>', 'isfee':'<%=fee[i] == 1 ? "y" : "n" %>'}, // <%=li.get(i).get("cname") %><%}%>
			];
			var nowF = "ele1"; // 默认焦点
			var pageids_arr = "<%=pageids_arr %>"; // 歌曲ids
			var timeID; // 滚动计时器
			var loadStime; // 状态栏计时器

			// 按键事件
			window.document.onkeydown = document.onirkeypress = function(event) {
				event = event ? event : window.event;
				var keyCode = event.which ? event.which : event.keyCode;
				var keyEvent = get_key_event(keyCode);

				if(nowAct == 0){
					operaClock();
					if(keyCode == 8 || keyCode == 24 || keyCode == 32){
						nowLoad();
						setTimeout("__return()", 500);
						return false;
					} else if(keyCode == 37){ // 左
						move_center('moveL');
						return false;
					} else if(keyCode == 38){ // 上
						move_center('moveU');
						return false;
					} else if(keyCode == 39){ // 右
						move_center('moveR');
						return false;
					} else if(keyCode == 40){ // 下
						move_center('moveD');
						return false;
					} else if(keyCode == 13){
						move_center('ok');
					}
				} else if(nowAct == 1){ // 计费按确认
					try{
						eval(getCurZone() + "_move('" + keyEvent + "')");
					} catch(e){
						eval("__move('" + keyEvent + "')");
					}
				}
			};

			// 需要修改的部分
			function move_center(dir){
				if(dir == 'moveL'){
					if(nowF == "ele1") {
						$("ele1").style.visibility = "hidden";
						$("ele10").style.visibility = "visible";
						nowF = "ele10";
					} else if(nowF == "ele2") {
						$("ele2").style.visibility = "hidden";
						$("ele1").style.visibility = "visible";
						nowF = "ele1";
					} else if(nowF == "ele3") {
						$("ele3").style.visibility = "hidden";
						$("ele2").style.visibility = "visible";
						nowF = "ele2";
					} else if(nowF == "ele4") {
						$("ele4").style.visibility = "hidden";
						$("ele3").style.visibility = "visible";
						nowF = "ele3";
					} else if(nowF == "ele5") {
						$("ele5").style.visibility = "hidden";
						$("ele4").style.visibility = "visible";
						nowF = "ele4";
					} else if(nowF == "ele6") {
						$("ele6").style.visibility = "hidden";
						$("ele5").style.visibility = "visible";
						nowF = "ele5";
					} else if(nowF == "ele7") {
						$("ele7").style.visibility = "hidden";
						$("ele6").style.visibility = "visible";
						nowF = "ele6";
					} else if(nowF == "ele8") {
						$("ele8").style.visibility = "hidden";
						$("ele7").style.visibility = "visible";
						nowF = "ele7";
					} else if(nowF == "ele9") {
						$("ele9").style.visibility = "hidden";
						$("ele8").style.visibility = "visible";
						nowF = "ele8";
					} else if(nowF == "ele10") {
						$("ele10").style.visibility = "hidden";
						$("ele9").style.visibility = "visible";
						nowF = "ele9";
					}
				} else if(dir == 'moveU'){
					if(nowF == "ele1") {
						$("ele1").style.visibility = "hidden";
						$("ele10").style.visibility = "visible";
						nowF = "ele10";
					} else if(nowF == "ele2") {
						$("ele2").style.visibility = "hidden";
						$("ele1").style.visibility = "visible";
						nowF = "ele1";
					} else if(nowF == "ele3") {
						$("ele3").style.visibility = "hidden";
						$("ele2").style.visibility = "visible";
						nowF = "ele2";
					} else if(nowF == "ele4") {
						$("ele4").style.visibility = "hidden";
						$("ele3").style.visibility = "visible";
						nowF = "ele3";
					} else if(nowF == "ele5") {
						$("ele5").style.visibility = "hidden";
						$("ele4").style.visibility = "visible";
						nowF = "ele4";
					} else if(nowF == "ele6") {
						$("ele6").style.visibility = "hidden";
						$("ele5").style.visibility = "visible";
						nowF = "ele5";
					} else if(nowF == "ele7") {
						$("ele7").style.visibility = "hidden";
						$("ele6").style.visibility = "visible";
						nowF = "ele6";
					} else if(nowF == "ele8") {
						$("ele8").style.visibility = "hidden";
						$("ele7").style.visibility = "visible";
						nowF = "ele7";
					} else if(nowF == "ele9") {
						$("ele9").style.visibility = "hidden";
						$("ele8").style.visibility = "visible";
						nowF = "ele8";
					} else if(nowF == "ele10") {
						$("ele10").style.visibility = "hidden";
						$("ele9").style.visibility = "visible";
						nowF = "ele9";
					}
				} else if(dir == 'moveR'){
					if(nowF == "ele1") {
						$("ele1").style.visibility = "hidden";
						$("ele2").style.visibility = "visible";
						nowF = "ele2";
					} else if(nowF == "ele2") {
						$("ele2").style.visibility = "hidden";
						$("ele3").style.visibility = "visible";
						nowF = "ele3";
					} else if(nowF == "ele3") {
						$("ele3").style.visibility = "hidden";
						$("ele4").style.visibility = "visible";
						nowF = "ele4";
					} else if(nowF == "ele4") {
						$("ele4").style.visibility = "hidden";
						$("ele5").style.visibility = "visible";
						nowF = "ele5";
					} else if(nowF == "ele5") {
						$("ele5").style.visibility = "hidden";
						$("ele6").style.visibility = "visible";
						nowF = "ele6";
					} else if(nowF == "ele6") {
						$("ele6").style.visibility = "hidden";
						$("ele7").style.visibility = "visible";
						nowF = "ele7";
					} else if(nowF == "ele7") {
						$("ele7").style.visibility = "hidden";
						$("ele8").style.visibility = "visible";
						nowF = "ele8";
					} else if(nowF == "ele8") {
						$("ele8").style.visibility = "hidden";
						$("ele9").style.visibility = "visible";
						nowF = "ele9";
					} else if(nowF == "ele9") {
						$("ele9").style.visibility = "hidden";
						$("ele10").style.visibility = "visible";
						nowF = "ele10";
					} else if(nowF == "ele10") {
						$("ele10").style.visibility = "hidden";
						$("ele1").style.visibility = "visible";
						nowF = "ele1";
					}
				} else if(dir == 'moveD'){
					if(nowF == "ele1") {
						$("ele1").style.visibility = "hidden";
						$("ele2").style.visibility = "visible";
						nowF = "ele2";
					} else if(nowF == "ele2") {
						$("ele2").style.visibility = "hidden";
						$("ele3").style.visibility = "visible";
						nowF = "ele3";
					} else if(nowF == "ele3") {
						$("ele3").style.visibility = "hidden";
						$("ele4").style.visibility = "visible";
						nowF = "ele4";
					} else if(nowF == "ele4") {
						$("ele4").style.visibility = "hidden";
						$("ele5").style.visibility = "visible";
						nowF = "ele5";
					} else if(nowF == "ele5") {
						$("ele5").style.visibility = "hidden";
						$("ele6").style.visibility = "visible";
						nowF = "ele6";
					} else if(nowF == "ele6") {
						$("ele6").style.visibility = "hidden";
						$("ele7").style.visibility = "visible";
						nowF = "ele7";
					} else if(nowF == "ele7") {
						$("ele7").style.visibility = "hidden";
						$("ele8").style.visibility = "visible";
						nowF = "ele8";
					} else if(nowF == "ele8") {
						$("ele8").style.visibility = "hidden";
						$("ele9").style.visibility = "visible";
						nowF = "ele9";
					} else if(nowF == "ele9") {
						$("ele9").style.visibility = "hidden";
						$("ele10").style.visibility = "visible";
						nowF = "ele10";
					} else if(nowF == "ele10") {
						$("ele10").style.visibility = "hidden";
						$("ele1").style.visibility = "visible";
						nowF = "ele1";
					}
				} else if(dir == 'ok'){
					doclick();
				}
			}
			// 需要修改的部分
			function doclick(){
				if(nowF != "ele10" && nowF != "ele9"){
					var tempF = nowF.substr(3, nowF.length - 1);
					tempF = tempF - 1;
					var isline = songIds[tempF].isline;
					if(isline == "y"){ // 上线的歌曲
						var param = songIds[tempF].id;
						vtype = songIds[tempF].type;
						isfee = songIds[tempF].isfee;
						nowLoad();
						if(isfee == "y"){
							jsdata = "order with song " + param + " in <%=speTime %> as <%=speCont %>";
							setTimeout("AJAX_addSong(" + param + ")", 1000);
						} else{
							var jsonR = (getVal("globle", "return") && getVal("globle", "return") != "") ? eval('(' + getVal("globle", "return") + ')') : new Array();
							var nowInfo = getInfo();
							jsonR.push(eval('(' + nowInfo + ')'));
							put("globle", "return", jsonToStr(jsonR));
							put("url", "params", "y=" + param);
							setTimeout("round('playfree_" + getVal("globle", "preferPlayer") + ".jsp')", 500);
						}
					} else{ // 专辑里的该歌曲已经被下线
						showStatus(2000);
					}
				} else {
					if(nowF == "ele10") { // 返回按钮
						nowLoad();
						setTimeout("__return()", 500);
					} else if(nowF == "ele9") { // 全部播放
						nowLoad();
						jsdata = "order with all songs in <%=speTime %> as <%=speCont %>";
						var r = new Date().getTime();
						var reqUrl = "operaMount.jsp?u=" + userid + "&o=checked&p=" + getVal('globle','platform') + "&i=" + pageids_arr + "&r=" + r;
						$("opera").src = reqUrl;
						return;
					}
				}
			}

			function showStatus(stime){
				if(loadStime >= 0){
					clearTimeout(loadStime);
					loadStime = -1;
				}
				$("status").style.display = "block";
				//延时关闭
				if(!stime) stime = 5000;
				if(!loadStime || loadStime < 0){
					loadStime = setTimeout(function(){
						$("status").style.display = "none";
						clearTimeout(loadStime);
						loadStime = -1;
					}, stime);
				}
			}

			// 处理去往别的页面时候返回到该页面的参数,从而选中对应按钮
			function getInfo(){
				var nowInfo = '{"back":[{"target":"<%=uri %>"},{"params":"0"},{"zone":"0"},{"node":"' + nowF + '"}]}';
			return nowInfo;
			}

			function start(){
				if(getVal('state', 'cur_node')){
					nowF = getVal('state', 'cur_node');
				}
				$(nowF).style.visibility = "visible";
			}
			<%if(isMobile.equals("mobile")){%>
			function displayFlag(){
				if($('keyboard').style.display == 'block'){
					$('keyboard').style.display = 'none';
					$('flagKg').style.background = 'red';
				} else{
					$('keyboard').style.display = 'block';
					$('flagKg').style.background = 'green';
				}
			}<%}%>
		</script>
	</head>
	<body onload="start()" bgcolor="transparent">
		<div id="tips" style="position:absolute;left:5px;top:-40px;width:1270px;height:40px;color:#000000;font-size:22px;background:url(images/HD/common/blank.png);text-align:center"></div>
		<img src="images/HD/activities/<%=abbr %>/bj.jpg" style="position:absolute;left:0px;top:0px;width:1280px;height:720px;z-index:-1">
		<img id="status" src="images/HD/common/eles/offline.png" style="position:absolute;left:526px;top:560px;width:228px;height:50px;z-index:20;display:none">
		<img id="ele1" src="images/HD/activities/<%=abbr %>/a.png" style="position:absolute;left:528px;top:372px;width:181px;height:87px;z-index:3;visibility:hidden">
		<img id="ele2" src="images/HD/activities/<%=abbr %>/a.png" style="position:absolute;left:713px;top:372px;width:181px;height:87px;z-index:3;visibility:hidden">
		<img id="ele3" src="images/HD/activities/<%=abbr %>/a.png" style="position:absolute;left:898px;top:372px;width:181px;height:87px;z-index:3;visibility:hidden">
		<img id="ele4" src="images/HD/activities/<%=abbr %>/a.png" style="position:absolute;left:1083px;top:372px;width:181px;height:87px;z-index:3;visibility:hidden">
		<img id="ele5" src="images/HD/activities/<%=abbr %>/a.png" style="position:absolute;left:528px;top:480px;width:181px;height:87px;z-index:3;visibility:hidden">
		<img id="ele6" src="images/HD/activities/<%=abbr %>/a.png" style="position:absolute;left:713px;top:480px;width:181px;height:87px;z-index:3;visibility:hidden">
		<img id="ele7" src="images/HD/activities/<%=abbr %>/a.png" style="position:absolute;left:898px;top:480px;width:181px;height:87px;z-index:3;visibility:hidden">
		<img id="ele8" src="images/HD/activities/<%=abbr %>/a.png" style="position:absolute;left:1083px;top:480px;width:181px;height:87px;z-index:3;visibility:hidden">
		<img id="ele9" src="images/HD/activities/<%=abbr %>/b.png" style="position:absolute;left:668px;top:646px;width:131px;height:37px;z-index:3;visibility:hidden">
		<img id="ele10" src="images/HD/activities/<%=abbr %>/b.png" style="position:absolute;left:896px;top:646px;width:131px;height:37px;z-index:3;visibility:hidden">
		<%if(isMobile.equals("mobile")){ %>
		<input id="flagKg" type="button" onclick="displayFlag()" style="position:absolute;left:1230px;top:670px;width:40px;height:40px;z-index:10001;border:none;background:green" />
		<div id="keyboard" style="display:block">
			<div style="position:absolute;left:10px;top:209.5px;width:288px;height:281px;z-index:9999;background:url(images/HD/common/admin/dt.png) no-repeat"></div>
			<input type="button" onclick="operaClock();nowLoad();setTimeout('__return()', 500);" style="position:absolute;left:1163px;top:306.5px;width:107px;height:107px;z-index:10000;border:none;background:url(images/HD/common/admin/rt.png) no-repeat" />
			<input type="button" onclick="operaClock();move_center('moveL')" style="position:absolute;left:29px;top:262.5px;width:77px;height:176px;z-index:10001;border:none;background:url(images/HD/common/admin/left.png) no-repeat" />
			<input type="button" onclick="operaClock();move_center('moveU')" style="position:absolute;left:68px;top:224.5px;width:175px;height:77px;z-index:10000;border:none;background:url(images/HD/common/admin/up.png) no-repeat" />
			<input type="button" onclick="operaClock();move_center('moveR')" style="position:absolute;left:205px;top:263.5px;width:76px;height:175px;z-index:10002;border:none;background:url(images/HD/common/admin/right.png) no-repeat" />
			<input type="button" onclick="operaClock();move_center('moveD')" style="position:absolute;left:68px;top:399.5px;width:174px;height:79px;z-index:10003;border:none;background:url(images/HD/common/admin/down.png) no-repeat" />
			<input type="button" onclick="operaClock();move_center('ok')" style="position:absolute;left:89px;top:285.5px;width:137px;height:136px;z-index:10004;border:none;background:url(images/HD/common/admin/ok.png) no-repeat" />
		</div>
		<%} %>
	</body>
</html>