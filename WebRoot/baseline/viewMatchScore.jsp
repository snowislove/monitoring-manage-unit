<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SysUserInfo user=(SysUserInfo)session.getAttribute("user");
Map app=user.getApp();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
    <title>基线比对</title>
    
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
		adiv.innerText="基线管理>基线比对";
	</script>
  </head>
  <body>
  	<div class="easyui-tabs" style="width:800px;height:400px">
  		<% if(app.get("防火墙基线比对分数排名查看")!=null){ %>
  		<!-- 防火墙告警设置 -->
		<div title="防火墙基线比对分数排名" style="padding:10px">
		<table>
			<tr>
			<td>选择日期从: <input id="dtf" class="easyui-datetimebox" style="width:150px"></td>
			<td>到: <input id="dtt" class="easyui-datetimebox" style="width:150px"></td>
			<td><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="ss()">查询</a></td>
			</tr>
		</table>
		<table id="listScore"></table>
		<script type="text/javascript">
			function ss(){
				
				$('#listScore').datagrid('load',{ 
					ff:$("#dtf").datetimebox('getValue'), 
					tt:$("#dtt").datetimebox('getValue')
					}
				); 
			}
		
		$(function(){  
    $("#listScore").datagrid({  
    	width:700,
        heigth:700,     
        idField:'resId_taskCode',  
         pageSize:10,
   	 	pageList:[10,20,50,100],
        url:'queryBaseLineMatchScore.action',  
       // queryParams:{'resId':'${resId}'},  
        singleSelect:true,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        pagination:true ,
        rownumbers:true,
        columns:[[  
            {field:'resId_taskCode',checkbox:true},  
            {field:'resName',title:'资源名称',width:100,editor:'text'},    
            {field:'resIp',title:'资源IP',width:100,editor:'text'},  
            {field:'cdate',title:'比对时间',width:100,editor:'text'},
            {field:'score',title:'分数',width:100,editor:'text'}
           	          	
        ]],   
       toolbar: [ 
       	<% if(app.get("查看分数明细")!=null){ %>
       {   
            text: '查看明细',   
            iconCls: 'icon-search',   
            handler: function () {     
               	 var rows = $('#listScore').datagrid('getSelections'); 
               	
				    var urll="toQueryMatchDatil.action?resId_taskCode="+rows[0].resId_taskCode;  
				   
				 	window.location.href=urll;
				}  
        }, '-',
        <%}%>
         {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listScore').datagrid('reload'); }
        }]
    }); 
		$('#listScore').datagrid("getPager").pagination({
		    		displayMsg:'当前显示从{from}到{to}共{total}记录',
		   			 onBeforeRefresh:function(pageNumber, pageSize){
		     		$(this).pagination('loading');
		    		
		    		 $(this).pagination('loaded');
		   		 }
		  	 });  
		
		    $('#listScore').datagrid("getPager").pagination({  
		
		        pageSize: 10,//每页显示的记录条数，默认为10  
		
		        pageList: [10,20,50,100],//可以设置每页记录条数的列表  
		
		        beforePageText: '第',//页数文本框前显示的汉字  
		
		        afterPageText: '页    共 {pages} 页',  
		
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
		
		        
		
		    });
	});  
		
		
	</script>
		
		
		</div>
		<% }if(app.get("查询防火墙基线分数")!=null){ %>
	
		<div title="按资源查看分数" style="padding:10px">
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
					<input id="cc2" class="easyui-combobox" editable="false" name="sbucityCode" data-options="valueField:'id',textField:'text',
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
				
				}"/>
			</td>
			</tr>
			<tr>
			<td>选择日期从: <input id="dt1" class="easyui-datetimebox" style="width:150px"></td>
			<td>到: <input id="dt2" class="easyui-datetimebox" style="width:150px"></td>
			<td><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="ss1()">查询</a></td>
			</tr>
		</table>
		<table id="listScoreByRes"></table>
		<script type="text/javascript">
			function ss1(){
				var resobj=document.getElementById("resobj");
				$('#listScoreByRes').datagrid('load',{ 
					ff:$("#dt1").datetimebox('getValue'), 
					tt:$("#dt2").datetimebox('getValue'),
					resId:resobj.value
					}
				); 
			}
		
		$(function(){  
    $("#listScoreByRes").datagrid({  
    	width:700,
        heigth:700,     
        idField:'resId_taskCode',  
         pageSize:10,
   	 	pageList:[10,20,50,100],
        url:'queryResBaseLineMatchScore.action',  
       // queryParams:{'resId':'${resId}'},  
        singleSelect:true,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        pagination:true ,
        rownumbers:true,
        columns:[[  
            {field:'resId_taskCode',checkbox:true},  
            {field:'cdate',title:'比对时间',width:100,editor:'text'},
            {field:'score',title:'分数',width:100,editor:'text'}
           	          	
        ]],   
       toolbar: [ 
       <% if(app.get("查看分数明细")!=null){ %>
       {   
            text: '查看明细',   
            iconCls: 'icon-search',   
            handler: function () {     
               	 var rows = $('#listScoreByRes').datagrid('getSelections'); 
               	
				    var urll="toQueryMatchDatil.action?resId_taskCode="+rows[0].resId_taskCode;  
				   
				 	window.location.href=urll;
				}  

               
        }, '-', 
        <%}%>
        {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listScoreByRes').datagrid('reload'); }
        }]
    }); 
		$('#listScoreByRes').datagrid("getPager").pagination({
		    		displayMsg:'当前显示从{from}到{to}共{total}记录',
		   			 onBeforeRefresh:function(pageNumber, pageSize){
		     		$(this).pagination('loading');
		    		
		    		 $(this).pagination('loaded');
		   		 }
		  	 });  
		
		    $('#listScore').datagrid("getPager").pagination({  
		
		        pageSize: 10,//每页显示的记录条数，默认为10  
		
		        pageList: [10,20,50,100],//可以设置每页记录条数的列表  
		
		        beforePageText: '第',//页数文本框前显示的汉字  
		
		        afterPageText: '页    共 {pages} 页',  
		
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
		
		        
		
		    });
		});  
		
		
	</script>
		
		</div>
		<%} %>
	</div>
  </body>
</html>
