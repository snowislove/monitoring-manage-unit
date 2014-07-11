<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SysUserInfo user=(SysUserInfo)session.getAttribute("user");
Map app=user.getApp();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
    <title>配置文件比对</title>
    
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
		adiv.innerText="数据展示>配置文件比对";
	</script>
  </head>
  <body>
  	<div class="easyui-tabs" style="width:1000px;height:800px">
  		
  		<!-- 防火墙告警设置 -->
		<div title="历史配置文件比对" style="padding:10px">
		
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
				<lable>防火墙：</lable>
				<input id="cc3" class="easyui-combobox" name="resId" editable="false" data-options="valueField:'id',textField:'text',
						onSelect: function(rec){ 
							
							
							var url = 'getConfigVersion.action?resId='+rec.id+'&page=1&maxRow=5'; 
											$('#cc4').combobox('clear');
											$('#cc4').combobox('reload', url); 
											$('#cc5').combobox('clear');
											$('#cc5').combobox('reload', url); 
				
				}"/>
			</td>
			</tr>
			<tr>
			<td>请选择版本： <input id="cc4" class="easyui-combobox" name="configv1" editable="false" data-options="valueField:'id',textField:'text'"/></td>
			<td>请选择比对版本: <input id="cc5" class="easyui-combobox" name="configv2" editable="false" data-options="valueField:'id',textField:'text'"/></td>
			<td><a href="javascript:void(0)" class="easyui-linkbutton" onclick="ss1()">比对</a></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>增加配置：</td>
				<td>减少配置：</td>
			</tr>
			<tr>
				<td><textarea id="addconfig" class="datagrid-editable-input" name="addconfig" style="resize:none;width:470px;height:300px;color:red" ></textarea></td>
				<td><textarea id="deletefig" class="datagrid-editable-input" name="deletefig" style="resize:none;width:470px;height:300px;color:blue" ></textarea></td>
			</tr>
			<tr>
				<td>原版本内容：</td>
				<td>比对版本内容：</td>
			</tr>
			<tr>
				<td><textarea id="conf1" class="datagrid-editable-input" name="conf1" style="resize:none;width:470px;height:300px"></textarea></td>
				<td><textarea id="conf2" class="datagrid-editable-input" name="conf2" style="resize:none;width:470px;height:300px"></textarea></td>
			</tr>
			
		</table>
		<script type="text/javascript">
			function ss1(){
				var configid1= $('#cc4').combobox('getValue');
				var configid2= $('#cc5').combobox('getValue');
				
				$.post('configMatch.action', {configId1:configid1,configId2:configid2} ,function(data){
  									
			 						var addconfig=document.getElementById("addconfig");
			 						
			 						addconfig.innerText=data.add;
			 						var deletefig=document.getElementById("deletefig");
			 						deletefig.innerText=data.del;
			 						var conf1=document.getElementById("conf1");
			 						
			 						conf1.innerText=data.conf1;
			 						var conf2=document.getElementById("conf2");
			 						conf2.innerText=data.conf2;
								});
			}
		</script>
		</div>
		
	
		<div title="HA配置比对" style="padding:10px">
			
		<table>
			<tr>
				<td>
					<lable>&nbsp;&nbsp;省份：</lable>
			    		<input name="cityCode" id="ch1" class="easyui-combobox" editable="false" data-options=" valueField: 'id', textField: 'text', url: 'getAllCity.action', 
			
										onSelect: function(rec){ 
			
										var url = 'getCityByParent.action?cityCode='+rec.id; 
										$('#ch2').combobox('clear');
										$('#ch2').combobox('reload', url); 
			
									}" /> 
				</td>
				<td>
					<lable>&nbsp;&nbsp;单位：</lable>
					<input id="ch2" class="easyui-combobox" name="sbucityCode" editable="false" data-options="valueField:'id',textField:'text',
						onSelect: function(rec){ 
											var url = 'getResObjByCity.action?cityCode='+rec.id; 
											$('#ch3').combobox('clear');
											$('#ch3').combobox('reload', url); 
											$('#cha3').combobox('clear');
											$('#cha3').combobox('reload', url); 
					}" />
				</td>
				<td>
				<lable>防火墙：</lable>
				<input id="ch3" class="easyui-combobox" name="resId" editable="false" data-options="valueField:'id',textField:'text',
						onSelect: function(rec){ 
							
							
							var url = 'getConfigVersion.action?resId='+rec.id+'&page=1&maxRow=1'; 
											$('#ch4').combobox('clear');
											$('#ch4').combobox('reload', url); 
											
				
				}"/>
				<lable>&nbsp;&nbsp;HA防火墙：</lable>
				<input id="cha3" class="easyui-combobox" name="resId" editable="false" data-options="valueField:'id',textField:'text',
						onSelect: function(rec){ 
							
							
							var url = 'getConfigVersion.action?resId='+rec.id+'&page=1&maxRow=1'; 
											$('#cha5').combobox('clear');
											$('#cha5').combobox('reload', url); 
											
				
				}"/>
			</td>
			</tr>
			
			<tr>
			<td>版本： <input id="ch4" class="easyui-combobox" name="iconfigv1" editable="false" data-options="valueField:'id',textField:'text'"/></td>
			<td>HA版本: <input id="cha5" class="easyui-combobox" name="iconfigv2" editable="false" data-options="valueField:'id',textField:'text'"/></td>
			<td><a href="javascript:void(0)" class="easyui-linkbutton" onclick="ss2()">比对</a></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>增加配置</td>
				<td>减少配置</td>
			</tr>
			<tr>
				<td><textarea id="haaddconfig" class="datagrid-editable-input" name="addconfig" style="resize:none;width:470px;height:300px;color:red" ></textarea></td>
				<td><textarea id="hadeletefig" class="datagrid-editable-input" name="deletefig" style="resize:none;width:470px;height:300px;color:blue"></textarea></td>
			</tr>
			<tr>
				<td>原版本内容：</td>
				<td>比对版本内容：</td>
			</tr>
			<tr>
				<td><textarea id="haconf1" class="datagrid-editable-input" name="haconf1" style="resize:none;width:470px;height:300px" ></textarea></td>
				<td><textarea id="haconf2" class="datagrid-editable-input" name="haconf2" style="resize:none;width:470px;height:300px" ></textarea></td>
			</tr>
		</table>
		<script type="text/javascript">
			function ss2(){
				var configid1= $('#ch4').combobox('getValue');
				var configid2= $('#cha5').combobox('getValue');
				
				$.post('configMatch.action', {configId1:configid1,configId2:configid2} ,function(data){
  									
			 						var addconfig=document.getElementById("haaddconfig");
			 						
			 						addconfig.innerText=data.add;
			 						var deletefig=document.getElementById("hadeletefig");
			 						deletefig.innerText=data.del;
			 						var conf1=document.getElementById("haconf1");
			 						
			 						conf1.innerText=data.conf1;
			 						var conf2=document.getElementById("haconf2");
			 						conf2.innerText=data.conf2;
								});
			}
		</script>
	
		</div>
		
	</div>
  </body>
</html>
