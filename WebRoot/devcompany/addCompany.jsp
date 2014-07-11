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
<title>添加厂商</title>
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
		adiv.innerText="厂商管理>添加设备厂商";
	</script>
</head>
<body>
	<div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveCompany.action" method="post" onsubmit="return submitForm();">
		    	<table>
	    		<tr>
	    			<td><label>厂商名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入厂商名称" name="company.companyName" data-options="required:true"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>厂商描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" name="company.companyDesc" style="resize:none;"></textarea></td>
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