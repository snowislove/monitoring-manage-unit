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
		adiv.innerText="告警配置管理>告警规则设置";
	</script>
  </head>
  <body>
  	<div class="easyui-tabs" style="width:800px;height:350px">
  		<!-- 防火墙告警设置 -->
		<div title="防火墙告警规则设置" style="padding:10px">
			
			<input type="hidden" id="resobj" value=""/>
			<input type="hidden" id="eventType" value=""/>
    <table>
    	<tr>
    		<td>
			    <lable>&nbsp;&nbsp;省份：</lable>
			    <input name="cityCode" id="cc1" class="easyui-combobox" editable="false" data-options=" required:true, valueField: 'id', textField: 'text', url: 'getAllCity.action', 
			
										onSelect: function(rec){ 
			
										var url = 'getCityByParent.action?cityCode='+rec.id; 
										$('#cc2').combobox('clear');
										$('#cc2').combobox('reload', url); 
			
									}" /> 
			</td>
			<td>
				<lable>&nbsp;&nbsp;单位：</lable>
				<input id="cc2" class="easyui-combobox" name="sbucityCode" editable="false" data-options="required:true,valueField:'id',textField:'text',
					onSelect: function(rec){ 
										var url = 'getResObjByCity.action?cityCode='+rec.id; 
										$('#cc3').combobox('clear');
										$('#cc3').combobox('reload', url); 
				}" />
			</td>
			<td>
				<lable>&nbsp;防火墙：</lable>
				<input id="cc3" class="easyui-combobox" name="resId" editable="false" data-options="required:true,valueField:'id',textField:'text',
					onSelect: function(rec){ 
							var resobj=document.getElementById('resobj');
							resobj.value=rec.id;
							var eventType=document.getElementById('eventType');
							if(eventType.value!=''){
	            				$.post('viewEventRule.action', {resId:resobj.value,eventTypeId:eventType.value},function(data){
			 						$('#listDetail').datagrid('loadData',data);
								});
							}
				
				}" />
			</td>
			<td>
				<lable>&nbsp;事件类型：</lable>
				<input id="cc4" class="easyui-combobox" name="eventTypeId" editable="false" data-options="required:true,valueField:'id',textField:'text',url: 'getAllEventTypeByClass.action?resclass=fw',
					onSelect: function(rec){ 
								var eventType=document.getElementById('eventType');			
								eventType.value=rec.id;
								var resobj=document.getElementById('resobj');
								if(resobj.value!=''){
		            				$.post('viewEventRule.action', {resId:resobj.value,eventTypeId:eventType.value},function(data){
				 						$('#listDetail').datagrid('loadData',data);
									});
								}
				}" />
			</td>
	  	</tr>
	  </table>
	  <div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>告警规则：</div>
	  </div>
	 
		<table id="listDetail"></table>
		<script type="text/javascript">
		$(function(){  
    $("#listDetail").datagrid({  
    	width:700,
        heigth:700,     
        idField:'ruleid',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'',  
        singleSelect:false,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        columns:[[  
            {field:'ruleid',checkbox:true},  
            {field:'eventlevel',title:'告警级别',width:100,editor:'text',sortable:true},     
            {field:'setMsg',title:'是否产生告警短信',width:200,editor:'text'} ,
            {field:'topr',title:'操作符',width:100,editor:'textarea'}, 
            {field:'tvalue',title:'阀值',width:150,editor:'text'},
            {field:'repeat',title:'是否重复告警',width:150,editor:'text'},
            {field:'recoverSetMsg',title:'是否产生恢复短信',width:150,editor:'text'}
        ]],   
       toolbar: [{   
            text: '添加告警规则',   
            iconCls: 'icon-add',   
            handler: function () {  
            	var resId=document.getElementById("resobj");
            	var eventType=document.getElementById("eventType");
            	if(eventType.value==''|| resId.value==''){
            		
            		$.messager.alert("错误提示", "请选择资源和事件类型！", "info");
            		return ;
            	}
               window.location.href="toAddEventRule.action?resId="+resId.value+"&eventTypeId="+eventType.value;
            }   
        }, '-', {   
            text: '删除告警规则',   
            iconCls: 'icon-remove',   
            handler: function () {  
            	var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				        
				       $.messager.alert("错误提示", "未选择条目！", "info");
				        return;  
				     }  
				     
				$.messager.confirm('操作提示', '你确认要删除吗?', function(r){
				if (r){ 
					 var urll="deleteEventRule.action?ruleId=";  
				   
				    for(var i=0;i<rows.length;i++){ 
				    	if(i==(rows.length-1)){
				    		urll+=rows[i].ruleid;
				    	}else{
				    		urll=urll+rows[i].ruleid+",";
				    	}
				    }  
				   
				 	window.location.href=urll;
				 } });
                

            }   
        }, '-', {   
            text: '修改告警规则',   
            iconCls: 'icon-edit',   
            handler: function () {     
               	 var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				        
				       $.messager.alert("错误提示", "未选择条目！", "info");  
				        return;  
				     }  
				     if (rows.length>1){
				     	
				     	$.messager.alert("错误提示", "修改告警规则只能选择一个条目！", "info");
				     	return;
				     }
				    var urll="toModifyEventRule.action?ruleid="+rows[0].ruleid;  
				    
				 	window.location.href=urll;
				}  

               
        }, '-', {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listDetail').datagrid('reload'); }
        }]     
        
    }); 
		
		
	});  
		
		
	</script>
		
		
		</div>
		
	<!-- 采集端告警设置 -->
		<div title="采集机告警规则设置" style="padding:10px">
			
			<input type="hidden" id="mca" value=""/>
			<input type="hidden" id="meventType" value=""/>
			<table>
    		<tr>
    		<td>
			    <lable>&nbsp;&nbsp;省份：</lable>
			    <input name="cityCode" id="mm1" class="easyui-combobox" editable="false" data-options=" required:true, valueField: 'id', textField: 'text', url: 'getAllCity.action', 
			
										onSelect: function(rec){ 
			
										var url = 'getMcaByCity.action?cityCode='+rec.id; 
										$('#mm2').combobox('clear');
										$('#mm2').combobox('reload', url); 
			
									}" /> 
			</td>
			<td>
				<lable>&nbsp;采集机：</lable>
				<input id="mm2" class="easyui-combobox" name="resId" editable="false" data-options="required:true,valueField:'id',textField:'text',
					onSelect: function(rec){ 
							var resobj=document.getElementById('mca');
							resobj.value=rec.id;
							var eventType=document.getElementById('meventType');
							if(eventType.value!=''){
	            				$.post('viewEventRule.action', {resId:resobj.value,eventTypeId:eventType.value},function(data){
			 						$('mcaDetail').datagrid('loadData',data);
								});
							}
				
				}" />
			</td>
			<td>
				<lable>&nbsp;事件类型：</lable>
				<input id="mm4" class="easyui-combobox" name="eventTypeId" editable="false" data-options="required:true,valueField:'id',textField:'text',url: 'getAllEventTypeByClass.action?resclass=mca',
					onSelect: function(rec){ 
								var eventType=document.getElementById('meventType');			
								eventType.value=rec.id;
								var resobj=document.getElementById('mca');
								if(resobj.value!=''){
		            				$.post('viewEventRule.action', {resId:resobj.value,eventTypeId:eventType.value},function(data){
				 						$('#mcaDetail').datagrid('loadData',data);
									});
								}
				}" />
			</td>
	  	</tr>
	  </table>
	  <div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>告警规则：</div>
	  </div>
		<table id="mcaDetail"></table>
		<script type="text/javascript">
		$(function(){  
    $("#mcaDetail").datagrid({  
    	width:700,
        heigth:700,     
        idField:'ruleid',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'',  
        singleSelect:false,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        columns:[[  
            {field:'ruleid',checkbox:true},  
            {field:'eventlevel',title:'告警级别',width:100,editor:'text',sortable:true},     
            {field:'setMsg',title:'是否产生告警短信',width:200,editor:'text'} ,
            {field:'topr',title:'操作符',width:100,editor:'textarea'}, 
            {field:'tvalue',title:'阀值',width:150,editor:'text'},
            {field:'repeat',title:'是否重复告警',width:150,editor:'text'},
            {field:'recoverSetMsg',title:'是否产生恢复短信',width:150,editor:'text'}
        ]],   
       toolbar: [{   
            text: '添加告警规则',   
            iconCls: 'icon-add',   
            handler: function () {  
            	var resId=document.getElementById("mca");
            	var eventType=document.getElementById("meventType");
            	if(eventType.value==''|| resId.value==''){
            		
            		$.messager.alert("错误提示", "请选择资源和事件类型！", "info");
            		return ;
            	}
               window.location.href="toAddEventRule.action?resId="+resId.value+"&eventTypeId="+eventType.value;
            }   
        }, '-', {   
            text: '删除告警规则',   
            iconCls: 'icon-remove',   
            handler: function () {  
            	var rows = $('#mcaDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				       $.messager.alert("错误提示", "未选择条目！", "info"); 
				        return;  
				     }  
            $.messager.confirm('操作提示', '你确认要删除吗?', function(r){
             	if (r){ 
					 var urll="deleteEventRule.action?ruleId=";  
				   
				    for(var i=0;i<rows.length;i++){ 
				    	if(i==(rows.length-1)){
				    		urll+=rows[i].ruleid;
				    	}else{
				    		urll=urll+rows[i].ruleid+",";
				    	}
				    }  
				   
				 	window.location.href=urll;
				 } });
                

            }   
        }, '-', {   
            text: '修改告警规则',   
            iconCls: 'icon-edit',   
            handler: function () {     
               	 var rows = $('#mcaDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				        
				       $.messager.alert("错误提示", "未选择条目！", "info");
				        return;  
				     }  
				     if (rows.length>1){
				     	
				     	$.messager.alert("错误提示", "修改告警规则只能选择一个条目！", "info");
				     	return;
				     }
				    var urll="toModifyEventRule.action?ruleid="+rows[0].ruleid;  
				    
				 	window.location.href=urll;
				}  

               
        }, '-', {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#mcaDetail').datagrid('reload'); }
        }]     
        
    }); 
		
		
	});  
		
		
	</script>
			
			
		</div>
	</div>
  </body>
</html>