<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import = "com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
<!DOCTYPE html>
<html>
<%
	String _contexPath = request.getContextPath().equals("/") ? ""
			: request.getContextPath();
	SysUserInfo user = (SysUserInfo) session.getAttribute("user");
	Map app = user.getApp();
%>
<head>
	<title>安全设备基线管理系统</title>
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<link rel="stylesheet" type="text/css" href="css/navigation.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/main.css" />
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/basic.css" />
	<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/style/app/css/app_main.css" />
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<script src="js/ueframe/main.js"></script>
	<script src="js/iframeAutoSize.js"></script>
	
	<script language="javascript">
		window.onresize = function() {
			setCenter(4, 80);
		}
		window.onload = function() {
			setCenter(4, 80);
		}
		
		//动态菜单JS
		navHover = function() {
    var lis = document.getElementById("navmenu-h").getElementsByTagName("LI");
	    for (var i=0; i<lis.length; i++) {
	        lis[i].onmouseover=function() {
	            this.className+=" iehover";
	        }
	        lis[i].onmouseout=function() {
	            this.className=this.className.replace(new RegExp(" iehover\\b"), "");
	        }
	    }
	}
	if (window.attachEvent) window.attachEvent("onload", navHover);
	</script>
	
</head>
<body class="easyui-layout">

		<div data-options="region:'north',border:false"
			style="height: 131px; width: 1270px;overflow:visible;">
			<div class="content_title_bg">
				<div class="content_title">
					<div style="float: left">
						欢迎 ${user.userName}！
					</div>
					<div style="float: right; padding-right: 10px">
						<a href="javascript:modifyselfmanager();">修改个人信息</a>&nbsp;&nbsp;
						<a href="javascript:logoutf();">注销</a>
					</div>
				</div>
			</div>
			<div class="about_title">
				安全设备基线管理系统
			</div>


			<div class="page_bar_bg">
				
							<div align="left" style="padding-left: 10px;">
								<ul id="navmenu-h">
									<%
										if (app.get("首页查看") != null) {
									%>
									<li>
										<a href="javascript:shouye();" class="easyui-linkbutton"
											data-options="plain:true">首页</a>
									</li>
									<%
										}
										if (app.get("设备管理菜单") != null) {
									%>
									<li>
										<a href="#" class="easyui-linkbutton"
											data-options="plain:true">设备管理</a>
										<ul>
											<%
												if (app.get("采集机查看") != null) {
											%>
											<li>
												<a href="javascript:mcamanager();" class="easyui-linkbutton"
													data-options="plain:true">采集机管理</a>
											</li>
											<%
												}
													if (app.get("查看厂商") != null) {
											%>
											<li>
												<a href="javascript:companymanager();"
													class="easyui-linkbutton" data-options="plain:true">厂商配置</a>
											</li>
											<%
												}
											%>
										</ul>
									</li>
									<%
										}
										if (app.get("规则配置管理菜单") != null) {
									%>
									<li>
										<a href="#" class="easyui-linkbutton"
											data-options="plain:true">规则管理</a>
										<ul>
											<%
												if (app.get("查看规则") != null) {
											%>
											<li>
												<a href="javascript:rulemanager();"
													class="easyui-linkbutton" data-options="plain:true">标准化规则配置</a>
											</li>
											<%
												}
											%>

										</ul>
									</li>
									<%
										}
										if (app.get("任务管理菜单") != null) {
									%>
									<li>
										<a href="#" class="easyui-linkbutton"
											data-options="plain:true">任务管理</a>
										<ul>
											<%
												if (app.get("任务展示") != null) {
											%>
											<li>
												<a href="javascript:taskmanager();"
													class="easyui-linkbutton" data-options="plain:true">任务展示</a>
											</li>
											<%
												}
											%>
											
										</ul>
									</li>
									<%
										}
										if (app.get("基线管理菜单") != null) {
									%>
									<li>
										<a href="#" class="easyui-linkbutton"
											data-options="plain:true">基线管理</a>
										<ul>
											<%
												if (app.get("基线查看") != null) {
											%>
											<li>
												<a href="javascript:baselinemanager();"
													class="easyui-linkbutton" data-options="plain:true">基线管理</a>
											</li>
											<%
												}
													if (app.get("基线模板查看") != null) {
											%>
											<li>
												<a href="javascript:baselinetemplatemanager();"
													class="easyui-linkbutton" data-options="plain:true">基线模板管理</a>
											</li>

											<%
												}
													if (app.get("基线比对查看") != null) {
											%>
											<li>
												<a href="javascript:matchscoremanager();"
													class="easyui-linkbutton" data-options="plain:true">基线比对结果</a>
											</li>
											<%
												}
											%>
										</ul>
									</li>
									<%
										}
										if (app.get("告警配置管理菜单") != null) {
									%>
									<li>
										<a href="#" class="easyui-linkbutton"
											data-options="plain:true">告警管理</a>
										<ul>
											<%
												if (app.get("查看事件类型") != null) {
											%>
											<li>
												<a href="javascript:eventmanager();"
													class="easyui-linkbutton" data-options="plain:true">事件类型管理</a>
											</li>
											<%
												}
													if (app.get("查看告警规则") != null) {
											%>
											<li>
												<a href="javascript:eventrulemanager();"
													class="easyui-linkbutton" data-options="plain:true">告警规则管理</a>
											</li>
											<%
												}
													if (app.get("查看指标") != null) {
											%>
											<li>
												<a href="javascript:kpimanager();" class="easyui-linkbutton"
													data-options="plain:true">指标管理</a>
											</li>
											<%
												}
											%>
										</ul>
									</li>
									<%
										}
										if (app.get("数据展示菜单") != null) {
									%>
									<li>
										<a href="#" class="easyui-linkbutton"
											data-options="plain:true">数据展示</a>
										<ul>
											<%
												if (app.get("防火墙配置查看") != null) {
											%>
											<li>
												<a href="javascript:filemanager();"
													class="easyui-linkbutton" data-options="plain:true">防火墙配置文件</a>
											</li>
											<%
												}
													if (app.get("配置文件比对") != null) {
											%>
											<li>
												<a href="javascript:configmatchmanager();"
													class="easyui-linkbutton" data-options="plain:true">配置文件比对</a>
											</li>
											<%
												}
													if (app.get("SYSLOG命中查看") != null) {
											%>
											<li>
												<a href="javascript:hitmanager();" class="easyui-linkbutton"
													data-options="plain:true">SYSLOG命中</a>
											</li>
											<%
												}
											%>
										</ul>
									</li>
									<%
										}
										if (app.get("系统管理菜单") != null || user.getAccount().equals("admin")) {
									%>
									<li>
										<a href="#" class="easyui-linkbutton"
											data-options="plain:true">系统管理</a>
										<ul>
											<%
												if (app.get("查看部门") != null
															|| user.getAccount().equals("admin")) {
											%>
											<li>
												<a href="javascript:orgmanager();" class="easyui-linkbutton"
													data-options="plain:true">部门管理</a>
											</li>
											<%
												}
													if (app.get("查看用户") != null
															|| user.getAccount().equals("admin")) {
											%>
											<li>
												<a href="javascript:usermanager();"
													class="easyui-linkbutton" data-options="plain:true">用户管理</a>
											</li>
											<%
												}
													if (app.get("查看角色") != null
															|| user.getAccount().equals("admin")) {
											%>
											<li>
												<a href="javascript:rolemanager();"
													class="easyui-linkbutton" data-options="plain:true">角色管理</a>
											</li>
											<%
												}
													if (app.get("密码策略") != null
															|| user.getAccount().equals("admin")) {
											%>
											<li>
												<a href="javascript:passwdrulemanager();"
													class="easyui-linkbutton" data-options="plain:true">密码策略</a>
											</li>
											<%
												}
													if (app.get("访问策略") != null
															|| user.getAccount().equals("admin")) {
											%>
											<li>
												<a href="javascript:accessrulemanager();"
													class="easyui-linkbutton" data-options="plain:true">访问策略</a>
											</li>
											<%
												}
													if (app.get("系统日志") != null) {
											%>
											<li>
												<a href="javascript:systemmanager();"
													class="easyui-linkbutton" data-options="plain:true">系统日志</a>
											</li>
											<%
												}
											%>
										</ul>
									</li>
									<%
										}
									%>

								</ul>
							</div>
					
			</div>
		</div>

		<div data-options="region:'west',split:true,title:'资源树'" style="width:200px;padding-left:10px;">
		<iframe src="tree/treeViewFrame.jsp" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
	</div>
	
	<div data-options="region:'center',title:''">
		<div id="aa" class="easyui-accordion" scrolling="false" data-options="fit:true,border:false,onSelect:ref" >
			
					<div class="content" title="主视图" data-options="selected:true">
						
						<div class="content_title_bg" style="height:7%;">
							<div id="operation" class="content_title">
								
							</div>
						</div>
						<div id="cont" style="overflow: hidden; width: 100%;height:93%;">

							<iframe target="contextMain" name="contextMain" id="contextMain" src="first.jsp" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>	
					
						</div>
					</div>
					
			
				<div title="告警视图"  style="padding:10px;">
					
					<iframe id="eventl" src="event/eventlist.jsp" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
					
				</div>
			</div>	
		
			
	</div>
	<script type="text/javascript">
	
		function ref(title,index){
			if(title=="告警视图"){
				window.frames.eventl.location.reload();
			}
			
		}
		function shouye(){
			window.frames.contextMain.location.href="first.jsp";
		}
		function companymanager(){
			window.frames.contextMain.location.href="devcompany/viewAllDevCompany.jsp";
		}
		function mcamanager(){
			window.frames.contextMain.location.href="resobj/viewMca.jsp";
		}
		function baselinetemplatemanager(){
			window.frames.contextMain.location.href="baseline/viewBaseLineTemplate.jsp";
		}
		function baselinemanager(){
			window.frames.contextMain.location.href="baseline/viewBaseLine.jsp";
		}
		function rulemanager(){
			window.frames.contextMain.location.href="rule/viewAllDevType.jsp";
		}
		function usermanager(){
			window.frames.contextMain.location.href="users/viewUser.jsp";
		}
		function rolemanager(){
			window.frames.contextMain.location.href="users/viewRole.jsp";
		}
		function orgmanager(){
			window.frames.contextMain.location.href="users/viewOrg.jsp";
		}
		function kpimanager(){
			window.frames.contextMain.location.href="resobj/viewKpiInfo.jsp";
		}
		function eventmanager(){
			window.frames.contextMain.location.href="event/viewEventType.jsp";
		}
		function eventrulemanager(){
			window.frames.contextMain.location.href="event/eventRule.jsp";
		}
		function matchscoremanager(){
			window.frames.contextMain.location.href="baseline/viewMatchScore.jsp";
		}
		function filemanager(){
			window.frames.contextMain.location.href="fwfile/viewFwFile.jsp";
		}
		function hitmanager(){
			window.frames.contextMain.location.href="syslog/viewSyslogHit.jsp";
		}
		function configmatchmanager(){
			window.frames.contextMain.location.href="resobj/configMatch.jsp";
		}
		function modifyselfmanager(){
			window.frames.contextMain.location.href="users/modifySelf.jsp";
		}
		function systemmanager(){
			window.frames.contextMain.location.href="log.jsp";
		}
		function taskmanager(){
			window.frames.contextMain.location.href="task/queryTaskView.jsp";
		}
		function taskexecmanager(){
			window.frames.contextMain.location.href="task/viewTaskExec.jsp";
		}
		function passwdrulemanager(){
			window.frames.contextMain.location.href="toModifyPasswdRule.action";
		}
		function accessrulemanager(){
			window.frames.contextMain.location.href="toModifyAccessRule.action";
		}
		function logoutf(){
				$.messager.confirm('操作提示', '你确认要退出系统吗?', function(r){
				if (r){
				  window.location.href="logout.action";
				 }else{
				 	
				 }
				   return;
				 });
		        
		}
		
	</script>
</body>
</html>
