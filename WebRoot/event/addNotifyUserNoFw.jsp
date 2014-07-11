<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
<html>
  <head>
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
     	function removedata(Sel){
     		for(var	i=0; i<Sel.options.length; i++)
			{
					Sel.remove(i);
					i--;
			}
     	}
	</script>
 </head>
 <body>
 	<div class="easyui-panel" title="请选择防火墙" style="width:800px">
   					<form name="saveForm" id="saveForm" method="POST" action="saveOrUpdateNotifyUser.action" onsubmit="return tosumbit();">
   						<input type="hidden" id="resId" name="resId" value=""/>
   						<input type="hidden" id="ruleId" name="ruleId" value="${ruleId }"/>
   						<input type="hidden" id="userIds" name="userIds" value=""/>
   						 <table>
   						 	<tr>
   								<td>
								    <lable>&nbsp;&nbsp;省份：</lable>
								    <input name="cityCode" id="cc1" class="easyui-combobox" editable="false" data-options=" valueField: 'id', textField: 'text', url: 'getAllCity.action', 
								
															onSelect: function(rec){ 
								
															var url = 'getCityByParent.action?cityCode='+rec.id; 
															$('#cc2').combobox('clear');
															$('#cc2').combobox('reload', url); 
								
														}" /> 
								</td>
								<td>
									<lable>&nbsp;&nbsp;单位：</lable>
									<input id="cc2" class="easyui-combobox" name="sbucityCode" editable="false" data-options="valueField:'id',textField:'text',
										onSelect: function(rec){ 
															var url = 'getResObjByCity.action?cityCode='+rec.id; 
															$('#cc3').combobox('clear');
															$('#cc3').combobox('reload', url); 
									}" />
								</td>
								<td>
									<lable>&nbsp;防火墙：</lable>
									<input id="cc3" class="easyui-combobox" name="resId" editable="false" data-options="valueField:'id',textField:'text',
										onSelect: function(rec){ 
										
												var resobj=document.getElementById('resId');
												
												resobj.value=rec.id;
												
												var ruleId=document.getElementById('ruleId');
												
												if(ruleId.value!=''&&resobj.value!=''){
						            				$.post('queryAlarmReceive.action', {resId:resobj.value,ruleId:ruleId.value},function(data){
								 						var sel=document.getElementById('selleft');
								 						var rig=document.getElementById('selright');
								 						removedata(sel);
								 						removedata(rig);
								 						
								 						if(data.userList!=null){
 															fillSelect(sel, data.userList);
 														}
 														if(data.receiveList!=null){
 															fillSelect(rig, data.receiveList);
 														}
													});
												}
									
									}" />
								</td>
   							</tr>
   						 
    					</table>
    							
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
 	
 </body>
 </html>