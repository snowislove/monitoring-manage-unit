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
    <title>资源查看</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/main.css" />
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/basic.css" />
	<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/style/app/css/app_main.css" />
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="告警配置管理>查看事件消息格式";
	</script>
	<script>
		function deitable(){
			var resDesc=document.getElementById("msgFormat");
			resDesc.disabled=false;
			$('#sub').linkbutton('enable');
			$('#clr').linkbutton('enable');
		}
	</script>
  </head>
  
  <body>
  <div style="padding:5px;border:1px solid #95B8E7;width:588px;background:#EFF5FF">
  		 <% if(app.get("修改事件格式")!=null){ %>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="deitable()">编辑内容</a>
		<a id="sub" href="javascript:document.ff.submit();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  disabled=true>修改提交</a>
		<%} %>
	</div>
  
  <div class="easyui-panel" title="" style="width:600px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifyEventMsg.action" method="post">
		    <input type="hidden" name="msg.id" value="${ emsg.id}"/>
		    <input type="hidden" name="msg.eventTypeId" value="${ emsg.eventTypeId}"/>
		    	<table>
	    		<tr>
	    			<td><label>事件消息格式：</label></td>
	    			<td><textarea id="msgFormat" class="datagrid-editable-input" name="msg.msgFormat" style="resize:none;width:300px;height:100px" disabled=true>${ emsg.msgFormat}</textarea></td>
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
	    			
	    			</td>
	    			<td>
	    			
	    			</td>
	    		<tr>
	    	</table>
		    </form>		     
    	</div>
  </div>
  <script>
		function submitForm(){
			$('#ff').form('submit');
		}
		function clearForm(){
			$('#ff').form('reset');
		}
		
	</script>
  </body>
</html>
