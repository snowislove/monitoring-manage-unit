<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
<html>
  <head>
   <%
   		String userDisplay="";
		List userList =(List)request.getAttribute("userList");
	
		for(int i=0; i<userList.size(); i++)
		{
			SysUserInfo value = (SysUserInfo)userList.get(i);
			userDisplay+=value.getId()+"|"+value.getAccount()+":"+value.getUserName();
			if(i<userList.size()-1){
				userDisplay+=";";
			}
		}
    %>
    <title>添加告警接收人</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
    <script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="告警配置管理>添加告警接收人";
		
		function fillSelect(selectElement, datastr){
			if (datastr == null || datastr.length==0)
				return;
			var records = datastr.split(";");
			selectElement.length = 0;
			for(i=0; i<records.length; i++) {
			fields = records[i].split("|");
			selectElement.options[selectElement.length] = new Option(fields[1], fields[0]);
 		}	
	}
		function moveSel(oTargetSel,oSourceSel)
		 {
		 	
			for(var	i=0; i<oSourceSel.options.length; i++)
			{
				if(oSourceSel.options[i].selected)
				{
					var	oOption	= document.createElement("option");
					oTargetSel.options.add(oOption);
					oOption.text= oSourceSel.options[i].text;
					oOption.value=oSourceSel.options[i].value;
					oSourceSel.remove(i);
					i--;
				}
			}
		 }
		  function moveAll(oTargetSel, oSourceSel)
		 {
			for(var	i=0; i<oSourceSel.options.length; i++)
			{
					var	oOption	= document.createElement("option");
					oTargetSel.options.add(oOption);
					oOption.text= oSourceSel.options[i].text;
					oOption.value=oSourceSel.options[i].value;
					oSourceSel.remove(i);
					i--;
			}
		 }
    	 function moveltr(){
     		var selleft=document.getElementById("selleft");
     		var selright=document.getElementById("selright");
     		moveSel(selright,selleft);
     	}
     	function movertl(){
     		var selleft=document.getElementById("selleft");
     		var selright=document.getElementById("selright");
     		moveSel(selleft,selright);
     	}
     	function moveallltr(){
     		var selleft=document.getElementById("selleft");
     		var selright=document.getElementById("selright");
     		moveAll(selright,selleft);
     	}
     	function moveallrtl(){
     		var selleft=document.getElementById("selleft");
     		var selright=document.getElementById("selright");
     		moveAll(selleft,selright);
     	}
     	function tosumbit(){
     		var users =document.getElementById("selright").options;
     		if(users.length==0){
     			
     			$.messager.alert("错误提示", "请选择一个或多个接收者！", "info");
				return false;
     		}
     		var userid="";
     		var count=0;
     		for(var i=0;i<users.length;i++){
     			userid+=users[i].value;
     			if(i!=(users.length-1)){
     				userid+=",";
     			}
     		}
     		var userIds=document.getElementById("userIds");
     		userIds.value=userid;
     		return true;
     	}
	</script>
 </head>
 <body>
 	<div class="easyui-panel" title="请选择告警接收人" style="width:500px">
   					<form name="saveForm" id="saveForm" method="POST" action="saveOrUpdateNotifyUser.action" onsubmit="return tosumbit();">
   						<input type="hidden" name="resId" value="${resId }"/>
   						<input type="hidden" name="ruleId" value="${ruleId }"/>
   						<input type="hidden" id="userIds" name="userIds" value=""/>
   						<table  width="350px" border="1" cellspacing="0" cellpadding="0">
						  	<tr>
						  	  <!-- 左侧Select -->
						 		<td width="25%" align="left">
						  			<select name="selleft" id="selleft" size="20" style="width:150" multiple ></select>
						    	</td>
						    	<!-- 按钮 -->
						     <td width="50" align="center">
						       	<p><input  name="moveleft" type="button"  style="width:30" value="&gt;" onclick="moveltr()"/></p>
						        <p><input  name="moveallleft" type="button"  style="width:30" value="&gt;&gt;" onclick="moveallltr()"/></p>
						       	<p><input  name="moveright" type="button"  style="width:30" value="&lt;" onclick="movettl()"/></p>
						       	<p><input  name="moveallright" type="button"  style="width:30" value="&lt;&lt;" onclick="moveallrtl()"/></p>
						   	</td>
								<!-- 右侧Select -->
						   	<td   align="left">
						     	<select id="selright" name="selright" size="20" multiple="multiple"  style="width:150" ></select>
							</td>
							
						  </tr>
						  <tr>
						  	<td colspan="3">
						  		<input type="submit" value="提交"/>
						  	</td>
						  </tr>
						</table>
   					</form>
 	</div>
 	<script>
 		var sel=document.getElementById("selleft");
 		fillSelect(sel, "<%=userDisplay%>");
 	</script>
 </body>
 </html>