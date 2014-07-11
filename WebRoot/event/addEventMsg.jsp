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
    <title>事件消息格式添加</title>
    
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
		adiv.innerText="告警配置管理>创建事件消息格式";
	</script>
  </head>
  
  <body>
  	<form id="ff" action="saveEventMsg.action" method="post" target="contextMain" onsubmit="return submitForm();">
    		<input type="hidden" name="msg.eventTypeId" value="${eventTypeId }"/>
    		
  			<div id="p" class="easyui-panel" title="基线规则" style="width:500px;height:300px;padding:10px;">
				<table width="400" border="0">
       				 <tr>
				          <td><lable>事件消息格式：</lable></td>
				          <td><label>
				            <textarea id="ccr" class="datagrid-editable-input"  name="msg.msgFormat" style="resize:none;width:300px;height:100px"></textarea> 
				           
				          </label></td>
       				 </tr>
       				 <tr>
	    			<td>
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
			</div>
		</form>
	<script>
 
		function submitForm(){
			return $('#ff').form('validate');
		}
	</script>
  </body>
  </html>