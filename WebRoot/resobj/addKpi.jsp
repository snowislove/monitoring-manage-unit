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
    <title>创建指标</title>
    
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
		adiv.innerText="指标管理>创建指标";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveKpiInfo.action" method="post" onsubmit="return submitForm();">
		    	<table>
		    	<tr>
	    			<td><label>指标名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入指标英文名称" name="kpiInfo.kpiName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>指标描述：</label></td>
	    			<td><textarea class="datagrid-editable-input"  name="kpiInfo.kpiDesc" style="resize:none;"></textarea> 
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>资源类型：</label></td>
	    			<td> 
	    			<input name="kpiInfo.classId" id="cc1" missingMessage="请选择资源类型"  class="easyui-combobox" editable="false" data-options=" required:true, valueField: 'classid', textField: 'text', url: 'findAllResClass.action'" /> 
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
