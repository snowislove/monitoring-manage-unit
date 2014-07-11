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
		adiv.innerText="厂商管理>设备型号列表";
	</script>
  </head>
    <body>
  	<table id="listDetail"></table>
	<script type="text/javascript">
		$(function(){  
    $("#listDetail").datagrid({  
    	width:700,
        heigth:700,     
        idField:'typeid_ccode',  
         pageSize:10,
   		 pageList:[10,20,50,100],
        url:'viewDevTypeByCompanyCode.action',  
        queryParams:{'companyCode':'<%=request.getParameter("companyCode")%>'},  
        singleSelect:false,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',
        pagination:true ,
        rownumbers:true,
        columns:[[  
            {field:'typeid_ccode',checkbox:true},  
            {field:'typecode',title:'型号编码',width:100,editor:'text',sortable:true},    
            {field:'typename',title:'型号名称',width:100,editor:'text'},  
            {field:'typedesc',title:'型号描述',width:100,editor:'text'},    
        ]],   
       toolbar: [{   
            text: '添加防火墙型号',   
            iconCls: 'icon-add',   
            handler: function () {     
               	 	    
				    var urll="toAddType.action?companyCode=<%=request.getParameter("companyCode")%>";  
				    
				 	window.location.href=urll;
				}  

               
        }, '-', {   
            text: '删除型号',   
            iconCls: 'icon-remove',   
            handler: function () {   
            	var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				        
				        $.messager.alert("错误提示", "未选择条目！", "info"); 
				        return;  
				     }  
            	$.messager.confirm('操作提示', '你确认要删除吗?', function(r){
            		if (r){ 
            			 var urll="deleteDevType.action?devTypeId=";  
				    for(var i=0;i<rows.length;i++){ 
				    	if(i==(rows.length-1)){
				    		urll+=rows[i].typeid_ccode;
				    	}else{
				    		urll=urll+rows[i].typeid_ccode+",";
				    	}
				    }  
				    
				 	window.location.href=urll;
            		
            	} });
            	
               

            }   
        }, '-', {   
            text: '修改型号',   
            iconCls: 'icon-edit',   
            handler: function () {     
               	 var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				       
				       $.messager.alert("错误提示", "未选择条目！", "info");  
				        return;  
				     }  
				     if (rows.length>1){
				     	
				     	$.messager.alert("错误提示", "修改型号只能选择一个条目！", "info");
				     	return;
				     }
				    var urll="toModifyType.action?devTypeId="+rows[0].typeid_ccode;  
				    
				 	window.location.href=urll;
				}  

               
        }, '-', {   
            text: '添加采集命令',   
            iconCls: 'icon-add',   
            handler: function () {     
               	 var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				       
				       $.messager.alert("错误提示", "未选择条目！", "info");
				        return;  
				     }  
				     if (rows.length>1){
				     	
				     	$.messager.alert("错误提示", "添加采集命令只能选择一个条目！", "info");
				     	return;
				     }
				    var urll="toAddCommand.action?typeCode="+rows[0].typecode+"&companyCode=<%=request.getParameter("companyCode")%>";  
				    
				 	window.location.href=urll;
				}  
               
        }, '-', {   
            text: '查看采集命令',   
            iconCls: 'icon-search',   
            handler: function () {     
               	 var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				        
				       $.messager.alert("错误提示", "未选择条目！", "info"); 
				        return;  
				     }  
				     if (rows.length>1){
				     	
				     	$.messager.alert("错误提示", "查看采集命令只能选择一个条目！", "info");
				     	return;
				     }
				    var urll="toViewCommand.action?typeCode="+rows[0].typecode;  
				    
				 	window.location.href=urll;
				}  
               
        }, '-', {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listDetail').datagrid('reload'); }
        }],     
        
       
        onDblClickRow:function(index,row){  
          //  $('#listDetail').datagrid('expandRow', index);  
          //  $('#listDetail').datagrid('fitColumns',index);  
           window.location.href="toModifyType.action?devTypeId="+row.typeid_ccode;
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
