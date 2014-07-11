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
    <title>设备型号列表</title>
    
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
		adiv.innerText="规则配置管理>标准化规则配置";
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
		$(function(){  
    $("#listDetail").datagrid({  
    	width:900,
        heigth:700,     
        idField:'typeCode',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'getAllType.action',  
       // queryParams:{'viewType':'RK','RKD_ID':_rkdId},  
        singleSelect:false,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        pagination:true ,
        rownumbers:true,
        columns:[[  
            {field:'typeCode',title:'类型编码'},  
            {field:'typeName',title:'设备类型名称',width:100,editor:'text',sortable:true},    
            {field:'typeDesc',title:'设备描述',width:100,editor:'text'},  
            {field:'companyName',title:'设备厂商',width:100,editor:'text'}
            <% if(app.get("SYSLOG标准化规则操作")!=null){ %>
            ,
           	{
  				 field : 'soperation',
  				 title : 'SYSLOG标准化规则操作',
   				width : 160,
   				formatter:function(value,row,index){
					var d;
					if(row.hasSyslogRule=='0'){
    					d = '<a href="toChangeSyslogRule.action?typeCode=' + row.typeCode +'&oper=0"  class="easyui-linkbutton l-btn l-btn-plain" )>' + "<font color='blue'>添加规则</font></a>"; 
       				}else{
       					d = '<a href="toChangeSyslogRule.action?typeCode=' + row.typeCode +'&oper=1" class="easyui-linkbutton l-btn l-btn-plain")>' + "<font color='blue'>修改规则</font>" + "</a>&nbsp;"; 
       					d=d+'<a href="deleteSyslogRule.action?typeCode=' + row.typeCode +'&oper=2" class="easyui-linkbutton l-btn l-btn-plain")>' + "<font color='blue'>删除规则</font></a>"
       				}
    				return d; 
    			}
    		}
    		<% }if(app.get("防火墙配置标准化规则操作")!=null){ %>
    		,
    			{
  				 field : 'coperation',
  				 title : '防火墙配置标准化规则操作',
   				width : 160,
   				formatter:function(value,row,index){
					var d;
					if(row.hasConfigRule=='0'){
    					d = '<a href="toChangeConfigRule.action?typeCode=' + row.typeCode +'&oper=0"  class="easyui-linkbutton l-btn l-btn-plain" )>' + "<font color='blue'>添加规则</font></a>"; 
       				}else{
       					d = '<a href="toChangeConfigRule.action?typeCode=' + row.typeCode +'&oper=1" class="easyui-linkbutton l-btn l-btn-plain")>' + "<font color='blue'>修改规则</font>" + "</a>&nbsp;"; 
       					d=d+'<a href="deleteConfigRule.action?typeCode=' + row.typeCode +'&oper=2" class="easyui-linkbutton l-btn l-btn-plain")>' + "<font color='blue'>删除规则</font></a>"
       				}
    				return d; 
    			}
    		}
    		<%}%> 
        ]],   
       toolbar: [ {   
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
