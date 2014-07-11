<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
    <title>防火墙配置文件</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/autoMergeCells.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="数据展示>配置文件展示";
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
				<lable>&nbsp;&nbsp;防火墙：</lable>
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
		$(function(){  
    $("#listDetail").datagrid({  
    	width:700,
        heigth:700,     
        idField:'fileId',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'queryFwFileByRes.action',  
       // queryParams:{'viewType':'RK','RKD_ID':_rkdId},    
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        pagination:true ,
        columns:[[   
            {field:'fileName',title:'文件名称',width:150,editor:'text',sortable:true},     
            {field:'cdate',title:'采集日期',width:150,editor:'text'} ,
            {field:'fileSize',title:'文件大小',width:50,editor:'textarea'}, 
            {field:'filePath',title:'文件路径',width:300,editor:'text'}
            
        ]],   
       toolbar: [{   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listDetail').datagrid('reload'); }
        }]   
    }); 
		$('#listDetail').datagrid("getPager").pagination({
		    		displayMsg:'当前显示从{from}到{to}共{total}记录',
		   			 onBeforeRefresh:function(pageNumber, pageSize){
		     		$(this).pagination('loading');
		    		
		    		 $(this).pagination('loaded');
		   		 }
		  	 });  
		
		    $('#listDetail').datagrid("getPager").pagination({  
		
		        pageSize: 10,//每页显示的记录条数，默认为10  
		
		        pageList: [10,20,50,100],//可以设置每页记录条数的列表  
		
		        beforePageText: '第',//页数文本框前显示的汉字  
		
		        afterPageText: '页    共 {pages} 页',  
		
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
		
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
