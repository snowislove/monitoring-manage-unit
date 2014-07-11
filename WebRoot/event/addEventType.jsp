<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <title>创建事件类型</title>
    
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
		adiv.innerText="告警配置管理>创建事件类型";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveEventType.action" method="post" onsubmit="return submitForm();">
		    	<table>
		    	<tr>
	    			<td><label>事件类型名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入事件类型名称" name="eventType.eventTypeName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>事件类型描述：</label></td>
	    			<td><textarea class="datagrid-editable-input"  name="eventType.eventTypeDesc" style="resize:none;"></textarea> 
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>是否自动恢复：</label></td>
	    			<td> 
	    			<select class="easyui-combobox" name="eventType.eventRecover" editable="false" data-options="required:true">
	    					<option value="1" >自动恢复</option>
	    					<option value="0" selected>不自动恢复</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>
	    			&nbsp;
	    			</td>
	    			<td>
	    			&nbsp;
	    			</td>
	    		<tr>
	    		<tr>
	    			<td>
	    			<input type="submit" value="提交"/>
	    			</td>
	    			<td>
	    			<input type="reset" value="重置"/>
	    			</td>
	    		<tr>
	    	</table>
	    	
		    </form>		     
    	</div>
  </div>
  <script>
 
		function submitForm(){
			return $('#ff').form('validate');
		}
	</script>
  </body>
</html>
