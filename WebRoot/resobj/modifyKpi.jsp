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
<title>用户修改</title>
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
		adiv.innerText="指标管理>指标修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:388px;background:#EFF5FF">
		<a id="sub" href="javascript:if(document.ff.onsubmit()!=false)document.ff.submit();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" >修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:400px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifyKpiInfo.action" method="post" onsubmit="return submitForm();">
		    <input type="hidden" name="kpiInfo.id" value="${ kpi.id}"/>
		    	<table>
		    	<tr>
	    			<td><label>指标名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入指标英文名称" name="kpiInfo.kpiName" data-options="required:true" value="${ kpi.kpiName}"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>指标描述：</label></td>
	    			<td><textarea class="datagrid-editable-input"  name="kpiInfo.kpiDesc" style="resize:none;">${ kpi.kpiDesc}</textarea> 
	    			</td>
	    		</tr>
	    		
	    		
	    		<tr>
	    			<td><label>资源类型：</label></td>
	    			<td>  
	    			<select id="cc2" class="easyui-combobox" name="kpiInfo.classId" editable="false" data-options="required:true,valueField:'classid',textField:'text', url: 'findAllResClass.action'" style="width:80px">
	    					<option value="${ resClass.id}" selected>${resClass.className}</option>
	    				</select>
	    			</td>
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