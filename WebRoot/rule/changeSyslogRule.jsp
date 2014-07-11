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
    <title>SYSLOG标准化规则上传</title>
    
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
		adiv.innerText="规则管理>SYSLOG规则上传";
	</script>
  </head>
    <body>
    	<form id="ff" action="syslogRule.action" method="post" target="contextMain" enctype="multipart/form-data">
    		<input type="hidden" name="typeCode" value="${typeCode }"/>
    		<input type="hidden" name="oper" value="${oper }"/>
  			<div id="p" class="easyui-panel" title="请上传规则" style="width:500px;height:200px;padding:10px;">
				<table width="400" border="0">
					 <tr>
				          <td><lable>请选择syslog标准化脚本：</lable></td>
				          <td><label>
				            <input type="file" name="file" id="file"/>
				           
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
  </body>
</html>
