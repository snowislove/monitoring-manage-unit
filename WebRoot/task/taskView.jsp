<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import = "com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String _contexPath = request.getContextPath().equals("/")? "": request.getContextPath();
	SysUserInfo user=(SysUserInfo)session.getAttribute("user");
	Map app=user.getApp();
	String oldResId=request.getParameter("resId");
	String resId="";
	if(oldResId!=null&&!oldResId.trim().equals("")){
		if(oldResId.contains("_"))
		resId=oldResId.split("_")[1];
		else
		resId=oldResId;
	}
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>任务列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css" href="css/easyui.css">
<link rel="stylesheet" type="text/css" href="css/icon.css">
<link rel="stylesheet" type="text/css" href="css/demo.css">
<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="任务管理>任务列表";
		function mcapaused(url){
			window.location.href=url;
		}
		function toViewKpiValue(url){
			window.location.href=url;
		}
	</script>
</head>
<body>
	<table id="listDetail"></table>
	<script type="text/javascript">
			$(function() {
				$("#listDetail").datagrid({
					width : '100%',
					heigth : '100%',
					idField : 'id',
					pageSize : 10,
					pageList : [10, 20, 50, 100],
					url : 'viewTaskTaskScheduleAction.action',
					 queryParams:{'resId':'<%=resId%>'},
					singleSelect : false,
					fitColumns : true,
					nowrap : true,
					loadMsg : '数据加载中,请稍后……',
					pagination : true,
					rownumbers : true,
					columns : [[{
						field : 'id',
						checkbox : true
					}, {
						field : 'tid',
						title : '任务标识',
						width : 200,
						editor : 'text',
						sortable : true
					}, {
						field : 'reg',
						title : '省市县',
						width : 100,
						editor : 'text'
					}, {
						field : 'cat',
						title : '创建时间',
						width : 100,
						editor : 'text'
					}, {
						field : 'ope',
						title : '任务操作',
						width : 100,
						editor : 'text'
					}, {
						field : 'tip',
						title : '采集IP',
						width : 150,
						editor : 'text'
					}, {
						field : 'tpt',
						title : '采集端口',
						width : 100,
						editor : 'text'
					}, {
						field : 'sch',
						title : '调度规则',
						width : 80,
						editor : 'text'
					}]],
					toolbar : [
					<% if(app.get("添加任务")!=null){ %>
					{
						text : '添加任务',
						iconCls : 'icon-add',
						handler : function() {
							window.location.href = "<%=_contexPath%>/task/taskSave.jsp?operationType=new&resId=<%=resId%>";
						}
					}, '-', 
					<% }if(app.get("删除任务")!=null){ %>
					{
						text : '删除任务',
						iconCls : 'icon-remove',
						handler : function() {
							var rows = $('#listDetail').datagrid('getSelections');
								if (null == rows || rows.length == 0) {
									 $.messager.alert("错误提示", "未选择条目！", "info");
									return;
								}
							$.messager.confirm('操作提示', '你确认要删除吗?', function(r){
							if (r){ 
								var removeIds = "";
								for (var i = 0; i < rows.length; i++) {
									if (i == (rows.length - 1)) {
										removeIds += rows[i].tid;
									} else {
										removeIds = removeIds + rows[i].tid + ",";
									}
								}
								window.location.href = "removeTaskScheduleAction.action?tids="+removeIds+"&resId=<%=resId%>";
							 } });
							

						}
					}, '-', 
					<% }if(app.get("修改任务")!=null){ %>
					{
						text : '修改任务',
						iconCls : 'icon-edit',
						handler : function() {
							var rows = $('#listDetail').datagrid('getSelections');
							if (null == rows || rows.length == 0) {
								
								$.messager.alert("错误提示", "请选择需要修改的一个任务！", "info");
								return;
							}
							if (rows.length > 1) {
								
								$.messager.alert("错误提示", "请选择单个任务进行修改！", "info");
								return;
							}
							window.location.href = "<%=_contexPath%>/task/taskSave.jsp?operationType=update&tid="+rows[0].tid;
						}
					},  '-', 
					<%}%>
					{
						text : '刷新',
						iconCls : 'icon-reload',
						handler : function() {
							$('#listDetail').datagrid('reload');
						}
					}]

				});
				$('#listDetail').datagrid("getPager").pagination({
					displayMsg : '当前显示从{from}到{to}共{total}记录',
					onBeforeRefresh : function(pageNumber, pageSize) {
						$(this).pagination('loading');

						$(this).pagination('loaded');
					}
				});

				$('#listDetail').datagrid("getPager").pagination({

					pageSize : 10, //每页显示的记录条数，默认为10

					pageList : [10, 20, 50, 100], //可以设置每页记录条数的列表

					beforePageText : '第', //页数文本框前显示的汉字

					afterPageText : '页    共 {pages} 页',

					displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'

					

				});
			});
		</script>
</body>
</html>