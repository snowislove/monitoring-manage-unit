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
    <title>配置标准化规则上传</title>
    
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
		adiv.innerText="规则管理>配置标准化规则上传";
	</script>
  </head>
    <body>
    	<form id="ff" action="configRule.action" method="post" target="contextMain" enctype="multipart/form-data" onsubmit="return submitForm();">
    		<input type="hidden" name="typeCode" value="${typeCode }"/>
    		<input type="hidden" name="oper" value="${oper }"/>
  			<div id="p" class="easyui-panel" title="请上传规则" style="width:500px;height:300px;padding:10px;">
				<table width="400" border="0">
				 	
					 <tr>
				          <td><lable>请选择防火墙配置标准化脚本：</lable></td>
				          <td><label>
				            
				           	<input id="ff" class="easyui-validatebox" type="file" missingMessage="请选择文件" name="file" data-options="required:true"></input>
				          </label></td>
       				 </tr>
       				 <tr>
				          <td><lable>防火墙策略包含与冲突比对规则：</lable></td>
				          <td><label>
				            <textarea id="ccr" class="datagrid-editable-input"  name="containConflictRule" style="resize:none;width:300px;height:100px">${configRule.containConflictRule }</textarea> 
				           
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
   <script>
		function submitForm(){
			var t=document.getElementById("ccr");
			if(t.value==""){
				$.messager.alert('错误提示','请输入包含与冲突比对规则！','error');
				return false;
			}
			return true;
		}
		function clearForm(){
			$('#ff').form('clear');
		}
		
	</script>
</html>
