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
    <title>基线规则添加</title>
    
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
		adiv.innerText="基线管理>创建基线规则";
	</script>
  </head>
  
  <body>
  	<form id="ff" action="baseLineRuleMapping.action" method="post" target="contextMain" onsubmit="return submitForm();">
    		<input type="hidden" name="baselineId" value="${baselineId }"/>
    		
  			<div id="p" class="easyui-panel" title="基线规则" style="width:500px;height:300px;padding:10px;">
				<table width="400" border="0">
				 	
					 <tr>
					 	<td>
					 		<tr>
	    			<td><label>防火墙厂商：</label></td>
	    			<td><input name="companyCode" id="cc1" class="easyui-combobox" editable="false" missingMessage="请选择设备厂商" data-options=" required:true, valueField: 'id', textField: 'text', url: 'findAllCompany.action', 

							onSelect: function(rec){ 

							var url = 'findDevTypeByCompanyCode.action?companyCode='+rec.id; 
							$('#cc2').combobox('clear'); 
							$('#cc2').combobox('reload', url); 

						}" /> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>防火墙型号：</label></td>
	    			<td><input id="cc2" class="easyui-combobox" name="typeCode" editable="false" missingMessage="请选择设备型号" data-options="required:true,valueField:'id',textField:'text',
	    				onSelect: function(rec){ 
							$.post('getBaseLineRuleByDev.action', {typeCode:rec.id,baselineId:${baselineId }},function(data){
					 		var str=data.rule;
					 		var cc=document.getElementById('ccr');
					 		cc.innerText=str;
						});
							
	    			}" /></td>
	    		</tr>
		
       				 <tr>
				          <td><lable>基线比对规则：</lable></td>
				          <td><label>
				            <textarea id="ccr" class="datagrid-editable-input"  name="baselineRule" style="resize:none;width:300px;height:100px">${configRule.containConflictRule }</textarea> 
				           
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