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
<title>设备类型修改</title>
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
		adiv.innerText="采集机管理>采集机修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:488px;background:#EFF5FF">
		<a id="sub" href="javascript:if(document.ff.onsubmit()!=false)document.ff.submit();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" >修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:500px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifyMca.action" method="post" onsubmit="return submitForm();">
		    <input type="hidden" name="resObjForm.resId" value="${ mca.id}"/>
		   
		    	<table>
	    		<tr>
	    			<td><label>IP：</label></td>
	    			<td><input id="resIp1" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp1" missingMessage="请输入IP地址" value="${ip1 }" data-options="required:true"></input>.
	    			<input id="resIp2" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp2" missingMessage="请输入IP地址" value="${ip2 }" data-options="required:true"></input>.
	    			<input id="resIp3" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp3" missingMessage="请输入IP地址" value="${ip3 }" data-options="required:true"></input>.
	    			<input id="resIp4" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp4" missingMessage="请输入IP地址" value="${ip4 }" data-options="required:true"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>采集名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" style="width:150px" missingMessage="请输入资源名称" name="resObjForm.resName" value="${mca.resName}" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>资源描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" name="resObjForm.resDesc"  style="resize:none;width:150px">${mca.resDesc}</textarea></td>
	    		</tr>
	    		<tr>
	    			<td><label>是否启动：</label></td>
	    			<td>
	    			<c:if test="${ mca.resPaused eq '0'}">
	    			<input id="resPaused1" type="radio" name="resObjForm.resPaused" value="0" checked="true" >启动</input>
	    				<input id="resPaused2" type="radio" name="resObjForm.resPaused" value="1" >暂停</input>
	    			</c:if>
	    			<c:if test="${ mca.resPaused eq '1'}">
	    			<input id="resPaused1" type="radio" name="resObjForm.resPaused" value="0" >启动</input>
	    				<input id="resPaused2" type="radio" name="resObjForm.resPaused" value="1" checked="true">暂停</input>
	    			</c:if>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>所属城市：</label></td>
	    			<td>
	    				<select name="resObjForm.cityCode" id="cc1" style="width:100px" class="easyui-combobox" editable="false" data-options=" required:true, valueField: 'id', textField: 'text', url: 'getAllCity.action'" >
							<option value="${ mca.cityCode}" selected>${cityName}</option>
						</select> 
	    			</td>
	    		</tr>
	    	</table>
		    </form>		     
    	</div>
  </div>
  <script>
 
		function submitForm(){
			var resIp1=document.getElementById("resIp1");
			var resIp2=document.getElementById("resIp2");
			var resIp3=document.getElementById("resIp3");
			var resIp4=document.getElementById("resIp4");
			if(resIp1.value>255||resIp2.value>255||resIp3.value>255||resIp4.value>255){
				
				$.messager.alert("错误提示", "ip地址不能超过255！", "info");
				return false;
			}
			return $('#ff').form('validate');
		}
	</script>
</body>
</html>