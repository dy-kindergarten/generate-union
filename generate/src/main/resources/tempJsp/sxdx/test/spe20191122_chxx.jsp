<%@page language="java" import="java.util.*,com.rainbow.api.*,org.apache.commons.lang3.StringUtils" pageEncoding="GBK"%>
<%
	// 配置歌曲ID的地方和配置歌曲是否收费的地方 fee(1:收费点播按钮 0:免费点播按钮 -1:其它按钮)
	// 需要修改的部分
	int[] fee = {-1,-1};
	String userid = DoParam.Analysis("globle", "userid", request);
	String uri = request.getRequestURI();
	uri = uri.substring(uri.lastIndexOf("/") + 1);
	String abbr = uri.replace(".jsp", "");
	String speTime = abbr.split("_")[0];
	String speCont = abbr.split("_")[1];
	String isMobile = StringUtils.isBlank(DoParam.Analysis("globle", "equipment", request)) ? "tv" : DoParam.Analysis("globle", "equipment", request);
	List<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
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
		}
	}
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>彩虹下线</title>
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
			var nowF = "ele1"; // 默认焦点
			var timeID; // 滚动计时器
			var loadStime; // 状态栏计时器

			// 按键事件
			window.document.onkeydown = document.onirkeypress = function(event) {
				event = event ? event : window.event;
				var keyCode = event.which ? event.which : event.keyCode;

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
						return false;
					} else if(keyCode == 39){ // 右
						move_center('moveR');
						return false;
					} else if(keyCode == 40){ // 下
						return false;
					} else if(keyCode == 13){
						move_center('ok');
					}
				}
			};

			// 需要修改的部分
			function move_center(dir){
				if(dir == 'moveL'){
					if(nowF == "ele1") {
						$("ele1").style.visibility = "hidden";
						$("ele2").style.visibility = "visible";
						nowF = "ele2";
					} else if(nowF == "ele2") {
						$("ele2").style.visibility = "hidden";
						$("ele1").style.visibility = "visible";
						nowF = "ele1";
					}
				} else if(dir == 'moveR'){
					if(nowF == "ele1") {
						$("ele1").style.visibility = "hidden";
						$("ele2").style.visibility = "visible";
						nowF = "ele2";
					} else if(nowF == "ele2") {
						$("ele2").style.visibility = "hidden";
						$("ele1").style.visibility = "visible";
						nowF = "ele1";
					}
				} else if(dir == 'ok'){
					doclick();
				}
			}
			// 需要修改的部分
			function doclick() {
				if(nowF == "ele1") {
					nowLoad();
					setTimeout("__return()", 500);
					return false;
				} else {
					var epgInfo = decodeURIComponent(GetCookie("epgInfo"));
					var back_url = window.location.href;
					window.location.href = "http://113.136.207.48:8080/sxdx/show/welcome.htm?epg_info=" + epgInfo + "&user_name=" + userid + "&userId="+ userid + "&backUrl=" + back_url;
				}
			}

			function showStatus(stime) {
				if(loadStime >= 0) {
					clearTimeout(loadStime);
					loadStime = -1;
				}
				$("status").style.display = "block";
				//延时关闭
				if(!stime) stime = 5000;
				if(!loadStime || loadStime < 0) {
					loadStime = setTimeout(function() {
						$("status").style.display = "none";
						clearTimeout(loadStime);
						loadStime = -1;
					}, stime);
				}
			}

			// 处理去往别的页面时候返回到该页面的参数,从而选中对应按钮
			function getInfo() {
				var nowInfo = '{"back":[{"target":"<%=uri %>"},{"params":"0"},{"zone":"0"},{"node":"' + nowF + '"}]}';
				return nowInfo;
			}

			function start() {
				if(getVal('state', 'cur_node')) {
					nowF = getVal('state', 'cur_node');
				}
				$(nowF).style.visibility = "visible";
			}
			<%if(isMobile.equals("mobile")) {%>
			function displayFlag() {
				if($('keyboard').style.display == 'block') {
					$('keyboard').style.display = 'none';
					$('flagKg').style.background = 'red';
				} else {
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
		<img id="ele1" src="images/HD/activities/<%=abbr %>/a.png" style="position:absolute;left:284px;top:558px;width:324px;height:90px;z-index:3;visibility:hidden">
		<img id="ele2" src="images/HD/activities/<%=abbr %>/a.png" style="position:absolute;left:674px;top:558px;width:324px;height:90px;z-index:3;visibility:hidden">
		<%if(isMobile.equals("mobile")){ %>
		<input id="flagKg" type="button" onclick="displayFlag()" style="position:absolute;left:1230px;top:670px;width:40px;height:40px;z-index:10001;border:none;background:green" />
		<div id="keyboard" style="display:block">
			<div style="position:absolute;left:10px;top:209.5px;width:288px;height:281px;z-index:9999;background:url(images/HD/common/admin/dt.png) no-repeat"></div>
			<input type="button" onclick="operaClock();nowLoad();setTimeout('__return()', 500);" style="position:absolute;left:1163px;top:306.5px;width:107px;height:107px;z-index:10000;border:none;background:url(images/HD/common/admin/rt.png) no-repeat" />
			<input type="button" onclick="operaClock();move_center('moveL')" style="position:absolute;left:29px;top:262.5px;width:77px;height:176px;z-index:10001;border:none;background:url(images/HD/common/admin/left.png) no-repeat" />
			<input type="button" onclick="operaClock();move_center('moveR')" style="position:absolute;left:205px;top:263.5px;width:76px;height:175px;z-index:10002;border:none;background:url(images/HD/common/admin/right.png) no-repeat" />
			<input type="button" onclick="operaClock();move_center('ok')" style="position:absolute;left:89px;top:285.5px;width:137px;height:136px;z-index:10004;border:none;background:url(images/HD/common/admin/ok.png) no-repeat" />
		</div>
		<%} %>
	</body>
</html>