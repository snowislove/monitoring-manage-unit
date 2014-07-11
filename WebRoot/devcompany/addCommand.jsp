<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加采集配置命令</title>
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
		adiv.innerText="厂商管理>添加采集配置命令";
	</script>
</head>
<body>
	<div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveCommand.action" method="post" onsubmit="return submitForm();">
		    	<input type="hidden" name="sc.typeCode" value="${typeCode }"/>
		    	<table>
	    		<tr>
	    			<td><label>开启命令：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="开启命令为登录设备后开启获取配置命令权限的命令，如无需开启则和采集命令相同" name="sc.openCommand" data-options="required:true"></input>&nbsp;</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>采集命令：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="如需多个采集命令请使用^号将命令隔开" name="sc.command" data-options="required:true"></input></td>
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