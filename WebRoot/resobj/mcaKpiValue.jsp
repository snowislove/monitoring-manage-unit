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
    <title>采集机指标</title>
    
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
		adiv.innerText="采集机管理>采集机指标";
	</script>
  </head>
    <body>
    <div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>采集机当前指标信息：</div>
	</div>
  	<table id="listDetail"></table>
  	
	<script type="text/javascript">
		$(function(){  
    $("#listDetail").datagrid({  
    	width:700,
        heigth:700,     
        idField:'mcaid',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'queryMcaRaw.action',  
        queryParams:{'resid':'${resid}'},  
        singleSelect:false,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        rownumbers:true,
        columns:[[  
            
            {field:'kpiName',title:'指标名称',width:100,editor:'text',sortable:true},  
            {field:'kpiDesc',title:'指标描述',width:100,editor:'text',sortable:true},   
            {field:'kpiValue',title:'指标值',width:100,editor:'text'}
                ]],   
       toolbar: [{   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listDetail').datagrid('reload'); }
        },'-', {   
            text: '采集程序重启',   
            iconCls: 'icon-restart',   
            handler: function () {   
                if (confirm("确定重启采集程序吗？")) {   
				    var urll="mcaOperation.action?mcaid=${resid}&operation=restart";  
				 	window.location.href=urll;
				}  

            }   
               
        }
        ]           
       
    }); 
		
	});  
	
	
		
		
	</script>
	
  </body>
</html>
