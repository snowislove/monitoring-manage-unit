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
		adiv.innerText="任务管理>任务执行情况";
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
				<input id="cc2" class="easyui-combobox"  name="sbucityCode" editable="false" data-options="valueField:'id',textField:'text',
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
				}" />
			</td>
			</tr>
			<tr>
			<td>选择日期从: <input id="dt1" class="easyui-datetimebox" style="width:150px"></td>
			<td>到: <input id="dt2" class="easyui-datetimebox" style="width:150px"></td>
			<td><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="ss1()">查询</a></td>
			</tr>
		</table>
	<table id="listDetail"></table>
	<script type="text/javascript">
		function ss1(){
				var resobj=document.getElementById("resobj");
				$('#listDetail').datagrid('load',{ 
					ff:$("#dt1").datetimebox('getValue'), 
					tt:$("#dt2").datetimebox('getValue'),
					resId:resobj.value
					}
				); 
			}
	
			$(function() {
				$("#listDetail").datagrid({
					width : '100%',
					heigth : '100%',
					idField : 'id',
					pageSize : 10,
					pageList : [10, 20, 50, 100],
					url : 'viewTaskExecute.action',
				//	 queryParams:{'resId':'<%=resId%>'},
					singleSelect : false,
					fitColumns : true,
					nowrap : true,
					loadMsg : '数据加载中,请稍后……',
					pagination : true,
					rownumbers : true,
					columns : [[ {
						field : 'taskId',
						title : '任务标识',
						width : 250,
						editor : 'text',
						sortable : true
					}, {
						field : 'cityName',
						title : '所属省',
						width : 50,
						editor : 'text'
					}, {
						field : 'fetchAt',
						title : '任务取走时间',
						width : 150,
						editor : 'text'
					}, {
						field : 'operation',
						title : '任务操作',
						width : 100,
						editor : 'text'
					}, {
						field : 'executeAt',
						title : '执行时间',
						width : 150,
						editor : 'text'
					}, {
						field : 'executeCost',
						title : '执行时长（毫秒）',
						width : 100,
						editor : 'text'
					}, {
						field : 'executeStatus',
						title : '执行结果',
						width : 100,
						editor : 'text'
					}]],
					toolbar : [{
						text : '刷新',
						iconCls : 'icon-reload',
						handler : function() {
							$('#listDetail').datagrid('reload');
						}
					}],

					onDblClickRow : function(index, row) {
						//  $('#listDetail').datagrid('expandRow', index);
						//  $('#listDetail').datagrid('fitColumns',index);
						window.location.href = "toViewMcaRaw.action?resid=" + row.mcaid;
					}
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