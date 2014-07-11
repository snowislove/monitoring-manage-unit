<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String _contexPath = request.getContextPath().equals("/")? "": request.getContextPath();
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
<input type="hidden" id="resobj" value=""/>
			<table>
			<tr>
    		<td>
			    <lable>&nbsp;&nbsp;省份：</lable>
			    <input name="cityCode" id="cc1" class="easyui-combobox" editable="false" data-options=" valueField: 'id', textField: 'text', url: 'getAllCity.action', 
			
										onSelect: function(rec){ 
			
										var url = 'getCityByParent.action?cityCode='+rec.id; 
										$('#cc2').combobox('clear');
										$('#cc2').combobox('reload', url); 
			
									}" /> 
			</td>
			<td>
				<lable>&nbsp;&nbsp;单位：</lable>
				<input id="cc2" class="easyui-combobox" name="sbucityCode" editable="false" data-options="valueField:'id',textField:'text',
					onSelect: function(rec){ 
										var url = 'getResObjByCity.action?cityCode='+rec.id; 
										$('#cc3').combobox('clear');
										$('#cc3').combobox('reload', url); 
				}" />
			</td>
			<td>
				<lable>&nbsp;防火墙：</lable>
				<input id="cc3" class="easyui-combobox" name="resId" editable="false" data-options="valueField:'id',textField:'text',
					onSelect: function(rec){ 
							var resobj=document.getElementById('resobj');
							resobj.value=rec.id;
							
	            				$.post('viewTaskTaskScheduleAction.action', {resId:resobj.value},function(data){
			 						$('#listDetail').datagrid('loadData',data);
								});
							
				
				}" />
			</td>
		</table>
	<table id="listDetail"></table>
	<script type="text/javascript">
			$(function() {
				$("#listDetail").datagrid({
					width : 1000,
					heigth : '100%',
					idField : 'id',
					pageSize : 10,
					pageList : [10, 20, 50, 100],
					url : 'viewTaskTaskScheduleAction.action',
					// queryParams:{'resId':'<%=resId%>'},
					singleSelect : false,
					fitColumns : true,
					nowrap : true,
					loadMsg : '数据加载中,请稍后……',
					pagination : true,
					rownumbers : true,
					columns : [[ {
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
					toolbar : [{
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

					/*onBeforeRefresh:function(){

					 $(this).pagination('loading');

					 alert('before refresh');

					 $(this).pagination('loaded');

					 }*/

				});
			});
		</script>
</body>
</html>