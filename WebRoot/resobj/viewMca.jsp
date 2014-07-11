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
    <title>采集端明细</title>
    
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
		adiv.innerText="采集机管理>采集机列表";
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
    	width:1000,
        heigth:700,     
        idField:'mcaid',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'viewAllMca.action',  
       // queryParams:{'viewType':'RK','RKD_ID':_rkdId},  
        singleSelect:false,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        pagination:true ,
        rownumbers:true,
        columns:[[  
            {field:'mcaid',checkbox:true},  
            {field:'resName',title:'采集名称',width:200,editor:'text',sortable:true},    
            {field:'resIp',title:'IP地址',width:200,editor:'text'},  
            {field:'resDesc',title:'描述',width:300,editor:'text'},   
            {field:'cityName',title:'所属城市',width:100,editor:'text'},
            {field:'cdate',title:'创建时间',width:150,editor:'text'}
            <% if(app.get("暂停启动采集端")!=null){ %>
            ,  
           	{
  				 field : 'mcapaused',
  				 title : '启动/暂停',
   				width : 100,
   				formatter:function(value,row,index){
					var d;
					if(row.mcapaused=='0'){
    					d = '<a href="updateMcaPaused.action?mcaid=' + row.mcaid +'&paused=1"  class="easyui-linkbutton l-btn l-btn-plain"' + "<font color='blue'>暂停</font></a>&nbsp;<font color='#808080'>启动</font>"; 
       				}else{
       					d = '<a href="updateMcaPaused.action?mcaid=' + row.mcaid +'&paused=0" class="easyui-linkbutton l-btn l-btn-plain"' + "<font color='blue'>启动</font>" + "</a>&nbsp;<font color='#808080'>暂停</font>"; 
       				}
    				return d; 
    			}
    		}
    		<%}%>
    		,
    		{
  				 field : 'maxLevel',
  				 title : '健康状态',
   				width : 100,
   				formatter:function(value,row,index){
				//    value="<img src='../../images/icons/alldelete.png'>" + "删除";
					var d;
					
					if(row.maxLevel=='-1'){
						d="<img src='<%=_contexPath%>/style/tree/images/paused_res.png'/>";
					}else if(row.maxLevel=='0'||row.maxLevel=='null'){
						d="<img src='<%=_contexPath%>/style/tree/images/alert_ok.png'/>";				
					}else if(row.maxLevel=='1'){
						d="<img src='<%=_contexPath%>/style/tree/images/alert_inform.png'/>";
					}else if(row.maxLevel=='2'){
						d="<img src='<%=_contexPath%>/style/tree/images/alert_minor.png'/>";
					}else if(row.maxLevel=='3'){
						d="<img src='<%=_contexPath%>/style/tree/images/alert_major.png'/>";
					}else if(row.maxLevel=='4'){
						d="<img src='<%=_contexPath%>/style/tree/images/alert_critical.png'/>";
					}
    				return d; 
    			}
    		}
    		<% if(app.get("启停采集端")!=null){ %>
    		,
    		{
  				 field : 'opt',
  				 title : '操作',
   				width : 200,
   				formatter:function(value,row,index){
				//    value="<img src='../../images/icons/alldelete.png'>" + "删除";
					var d;
					d = '<a href="toViewMcaRaw.action?resid=' + row.mcaid +'" class="easyui-linkbutton l-btn l-btn-plain">' + "<font color='blue'>查看明细</font>" + '</a>&nbsp;';
					d+='<a href="toViewEvent.action?resId=' + row.mcaid +'" class="easyui-linkbutton l-btn l-btn-plain">' + "<font color='blue'>查看告警</font>" + '</a>';
    				return d; 
    			}
    		}
    		<%}%>
        ]],   
       toolbar: [
       <% if(app.get("添加采集端")!=null){ %>
       {   
            text: '添加采集机',   
            iconCls: 'icon-add',   
            handler: function () {   
               window.location.href="<%=_contexPath%>/resobj/addMca.jsp";
            }   
        }, '-', 
         <% }if(app.get("删除采集端")!=null){ %>
        {   
            text: '删除采集机',   
            iconCls: 'icon-remove',   
            handler: function () { 
            	 var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				      
				        $.messager.alert("错误提示", "未选择条目！", "info");   
				        return;  
				     }   
               	$.messager.confirm('操作提示', '你确认要删除吗?', function(r){
               	if (r){ 
               		var urll="deleteMca.action?mcaids=";  
				    for(var i=0;i<rows.length;i++){ 
				    	if(i==(rows.length-1)){
				    		urll+=rows[i].mcaid;
				    	}else{
				    		urll=urll+rows[i].mcaid+",";
				    	}
				    }  
				 	window.location.href=urll;
               	 } });
                

            }   
        }, '-',
        <% }if(app.get("修改采集端")!=null){ %>
         {   
            text: '修改采集机',   
            iconCls: 'icon-edit',   
            handler: function () {     
               	 var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				      
				       $.messager.alert("错误提示", "未选择条目！", "info"); 
				        return;  
				     }  
				     if (rows.length>1){
				     	
				     	$.messager.alert("错误提示", "修改采集机只能选择一个条目！", "info");
				     	return;
				     }
				    var urll="toModifyMca.action?mcaid="+rows[0].mcaid;  
				    
				 	window.location.href=urll;
				}  
			
               
        },  '-', 
        <%}%>
         {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listDetail').datagrid('reload'); }
        }],     
        
       
        onDblClickRow:function(index,row){  
          //  $('#listDetail').datagrid('expandRow', index);  
          //  $('#listDetail').datagrid('fitColumns',index);  
           window.location.href="toViewMcaRaw.action?resid="+row.mcaid;
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
