<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>厂商修改</title>
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
		adiv.innerText="厂商管理>厂商信息修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:388px;background:#EFF5FF">
		<a id="sub" href="javascript:if(document.ff.onsubmit()!=false)document.ff.submit();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" >修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:400px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifyCompany.action" method="post" onsubmit="return submitForm();">
		    <input type="hidden" name="company.id" value="${ company.id}"/>
		    	<table>
	    		<tr>
	    			<td><label>厂商名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入厂商名称" name="company.companyName" value="${company.companyName }" data-options="required:true"></input></td>
	    		</tr>
	    		
	    		
	    		<tr>
	    			<td><label>厂商描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" name="company.companyDesc" style="resize:none;">${company.companyDesc}</textarea></td>
	    		</tr>
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