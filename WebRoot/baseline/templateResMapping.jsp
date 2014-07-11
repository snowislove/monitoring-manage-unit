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
    <title>模板资源映射</title>
    
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
		adiv.innerText="基线模板管理>模板资源映射";
	</script>
  </head>
    <body>
    <form id="ff" action="templateResMapping.action" method="post" onsubmit="return submitForm();">
    <table>
    	<tr>
    		<td>
			    <lable>&nbsp;&nbsp;省份：</lable>
			    <input name="cityCode" id="cc1" class="easyui-combobox" editable="false" missingMessage="请选择省份" data-options=" required:true, valueField: 'id', textField: 'text', url: 'getAllCity.action', 
			
										onSelect: function(rec){ 
			
										var url = 'getCityByParent.action?cityCode='+rec.id; 
										$('#cc2').combobox('clear');
										$('#cc2').combobox('reload', url); 
			
									}" /> 
			</td>
			<td>
		<lable>&nbsp;&nbsp;单位：</lable>
		<input id="cc2" class="easyui-combobox" name="sbucityCode" editable="false" missingMessage="请选择单位" data-options="required:true,valueField:'id',textField:'text',
			onSelect: function(rec){ 
								var url = 'getResObjByCity.action?cityCode='+rec.id; 
								$('#cc3').combobox('clear');
								$('#cc3').combobox('reload', url); 
		}" />
			</td>
			<td>
		<lable>&nbsp;防火墙：</lable>
		<input id="cc3" class="easyui-combobox" name="resId" editable="false" missingMessage="请选择防火墙" data-options="required:true,valueField:'id',textField:'text',
			onSelect: function(rec){ 
								var url = 'getBaseLineTemplateByCompany.action?resId='+rec.id; 
								$('#cc5').combobox('clear');
								$('#cc5').combobox('reload', url); 
		
		}" />
		</td>
	  	</tr>
	  	<tr>
	  		<td>
	  	<lable>设备厂商：</lable>
	  	<input name="companyCode" id="cc4" class="easyui-combobox" editable="false" missingMessage="请选择设备厂商" data-options=" required:true, valueField: 'id', textField: 'text', url: 'findAllCompany.action', 
	
								onSelect: function(rec){ 
	
								var url = 'getBaseLineTemplateByCompany.action?companyCode='+rec.id; 
								$('#cc5').combobox('clear');
								$('#cc5').combobox('reload', url); 
	
							}" /> 
		</td>
		<td>
		<lable>&nbsp;基线模板</lable>
		<input id="cc5" class="easyui-combobox" name="templateId" editable="false" missingMessage="请选择基线模板" data-options="required:true,valueField:'id',textField:'text'" />
		</td>
		<td></td>
		</tr>
		<tr>
		<td>
	    			<input type="submit" value="提交"/>
	    			</td>
	    			<td>
	    			<input type="reset" value="重置"/>
	    			</td>
		<td></td>
		</tr>
	</table>
	</form>
	<script>
 
		function submitForm(){
			return $('#ff').form('validate');
		}
	</script>
  </body>
</html>
