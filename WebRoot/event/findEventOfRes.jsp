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
    <title>事件查看</title>
    
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
		adiv.innerText="事件管理>事件查看";
	</script>
  </head>
  <body>
  	<div class="easyui-tabs" style="width:1000px;height:350px">
  		<!-- 防火墙告警设置 -->
		<div title="当前事件查看" style="padding:10px">
		<table id="listDetail"></table>
		<script type="text/javascript">
		$(function(){  
    $("#listDetail").datagrid({  
    	width:900,
        heigth:700,     
        idField:'eventid',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'getEventbyResId.action',  
        queryParams:{'resId':'${resId}'},  
        singleSelect:true,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        pagination:true ,
        rownumbers:true,
        columns:[[  
            {field:'eventid',checkbox:true},  
            {field:'eventlevel',title:'事件级别',width:100,editor:'text'},    
            {field:'message',title:'事件内容',width:300,editor:'text'},  
            {field:'cdate',title:'发生时间',width:150,editor:'text'},
            {field:'status',title:'事件状态',width:100,editor:'text'}
           	          	
        ]],   
       toolbar: [ {   
            text: '事件处理',   
            iconCls: 'icon-edit',   
            handler: function () {     
               	 var rows = $('#listDetail').datagrid('getSelections'); 
               	
				    var urll="toDealEvent.action?eventId="+rows[0].eventid;  
				   
				 	window.open(urll,'','width=900px,height=900px,left=10, top=10,toolbar=no, status=no, menubar=no, resizable=yes, scrollbars=yes');
				}  

               
        }, '-', {   
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
		
		        
		
		    });
	});  
		
		
	</script>
		
		
		</div>
		
	
		<div title="历史事件查询" style="padding:10px">
			<table>
			<tr>
			<td>选择日期从: <input id="dt1" type="text"></input></td>
			<td>到: <input id="dt2" type="text"></input></td>
			<td><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="ss()">查询</a></td>
			</tr>
			</table>
			<table id="historyList"></table>
			<script type="text/javascript">
			
			function ss(){
				$('#historyList').datagrid('load',{ 
					ff:$("#dt1").datetimebox('getValue'), 
					tt:$("#dt2").datetimebox('getValue'),
					resId:${resId}
					}
				); 
			}
			
			$('#dt1').datetimebox({ 
				showSeconds:true 
			}); 
			$('#dt2').datetimebox({ 
				showSeconds:true  
			}); 
		$(function(){  
    $("#historyList").datagrid({  
    	width:900,
        heigth:700,     
        idField:'eventid',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'getEventbyTime.action',  
    //    queryParams:{'resId':'${resId}','ff':f,'tt':t},  
        
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        pagination:true ,
        rownumbers:true,
        columns:[[  
            
            {field:'eventlevel',title:'事件级别',width:100,editor:'text'},    
            {field:'message',title:'事件内容',width:300,editor:'text'},  
            {field:'cdate',title:'发生时间',width:150,editor:'text'},
            {field:'confirmUser',title:'确认人',width:100,editor:'text'},
            {field:'confirmDate',title:'确认时间',width:150,editor:'text'},
            {field:'clearUser',title:'清除人',width:100,editor:'text'},
           	 {field:'clearDate',title:'清除时间',width:150,editor:'text'}         	
        ]],   
       toolbar: [ {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#historyList').datagrid('reload'); }
        }]
    }); 
		$('#historyList').datagrid("getPager").pagination({
		    		displayMsg:'当前显示从{from}到{to}共{total}记录',
		   			 onBeforeRefresh:function(pageNumber, pageSize){
		   				$(this).pagination('loading');
		    		
		    		 $(this).pagination('loaded');
				
		   		 }
		  	 });  
		
		    $('#historyList').datagrid("getPager").pagination({  
		
		        pageSize: 10,//每页显示的记录条数，默认为10  
		
		        pageList: [10,20,50,100],//可以设置每页记录条数的列表  
		
		        beforePageText: '第',//页数文本框前显示的汉字  
		
		        afterPageText: '页    共 {pages} 页',  
		
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
		
		        
		
		    });
	});  
		
		
		
	</script>
		</div>
	</div>
  </body>
</html>
