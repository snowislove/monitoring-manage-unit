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
    <title>基线模板列表</title>
    
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
		adiv.innerText="基线模板管理>基线模板列表";
	</script>
  </head>
    <body>
  	<table id="listDetail"></table>
	<script type="text/javascript">
		$(function(){  
    $("#listDetail").datagrid({  
    	width:1000,
        heigth:700,     
        idField:'mcaid',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'getAllBaseLinetemplate.action',  
       // queryParams:{'viewType':'RK','RKD_ID':_rkdId},  
        singleSelect:true,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        pagination:true ,
        columns:[[  
            {field:'templateId',checkbox:true},  
            {field:'templateName',title:'模板名称',width:100,editor:'text',sortable:true},    
            {field:'templateDesc',title:'模板描述',width:200,editor:'text'},  
            {field:'companyName',title:'支持厂商',width:100,editor:'text'},              
           	{field:'baselineDesc',title:'基线描述',width:400,editor:'text'},  
           	{field:'baselineType',title:'基线类型',width:100,editor:'text'},  
           	{field:'blackWhite',title:'黑白名单',width:100,editor:'text'},            	
        ]],   
       toolbar: [
       <% if(app.get("创建基线模板")!=null){ %>
       {   
            text: '创建基线模板',   
            iconCls: 'icon-add',   
            handler: function () {   
               window.location.href="<%=_contexPath%>/baseline/addBaseLineTemplate.jsp";
            }   
        }, '-',
        <% }if(app.get("删除基线模板")!=null){ %>
         {   
            text: '删除基线模板',   
            iconCls: 'icon-remove',   
            handler: function () { 
            	var rows = $('#listDetail').datagrid('getSelections');  
				 if (null == rows || rows.length == 0) {  
				        $.messager.alert("错误提示", "未选择条目！", "info");  
				        return;  
				 } 
				 $.messager.confirm('操作提示', '你确认要删除吗?', function(r){
				 
				 	if (r){
				 	
				    var urll="deleteTemplate.action?templateIds=";  
				    for(var i=0;i<rows.length;i++){ 
				    	if(i==(rows.length-1)){
				    		urll+=rows[i].templateId;
				    	}else{
				    		urll=urll+rows[i].templateId+",";
				    	}
				    }  
				    
				 	window.location.href=urll;
				 
				  } });       
               

            }   
        }, '-', 
        <% }if(app.get("修改基线模板")!=null){ %>
        {   
            text: '修改基线模板',   
            iconCls: 'icon-edit',   
            handler: function () {     
               	 var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				      
				       $.messager.alert("错误提示", "未选择条目！", "info");  
				        return;  
				     }  
				     if (rows.length>1){
				     	
				     	$.messager.alert("错误提示", "修改基线模板只能选择一个条目！", "info");
				     	return;
				     }
				    var urll="toModifyTemplate.action?templateId="+rows[0].templateId;  
				    
				 	window.location.href=urll;
				}  

               
        }, '-',
        <% }if(app.get("模板资源映射")!=null){ %>
        {   
            text: '模板资源映射',   
            iconCls: 'icon-mapping',   
            handler: function () {   
               window.location.href="<%=_contexPath%>/baseline/templateResMapping.jsp";
            }   
        }, '-',
        <%}%>
        {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listDetail').datagrid('reload'); }
        }],     
        
       
         
        onLoadSuccess:function(){  


      	   $(this).datagrid("autoMergeCells",['templateId','templateName','templateDesc','companyName']);  
		} 
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
